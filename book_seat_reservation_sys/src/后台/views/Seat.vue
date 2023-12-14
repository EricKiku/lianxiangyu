<template>
    <div>
        座位管理
    </div>
    <!-- 抽屉 -->
    <el-drawer class="drawer" v-model="drawerVisual" title="I am the title" :with-header="false"> 
        <div class="title bold fz-18 heiti">新增座位</div>
        <div class="row">
            <div class="label"> <span class="w-color">*</span>楼层：</div>
            <input v-model="addFloor" @keydown.enter="search" type="text" placeholder="请输入楼层">
        </div>
        <div class="row">
            <div class="label"> <span class="w-color">*</span>编号：</div>
            <input v-model="addCode" @keydown.enter="search" type="text" placeholder="请输入座位编号">
        </div>
        <div class="row">
            <div class="label">可否预约：</div>
            <input v-model="addCanBooked" @keydown.enter="search" type="text" placeholder="请输入是否可预约">
        </div>
        <div class="row">
            <div class="label">座位说明：</div>
            <input v-model="addInstruction" @keydown.enter="search" type="text" placeholder="请输入座位详细说明">
        </div>
        <div class="row">
            <div class="label">使用情况：</div>
            <input v-model="addCondition" @keydown.enter="search" type="text" placeholder="请输入座位使用情况">
        </div>
        <div class="w-color">{{ tipMessage }}</div>
        <div class="bottom">
            <v-btn :loading="loading" @click="addSeatF" class="s-color p-bgcolor">确定</v-btn>
        </div>
    </el-drawer>
    <!-- 编辑抽屉 -->
    <el-drawer class="editDrawer" v-model="editDrawerVisual" title="I am the title" :with-header="false">
        <div class="title">编辑座位信息:</div>
        <v-divider :thickness="4"></v-divider>
        <div class="item">
            <span>座位ID：</span>
            <v-chip color="#1E90FF">{{ editSeatData['SId'] }}</v-chip>
        </div>
        <div class="item">
            <span>是否可预约：</span>
            <v-switch v-model="editCanBooked" color="primary" label="是" :value="1" hide-details></v-switch>
        </div>
        <div class="item">
            <span>编号：</span>
            <input type="text" v-model="editCode">
        </div>
        <div class="item">
            <span>楼层：</span>
            <input type="text" v-model="editFloor">
        </div>
        <div class="item">
            <span>说明：</span>
            <input type="text" v-model="editInstruction">
        </div>
        <div class="item">
            <span>可用情况：</span>
            <input type="text" v-model="editCondition">
        </div>
        <v-btn @click="editSeatF" color="green" prepend-icon="mdi-car-brake-low-pressure">确定修改</v-btn>
    </el-drawer>
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
            <!-- 是否可预约下拉框 -->
            <span class="label cell-color">可否预约：</span>
            <el-select v-model="selectCanBooked" class="m-2" placeholder="请选择">
                <el-option v-for="(item, index) in canBooked" :key="index" :label="item['label']" :value="item['value']" />
            </el-select>
            <!-- 楼层下拉框 -->
            <span class="label cell-color">楼层：</span>
            <el-select v-model="selectFloor" class="m-2" placeholder="请选择">
                <el-option v-for="(item, index) in floor" :key="index" :label="item" :value="item" />
            </el-select>
            <!-- 当前状态下拉框 -->
            <span class="label cell-color">当前状态：</span>
            <el-select v-model="selectState" class="m-2" placeholder="请选择">
                <el-option v-for="(item, index) in state" :key="index" :label="item['label']" :value="item['value']" />
            </el-select>
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
            <v-btn @click="drawerVisual = true" class="p-bgcolor s-color" prepend-icon="mdi-plus-box">
                新增座位
            </v-btn>
        </div>
        <Table v-if="seatData" :data="seatData"
            :keys="['SId', 'SCanBeBooked', 'SCode', 'SFloor', 'SInstruction', 'SCondition', 'SState']"
            :header-alias="['ID', '是否可预约', '编号', '楼层', '说明', '可用情况', '当前状态']" :cell-style="cellStyle" :rules="rules"
            :edit="true" :delete1="true" @editFun="edit" @deleteFun="deleteFun">
        </Table>
    </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from "vue"
import Table from "../components/Table.vue";
import { getAllSeat, createStudent, addSeat, updateSeat, deleteSeat } from "../apis/admin.js"

