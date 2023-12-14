<template>
    <div>
        积分管理
    </div>
    <div class="data">
        <div class="search">
            <span class="label cell-color">搜索
                <v-tooltip text="可按姓名或学号搜索" location="top">
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
            <v-divider style="margin: 0px 5px 0px 0px;" class="border-opacity-25" vertical></v-divider>
            <!-- 楼层下拉框 -->
            <!-- <span class="label cell-color">类型：</span>
            <el-select v-model="selectType" class="m-2" placeholder="请选择">
                <el-option v-for="(item, index) in types" :key="index" :label="item" :value="item" />
            </el-select> -->
            <!-- 当前状态下拉框 -->
            <!-- <span class="label cell-color">当前状态：</span>
            <el-select v-model="selectState" class="m-2" placeholder="请选择">
                <el-option v-for="(item, index) in state" :key="index" :label="item['label']" :value="item['value']" />
            </el-select> -->
            <!-- <v-btn style="height: 30px;margin: 0px 5px;" class="p-bgcolor s-color" prepend-icon="mdi-magnify"
                @click="searchSelect">
                搜索
            </v-btn> -->
            <v-btn variant="outlined" style="height: 30px;margin: 0px 5px;" class="cell-color p-bdcolor s-bgcolor n-color"
                prepend-icon="mdi-trash-can-outline" @click="resetSearch">
                重置
            </v-btn>
            <v-tooltip text="搜索项从左至右依次筛选，结果保留至下一次搜索的源数据。使用[重置]清空搜索缓存" location="top">
                <template v-slot:activator="{ props }">
                    <v-icon v-bind="props" color="#606266" icon="mdi-help-circle-outline" />
                </template>
            </v-tooltip>
        </div>
        <div class="ops">
            <v-btn @click="getExchange" style="margin-right: 10px;" class="p-bgcolor s-color" prepend-icon="mdi-refresh">
                刷新
            </v-btn>
            <!-- <v-btn @click="drawerVisual = true" class="p-bgcolor s-color" prepend-icon="mdi-plus-box">
                新增商品
            </v-btn> -->
        </div>
        <Table v-if="pointsData" :data="pointsData" :keys="['p_id', 'stu_name', 'stu_number', 'p_amount']"
            :header-alias="['ID', '姓名', '学号', '拥有积分']" :cell-style="cellStyle">
        </Table>
    </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from "vue"
import Table from "../components/Table.vue";
import { getOpenScore } from "../apis/admin.js"

// 学生数据
let pointsData = ref<any>(null)
// 自定义各列单元格样式
let cellStyle = ref<any>(
    {
        3: {
            iconText: "mdi-star-four-points",
            color: "#c5a800",
            style: { color: '#1E90FF', height: '25px', 'background-color': '#D4E3F6', padding: '5px 20px', 'border-radius': '25px', 'font-weight': 'bold', 'font-size': '20px' }
            // img: true
        },
    }
)
// 自定义规则
// let rules = ref<any>({
//     2: {
//         1: {
//             icon: "",
//             text: "已完成",
//             style: { color: "white", 'background-color': "#1E90FF", width: '100px', display: "inline-block", 'align-items': "center", 'border-radius': '5px', padding: '5px 0px' }
//         },
//     }
// })
// 获取座位
function getExchange() {
    searchKey.value = ""
    getOpenScore().then(res => {
        pointsData.value = res.data.reverse()
        console.log(pointsData.value);
        console.log(Object.keys(pointsData.value[0]));
        // 任务：给其他几个模块也指定字段顺序，然后在解决key不唯一问题

        // 处理日期
        handleType()
    })
}
// 重置所有搜索项
function resetSearch() {
    selectType.value = ""
    getExchange()
}
// 搜索数据
let searchKey = ref("")
// 点击搜索
function search() {
    // 搜索数据源的每项的ename字段和etype字段
    let data = pointsData.value.filter((item: any) => {
        return item['stu_name'].includes(searchKey.value) || item['stu_number'].includes(searchKey.value)
    })
    pointsData.value = data
}





// 搜索栏功能数据
// 选择日期
let selectType = ref("")
let types = ref([])
// // 处理兑换品类型
function handleType() {
    let typeArr = <any>[]
    pointsData.value.forEach((item: any) => {
        if (!typeArr.includes(item['etype'])) {
            typeArr.push(item['etype'])
        }
    })
    types.value = typeArr
}

// // 搜索
function searchSelect() {

    let data = pointsData.value
    pointsData.value = null
    if (selectType.value) {
        data = data.filter((item: any) => {
            return item['etype'] == selectType.value
        })
    }
    pointsData.value = data
}















onMounted(async () => {
    getExchange()
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
    height: fit-content;
    display: flex;
    flex-wrap: wrap;

    .label {
        line-height: 30px;
        margin-left: 5px;
    }

    .my-select .v-select__selections {
        height: 30px;
        padding: 0;
    }

    .my-select .v-select__selection {
        line-height: 30px;
    }
}
</style>