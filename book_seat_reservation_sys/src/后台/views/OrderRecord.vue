<template>
    <div>
        预约记录
    </div>
    <div class="data">
        <div class="search">
            <span class="label cell-color">搜索
                <v-tooltip text="可搜索所有,并且搜索结果叠加" location="top">
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
            <!-- 日期下拉框 -->
            <span class="label cell-color">选择日期：</span>
            <el-select v-model="selectDate" class="m-2" placeholder="请选择">
                <el-option v-for="(item, index) in dates" :key="index" :label="item" :value="item" />
            </el-select>
            <!-- 楼层下拉框 -->
            <!-- <span class="label cell-color">楼层：</span>
            <el-select v-model="selectFloor" class="m-2" placeholder="请选择">
                <el-option v-for="(item, index) in floor" :key="index" :label="item" :value="item" />
            </el-select> -->
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
            <v-btn @click="getSeat" style="margin-right: 10px;" class="p-bgcolor s-color" prepend-icon="mdi-refresh">
                刷新
            </v-btn>
            <!-- <v-btn @click="drawerVisual = true" class="p-bgcolor s-color" prepend-icon="mdi-plus-box">
                新增座位
            </v-btn> -->
        </div>
        <Table v-if="recordData" :data="recordData"
            :keys="['r_id', 's_code', 'stu_id', 'r_start_time', 'r_end_time', 'r_enter_time', 'r_leave_time', 'r_date', 'r_state', 'r_complete']"
            :header-alias="['ID', '座位编号', '用户iD', '预约开始时间', '预约结束时间', '进入时间', '离开时间', '预约时间', '状态', '是否完成']"
            :cell-style="cellStyle" :rules="rules">
        </Table>
    </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from "vue"
import Table from "../components/Table.vue";
import { getAllRecord } from "../apis/admin.js"

// 学生数据
let recordData = ref<any>(null)
// 自定义各列单元格样式
let cellStyle = ref<any>(
    {
        3: {
            iconText: "mdi-timer-play",
            color: "#1E90FF",
            style: { color: '#1E90FF', height: '25px', 'background-color': '#D4E3F6', padding: '5px 20px', 'border-radius': '25px' }
        },
        4: {
            iconText: "mdi-timer-stop",
            color: "#c5a800",
            style: { color: '#1E90FF', height: '25px', 'background-color': '#D4E3F6', padding: '5px 20px', 'border-radius': '25px' }
        },
    }
)
// 自定义规则
let rules = ref<any>({
    8: {
        0: {
            icon: "",
            color: "",
            title: "",
            text: "正在预约"
        },
        1: {
            icon: "",
            color: "",
            title: "",
            text: "正在使用"
        },
        2: {
            icon: "",
            color: "",
            title: "",
            text: "--"
        }
    },
    9: {
        1: {
            icon: "",
            text: "已完成",
            style: { color: "white", 'background-color': "#1E90FF", width: '100px', display: "inline-block", 'align-items': "center", 'border-radius': '5px', padding: '5px 0px' }
        },
        0: {
            icon: "",
            text: "未完成",
            style: { color: "white", 'background-color': "#ccc", width: '100px', display: "inline-block", 'align-items': "center", 'border-radius': '5px', padding: '5px 0px' }
        }
    }
})
// 获取座位
function getSeat() {
    searchKey.value = ""
    getAllRecord().then(res => {
        recordData.value = res.data.reverse()
        console.log(recordData.value);
        console.log(Object.keys(recordData.value[0]));
        // 任务：给其他几个模块也指定字段顺序，然后在解决key不唯一问题

        // 处理日期
        handleDate()
    })
}
// 重置所有搜索项
function resetSearch() {
    selectDate.value = ""
    getSeat()
}
// 搜索数据
let searchKey = ref("")
// 点击搜索
function search() {
    // 搜索源数据的所有项的所有字段
    let data = recordData.value
    recordData.value = null
    if (searchKey.value) {
        data = data.filter((item: any) => {
            for (let key in item) {
                if (item[key].toString().includes(searchKey.value)) {
                    return true
                }
            }
            return false
        })
    }
    recordData.value = data
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
// // 处理日期数
function handleDate() {
    let floorArr = <any>[]
    recordData.value.forEach((item: any) => {
        if (!floorArr.includes(item['r_date'])) {
            floorArr.push(item['r_date'])
        }
    })
    dates.value = floorArr
}

// // 搜索
function searchSelect() {

    let data = recordData.value
    recordData.value = null
    if (selectDate.value) {
        data = data.filter((item: any) => {
            return item['r_date'] == selectDate.value
        })
    }
    recordData.value = data
}















onMounted(async () => {
    getSeat()
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