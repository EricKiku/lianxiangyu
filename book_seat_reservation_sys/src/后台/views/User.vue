<template>
    <!-- 抽屉 -->
    <el-drawer class="drawer" v-model="drawerVisual" title="I am the title" :with-header="false">
        <div class="title bold fz-18 heiti">新增用户</div>
        <div class="row">
            <div class="label">学号：</div>
            <input v-model="stuNumber" @keydown.enter="search" type="text" placeholder="请输入学号">
        </div>
        <div class="row">
            <div class="label">姓名：</div>
            <input v-model="stuName" @keydown.enter="search" type="text" placeholder="请输入姓名">
        </div>
        <div class="row">
            <div class="label">密码：</div>
            <input v-model="stuPwd" @keydown.enter="search" type="text" placeholder="请输入登录密码">
        </div>
        <div class="w-color">{{ tipMessage }}</div>
        <div class="bottom">
            <v-btn :loading="loading" @click="confirmCreateStudent" class="s-color p-bgcolor">确定</v-btn>
        </div>
    </el-drawer>

    <div>
        用户管理
    </div>
    <div class="data">
        <div class="search">
            <span class="label cell-color">搜索关键字
                <v-tooltip text="只可按姓名，学号搜索。并且搜索结果叠加" location="top">
                    <template v-slot:activator="{ props }">
                        <v-icon v-bind="props" color="#606266" icon="mdi-help-circle-outline" />
                    </template>
                </v-tooltip>
                :</span>
            <input v-model="searchKey" @keydown.enter="search" type="text" placeholder="请输入关键字，回车搜索">
            <v-btn style="height: 30px;margin: 0px 5px;" class="p-bgcolor s-color" prepend-icon="mdi-magnify"
                @click="search">
                搜索
            </v-btn>
            <v-btn variant="outlined" style="height: 30px;margin: 0px 5px;" class="cell-color p-bdcolor s-bgcolor n-color"
                prepend-icon="mdi-trash-can-outline" @click="getStudent">
                重置
            </v-btn>
        </div>
        <div class="ops">
            <v-btn @click="getStudent" style="margin-right: 10px;" class="p-bgcolor s-color" prepend-icon="mdi-refresh">
                刷新
            </v-btn>
            <v-btn @click="drawerVisual = true" class="p-bgcolor s-color" prepend-icon="mdi-plus-box">
                新增用户
            </v-btn>
        </div>
        <Table v-if="studentData" :data="studentData" :keys="['stuId', 'stuName', 'stuNumber', 'stuPwd']"
            :header-alias="['ID', '姓名', '学号', '密码']" :cell-style="cellStyle">
        </Table>
    </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from "vue"
import Table from "../components/Table.vue";
import { getAllStudent, createStudent } from "../apis/admin.js"

// 学生数据
let studentData = ref<any>(null)
// 自定义各列单元格样式
let cellStyle = ref<any>(
    {
        2: {
            style: { 'font-weight': 'bold', color: '#1E90FF', height: '25px', 'background-color': '#D4E3F6', padding: '5px 20px', 'border-radius': '25px' }
        }
    }
)
// 获取学生
function getStudent() {
    searchKey.value = ""
    getAllStudent().then(res => {
        studentData.value = res.data
        console.log(studentData.value);

    })
}

// 搜索数据
let searchKey = ref("")
// 点击搜索
function search() {
    if (searchKey.value) {
        studentData.value = studentData.value.filter((item: any) => {
            return item['stuName'].includes(searchKey.value) || item['stuNumber'].includes(searchKey.value)
        })
    } else {
        getStudent()
    }
}



// 抽屉的显示
let drawerVisual = ref(false)

// 添加学生数据
let stuNumber = ref("")
let stuName = ref("")
let stuPwd = ref("")
// 错误提示
let tipMessage = ref("")
// 加载中
let loading = ref(false)
// 点击确定
function confirmCreateStudent() {
    tipMessage.value = ""
    loading.value = true
    if (stuNumber.value && stuName.value && stuPwd.value) {
        createStudent(
            stuNumber.value,
            stuName.value,
            stuPwd.value
        ).then(res => {
            if (res['code'] === 200) {
                getStudent()
                drawerVisual.value = false
                stuNumber.value = ""
                stuName.value = ""
                stuPwd.value = ""
            }
        }).finally(() => {
            loading.value = false
        })
    } else {
        tipMessage.value = "请填写完整信息"
        loading.value = false
    }
}
onMounted(async () => {
    getStudent()
})
</script>

<style lang="less" scoped>
.drawer {
    .title {
        padding: 10px 0px;
        border-bottom: 3px solid var(--border-color);
        margin-bottom: 20px;
    }

    .row {
        padding: 5px 0px;
        display: flex;
        margin-bottom: 5px;

        .label {
            width: 100px;
            height: fit-content;
            line-height: 30px;
            text-align: right;
        }
    }

    input {
        flex: 1;
        margin-left: 5px;
        border: 1px solid #DCDFE6;
        border-radius: 5px;
        padding: 0px 5px;
        outline: none;
        box-sizing: border-box;
        transition: all .3s;
        height: 30px;

        &::placeholder {
            color: #C0C4CC;
        }

        &:focus {
            border: 1px solid var(--primary-color);
        }
    }

    .bottom {
        padding: 10px 0px;
        border-top: 3px solid var(--border-color);
    }
}

.ops,
.search {
    padding: 10px;
    background-color: white;
    border-radius: 5px;
    margin-bottom: 10px;
    display: flex;

    input {
        margin-left: 5px;
        border: 1px solid #DCDFE6;
        border-radius: 5px;
        padding: 0px 5px;
        outline: none;
        box-sizing: border-box;
        transition: all .3s;
        height: 30px;

        &::placeholder {
            color: #C0C4CC;
        }

        &:focus {
            border: 1px solid var(--primary-color);
        }
    }
}

.search {
    padding: 15px;
    height: 60px;

    .label {
        line-height: 30px;
    }
}
</style>