// 学生数据
let seatData = ref<any>(null)
// 自定义各列单元格样式
let cellStyle = ref<any>(
    {
        2: {
            style: { color: '#1E90FF', height: '25px', 'background-color': '#D4E3F6', padding: '5px 20px', 'border-radius': '25px' }
        },
        3: {
            style: { 'font-weight': 'bold' }
        },
        5: {
            style: { color: 'green', height: '25px', }
        }

    }
)
// 自定义规则
let rules = ref<any>({
    6: {
        2: {
            inco: "",
            text: "正在使用",
            style: { color: "white", 'background-color': "#c5a800", width: '100px', display: "inline-block", 'align-items': "center", 'border-radius': '5px', padding: '5px 0px' }
        },
        1: {
            icon: "",
            text: "正在预约",
            style: { color: "white", 'background-color': "#1E90FF", width: '100px', display: "inline-block", 'align-items': "center", 'border-radius': '5px', padding: '5px 0px' }
        },
        0: {
            icon: "",
            text: "空座位",
            style: { color: "white", 'background-color': "green", width: '100px', display: "inline-block", 'align-items': "center", 'border-radius': '5px', padding: '5px 0px' }
        }
    },
    1: {
        0: {
            icon: "mdi-alarm-off",
            color: "#DC143C",
            title: "不可预约"
        },
        1: {
            icon: "mdi-alarm-check",
            color: "green",
            title: "可预约"
        }
    }
})
// 获取座位
function getSeat() {
    searchKey.value = ""
    getAllSeat().then(res => {
        seatData.value = res.data
        // 处理座位
        handleFloor()
    })
}
// 重置所有搜索项
function resetSearch() {
    selectCanBooked.value = ""
    selectFloor.value = ""
    selectState.value = ""
    getSeat()
}
// 搜索数据
let searchKey = ref("")
// 点击搜索
function search() {
    // 搜索源数据的所有项的所有字段
    let data = seatData.value
    seatData.value = null
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
    seatData.value = data
}



// 抽屉的显示
let drawerVisual = ref(false)

// 增加座位抽屉数据
let addFloor = ref("")
let addCode = ref("")
let addCanBooked = ref("")
let addInstruction = ref("")
let addCondition = ref("")
// 错误提示
let tipMessage = ref("")
// 加载中
let loading = ref(false)
// 点击确定
// 调用增加座位接口
function addSeatF() {
    // 其中楼层和编号是必填项
    if (!addFloor.value || !addCode.value) {
        tipMessage.value = "楼层和编号是必填项"
        return
    }
    addSeat(addFloor.value, addCode.value, "", addCanBooked.value, addInstruction.value, addCondition.value).then(res => {
        if (res['code'] === 200) {
            getSeat()
            drawerVisual.value = false
            addFloor.value = ""
            addCode.value = ""
            addCanBooked.value = ""
            addInstruction.value = ""
            addCondition.value = ""
        }
    })
}


// 搜索栏功能数据
// 可预约数据
let selectCanBooked = ref("")
let canBooked = ref([
    {
        label: "可预约",
        value: '1'
    },
    {
        label: "不可预约",
        value: '0'
    }
])
// 楼层
let selectFloor = ref("")
let floor = ref()
// 状态
let selectState = ref("")
let state = ref([
    {
        label: "空座位",
        value: 0
    }, {
        label: "正在预约",
        value: 1
    }, {
        label: "正在使用",
        value: 2
    }
])
// 处理楼层数
function handleFloor() {
    let floorArr = <any>[]
    seatData.value.forEach((item: any) => {
        if (!floorArr.includes(item['SFloor'])) {
            floorArr.push(item['SFloor'])
        }
    })
    floor.value = floorArr
}

// 搜索
function searchSelect() {

    let data = seatData.value
    seatData.value = null
    if (selectCanBooked.value) {
        console.log("搜索可预约");

        data = data.filter((item: any) => {
            return item['SCanBeBooked'] == selectCanBooked.value
        })
    }
    console.log(data);

    console.log("搜索可预约", selectCanBooked.value);

    if (selectFloor.value) {
        data = data.filter((item: any) => {
            return item['SFloor'] == selectFloor.value
        })
    }
    if (selectState.value) {
        data = data.filter((item: any) => {
            return item['SState'] == selectState.value
        })
    }
    console.log(data);

    seatData.value = data
}



// 点击编辑，打开编辑抽屉
let editDrawerVisual = ref(false)
// 编辑数据
let editSeatData = ref<any>(null)
// 是否可预约
let editCanBooked = ref(0)
// 编号
let editCode = ref("")
// 楼层
let editFloor = ref("")
// 说明
let editInstruction = ref("")
// 可用情况
let editCondition = ref("")
function edit(data) {
    editSeatData.value = data
    editDrawerVisual.value = true
    editCanBooked.value = data['SCanBeBooked']
    editCode.value = data['SCode']
    editFloor.value = data['SFloor']
    editInstruction.value = data['SInstruction']
    editCondition.value = data['SCondition']
    console.log(editSeatData.value);

}
// 点击修改
function editSeatF() {
    updateSeat(editSeatData.value['SId'], editCanBooked.value ? 1 : 0, editCode.value, editFloor.value, editInstruction.value, editCondition.value).then(res => {
        if (res['code'] === 200) {
            getSeat()
            editDrawerVisual.value = false
        }
    })
}

// 删除方法
function deleteFun(data) {
    deleteSeat(data['SId']).then(res => {
        if (res['code'] === 200) {
            getSeat()
        }
    })
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