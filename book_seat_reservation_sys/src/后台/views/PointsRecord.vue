<template>
    <div>
        积分记录
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
            <span class="label cell-color">日期：</span>
            <el-select v-model="selectDate" class="m-2" placeholder="请选择">
                <el-option v-for="(item, index) in dates" :key="index" :label="item" :value="item" />
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
        <Table v-if="pointsLogData" :data="pointsLogData"
            :keys="['pl_id', 'stu_name', 'e_name', 'e_img', 'pl_add_less', 'pl_current_amount', 'pl_date', 'pl_type']"
            :header-alias="['ID', '用户姓名', '兑换品名称', '图片', '积分变化', '剩余积分', '日期', '类型']" :cell-style="cellStyle">
        </Table>
    </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from "vue"
import Table from "../components/Table.vue";
import { getAllScoreRecord } from "../apis/admin.js"

// 学生数据
let pointsLogData = ref<any>(null)
// 自定义各列单元格样式
let cellStyle = ref<any>(
    {
        1: {
            style: { color: 'green' }
        },
        2: {
            style: { color: '#1E90FF', height: '25px', 'background-color': '#D4E3F6', padding: '5px 20px', 'border-radius': '25px' },
        },
        3: {
            iconText: "mdi-timer-play",
            color: "#1E90FF",
            style: { color: '#1E90FF', height: '25px', 'background-color': '#D4E3F6', padding: '5px 20px', 'border-radius': '25px' },
            img: true
        },
        4: {
            style: { "font-weight": "bold" }
        },
        5: {
            iconText: "mdi-star-four-points",
            color: "#c5a800",
            style: { color: '#1E90FF', height: '25px', 'background-color': '#D4E3F6', padding: '5px 20px', 'border-radius': '25px' },
        },
        7: {
            style: { color: '#1E90FF', padding: '5px 20px', 'border-radius': '5px' },
        }
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
// 获取积分记录
function getExchange() {
    searchKey.value = ""
    getAllScoreRecord().then(res => {
        pointsLogData.value = res.data.reverse()

        // 处理日期
        handleDate()
    })
}
// 重置所有搜索项
function resetSearch() {
    selectDate.value = ""
    getExchange()
}
// 搜索数据
let searchKey = ref("")
// 点击搜索
function search() {
    // 搜索数据源的每项的ename字段和etype字段
    let data = pointsLogData.value.filter((item: any) => {
        return item['stu_name'].includes(searchKey.value) || item['e_name'].includes(searchKey.value)
    })
    pointsLogData.value = data
}





// 搜索栏功能数据
// 选择日期
let selectDate = ref("")
let dates = ref([])
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
function handleDate() {
    let typeArr = <any>[]
    pointsLogData.value.forEach((item: any) => {
        if (!typeArr.includes(item['pl_date'])) {
            typeArr.push(item['pl_date'])
        }
    })
    dates.value = typeArr
}

// // 搜索
function searchSelect() {

    let data = pointsLogData.value
    pointsLogData.value = null
    if (selectDate.value) {
        data = data.filter((item: any) => {
            return item['pl_date'] == selectDate.value
        })
    }
    pointsLogData.value = data
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