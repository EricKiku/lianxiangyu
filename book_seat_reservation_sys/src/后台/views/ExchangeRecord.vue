<template>
    <div>
        兑换品记录
    </div>
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
            <span class="label cell-color">兑换品：</span>
            <el-select v-model="selectName" class="m-2" placeholder="请选择">
                <el-option v-for="(item, index) in names" :key="index" :label="item" :value="item" />
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
            <v-btn @click="getExchangeRecord" style="margin-right: 10px;" class="p-bgcolor s-color"
                prepend-icon="mdi-refresh">
                刷新
            </v-btn>
            <!-- <v-btn @click="drawerVisual = true" class="p-bgcolor s-color" prepend-icon="mdi-plus-box">
                新增商品
            </v-btn> -->
        </div>
        <Table v-if="exchangeLogData" :data="exchangeLogData"
            :keys="['el_id', 'e_name', 'e_img', 'stu_name', 'stu_number', 'e_points', 'e_inventory',]"
            :header-alias="['ID', '兑换品名称', '图片', '兑换用户', '学号', '所需积分', '库存']" :cell-style="cellStyle">
        </Table>
    </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from "vue"
import Table from "../components/Table.vue";
import { getAllExchangeRecord } from "../apis/admin.js"

// 学生数据
let exchangeLogData = ref<any>(null)
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
// 获取兑换品记录
function getExchangeRecord() {
    searchKey.value = ""
    getAllExchangeRecord().then(res => {
        exchangeLogData.value = res.data.reverse()
        console.log(exchangeLogData.value);
        console.log(Object.keys(exchangeLogData.value[0]));
        // 任务：给其他几个模块也指定字段顺序，然后在解决key不唯一问题

        // 处理
        handleName()
    })
}
// 重置所有搜索项
function resetSearch() {
    selectName.value = ""
    getExchangeRecord()
}
// 搜索数据
let searchKey = ref("")
// 点击搜索
function search() {
    // 搜索数据源的每项的ename字段和etype字段
    let data = exchangeLogData.value.filter((item: any) => {
        return item['e_name'].includes(searchKey.value) || item['stu_name'].includes(searchKey.value) || item['stu_pwd'].includes(searchKey.value) || item['stu_pwd'].includes(searchKey.value)
    })
    exchangeLogData.value = data
}





// 搜索栏功能数据
// 选择日期
let selectName = ref("")
let names = ref([])

// // 处理兑换品名称
function handleName() {
    let typeArr = <any>[]
    exchangeLogData.value.forEach((item: any) => {
        if (!typeArr.includes(item['e_name'])) {
            typeArr.push(item['e_name'])
        }
    })
    names.value = typeArr
}

// // 搜索
function searchSelect() {

    let data = exchangeLogData.value
    exchangeLogData.value = null
    if (selectName.value) {
        data = data.filter((item: any) => {
            return item['e_name'] == selectName.value
        })
    }
    exchangeLogData.value = data
}















onMounted(async () => {
    getExchangeRecord()
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