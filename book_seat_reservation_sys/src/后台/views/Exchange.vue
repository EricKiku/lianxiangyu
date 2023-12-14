<template>
    <div>
        兑换品管理
    </div>
    <!-- 编辑抽屉 -->
    <el-drawer class="editDrawer" v-model="editDrawerVisual" title="I am the title" :with-header="false">
        <div class="title">编辑座位信息:</div>
        <v-divider :thickness="4"></v-divider>
        <div class="item">
            <span>兑换品ID：</span>
            <v-chip color="#1E90FF">{{ editId }}</v-chip>
        </div>
        <div class="item">
            <span>兑换品名称：</span>
            <input type="text" v-model="editName">
        </div>
        <div class="item">
            <span>所需积分：</span>
            <input type="text" v-model="editPoints">
        </div>
        <div class="item">
            <span>库存：</span>
            <input type="text" v-model="editInventory">
        </div>
        <div class="item">
            <span>描述：</span>
            <input type="text" v-model="editDescription">
        </div>
        <div class="item">
            <span>类型：</span>
            <input type="text" v-model="editType">
        </div>
        <v-btn @click="editFun" color="green" prepend-icon="mdi-car-brake-low-pressure">确定修改</v-btn>
    </el-drawer>
    <div class="data">
        <div class="search">
            <span class="label cell-color">搜索
                <v-tooltip text="只可搜索兑换品名称和类型" location="top">
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
            <span class="label cell-color">类型：</span>
            <el-select v-model="selectType" class="m-2" placeholder="请选择">
                <el-option v-for="(item, index) in types" :key="index" :label="item" :value="item" />
            </el-select>
            <!-- 当前状态下拉框 -->
            <!-- <span class="label cell-color">当前状态：</span>
            <el-select v-model="selectState" class="m-2" placeholder="请选择">
                <el-option v-for="(item, index) in state" :key="index" :label="item['label']" :value="item['value']" />
            </el-select> -->
            <v-btn style="height: 30px;margin: 0px 5px;" class="p-bgcolor s-color" prepend-icon="mdi-magnify"
                @click="searchSelect">
                搜索
            </v-btn>
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
        <Table v-if="exchangeData" :data="exchangeData"
            :keys="['eid', 'ename', 'eimg', 'epoints', 'einventory', 'edescription', 'etype']"
            :header-alias="['ID', '兑换品名称', '图片', '所需积分', '库存', '描述', '类型']" :cell-style="cellStyle" :edit="true"
            :delete1="true" @edit-fun="edit" @delete-fun="deleteFun">
        </Table>
    </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from "vue"
import Table from "../components/Table.vue";
import { getAllExchange, updateExchange, deleteExchange } from "../apis/admin.js"

// 学生数据
let exchangeData = ref<any>(null)
// 自定义各列单元格样式
let cellStyle = ref<any>(
    {
        1: {
            style: { color: '#1E90FF', height: '25px', 'background-color': '#D4E3F6', padding: '5px 20px', 'border-radius': '25px' },
        },
        2: {
            iconText: "mdi-timer-play",
            color: "#1E90FF",
            style: { color: '#1E90FF', height: '25px', 'background-color': '#D4E3F6', padding: '5px 20px', 'border-radius': '25px' },
            img: true
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
    getAllExchange().then(res => {
        exchangeData.value = res.data.reverse()
        console.log(exchangeData.value);
        console.log(Object.keys(exchangeData.value[0]));
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
    let data = exchangeData.value.filter((item: any) => {
        return item['ename'].includes(searchKey.value) || item['etype'].includes(searchKey.value)
    })
    exchangeData.value = data
}





// 搜索栏功能数据
// 选择日期
let selectType = ref("")
let types = ref([])
// // 楼层
// let selectFloor = ref("")
// let floor = ref()
// // 状态
// let selectState = ref("")
// let state = ref([
//     {
//         label: "空座位",
//         value: 0
//     }, {
//         label: "正在预约",
//         value: 1
//     }, {
//         label: "正在使用",
//         value: 2
//     }
// ])
// // 处理兑换品类型
function handleType() {
    let typeArr = <any>[]
    exchangeData.value.forEach((item: any) => {
        if (!typeArr.includes(item['etype'])) {
            typeArr.push(item['etype'])
        }
    })
    types.value = typeArr
}

// // 搜索
function searchSelect() {

    let data = exchangeData.value
    exchangeData.value = null
    if (selectType.value) {
        data = data.filter((item: any) => {
            return item['etype'] == selectType.value
        })
    }
    exchangeData.value = data
}



// 点击编辑，打开编辑抽屉
let editDrawerVisual = ref(false)
let editId = ref()
let editName = ref()
let editPoints = ref()
let editInventory = ref()
let editDescription = ref()
let editType = ref()

function edit(data) {
    console.log(data);
    editId.value = data['eid']
    editName.value = data['ename']
    editPoints.value = data['epoints']
    editInventory.value = data['einventory']
    editDescription.value = data['edescription']
    editType.value = data['etype']
    editDrawerVisual.value = true


}

// 修改方法
function editFun() {
    updateExchange(editId.value, editName.value, editPoints.value, editInventory.value, editDescription.value, editType.value).then(res => {
        console.log(res);
        if (res['code'] == "200") {
            editDrawerVisual.value = false
            getExchange()
        }
    })
}

// 删除兑换品方法
function deleteFun(data) {
    deleteExchange(data['eid']).then(res => {
        console.log(res);
        if (res['code'] == "200") {
            getExchange()
        }
    })
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

.editDrawer {
    .title {
        padding: 10px 0px;
        font-weight: bold;
    }

    .item {
        border: 1px solid var(--border-color);
        height: fit-content;
        margin: 5px 0px;
        padding: 10px;
        box-sizing: border-box;

        // display: flex;
        input {
            width: 100%;
            height: 30px;
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