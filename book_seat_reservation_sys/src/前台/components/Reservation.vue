<template>
    <v-card class="detail p-bdcolor" variant="outlined">
        <template v-slot:loader="{ isActive }">
            <v-progress-linear :active="isActive" color="#1E90FF" height="4" indeterminate></v-progress-linear>
        </template>
        <v-card class="card mx-auto p-color" width="100%">
            <v-img class="align-end text-white" height="200" src="/src/assets/img/today.jpg" cover>
                <v-card-title class="n-color"> <v-icon icon="mdi-checkbox-marked-circle-auto-outline" /> 今日预约</v-card-title>
            </v-img>
            <v-card-text v-if="today">
                <v-chip class="ma-2" color="blue-darken-1" prepend-icon="mdi-heating-coil">
                    楼层：{{ today['s_floor'] }}
                </v-chip>
                -
                <v-chip @click="clickSeat(today['s_code'])" class="ma-2 pointer" color="blue-darken-1"
                    prepend-icon="mdi-seat-outline">
                    座位号：{{ today['s_code'] }}
                </v-chip>
                -
                <v-chip class="ma-2" color="teal-darken-2" prepend-icon="mdi-timer-play">
                    起始时间：{{ today['r_start_time'] }}
                </v-chip>
                -
                <v-chip class="ma-2" color="orange-darken-3" prepend-icon="mdi-timer-stop">
                    离开时间：{{ today['r_end_time'] }}
                </v-chip>

            </v-card-text>
            <v-card-text v-show="!today">
                <v-chip class="ma-2" color="pink" label>
                    <v-icon start icon="mdi-progress-alert"></v-icon>
                    今天还没有预约
                </v-chip>
                <!-- -- -->
                <v-dialog width="500" transition="dialog-bottom-transition">
                    <template v-slot:activator="{ props }">
                        <v-btn v-show="!today" v-bind="props" class="cancelBtn p-bgcolor s-color"
                            prepend-icon="mdi-bell-plus-outline">
                            立刻预约
                        </v-btn>
                    </template>

                    <template v-slot:default="{ isActive }">
                        <v-card title="预约窗口">
                            <v-card-text>
                                <v-containerSelf>
                                    <v-row>
                                        <v-col cols="12" sm="6">
                                            <span class="i-color">当前选中座位：</span>
                                            <v-chip class="p-color" prepend-icon="mdi-seat-outline">
                                                {{ seatCode || '请选择一个座位' }}
                                            </v-chip>
                                        </v-col>
                                    </v-row>
                                    <v-row>
                                        <v-col cols="12" sm="6">
                                            <v-select color="teal-darken-2" v-model="startTime" label="起始时间" :items="[1]"
                                                outlined>
                                                <template v-slot:item="{ props, item }">
                                                    <div class="p-color">
                                                        选择开始时间:
                                                        <input class="pointer" ref="startTimeDom" v-model="startTime"
                                                            type="time">
                                                    </div>
                                                </template>
                                            </v-select>
                                        </v-col>
                                        <v-col cols="12" sm="6">
                                            <v-select color="orange-darken-3" v-model="endTime" label="离开时间" :items="[1]"
                                                outlined>
                                                <template v-slot:item="{ props, item }">
                                                    <div class="p-color">
                                                        选择结束时间:
                                                        <input class="pointer" ref="endTimeDom" v-model="endTime"
                                                            type="time">
                                                    </div>

                                                </template>
                                            </v-select>
                                        </v-col>
                                    </v-row>
                                </v-containerSelf>
                            </v-card-text>

                            <v-card-actions>
                                <v-spacer>
                                    <v-chip v-show="status == 1" style="margin-left: 10px;"
                                        prepend-icon="mdi-check-decagram" color="success">
                                        预约成功
                                    </v-chip>
                                </v-spacer>
                                <v-spacer>
                                    <v-chip v-show="status == 0" style="margin-left: 10px;"
                                        prepend-icon="mdi-alert-circle-outline" color="error">
                                        {{ reason }}
                                    </v-chip>
                                </v-spacer>
                                <v-btn :loading="loading" class="p-bdcolor p-color" variant="outlined" @click="addRecord"
                                    prepend-icon="mdi-progress-check">
                                    确定
                                </v-btn>
                            </v-card-actions>
                        </v-card>
                    </template>
                </v-dialog>
                <!-- -- -->

            </v-card-text>
            <v-btn v-show="today" class="cancelBtn w-bgcolor s-color" prepend-icon="mdi-bell-cancel-outline">
                取消预约
            </v-btn>

        </v-card>
    </v-card>
</template>

<script lang="ts" setup>
import { onMounted, ref, watchEffect } from "vue"
import { getSeat, addReseRecord, getReseRecord, getSeatById } from "../apis/seat.js"
import { getDate } from "../../utils/getDate.js"
import { useGlobalStore } from "../../global/globalStore.js"
// 路由
import { useRouter } from "vue-router"
// 接收路由参数
import { useRoute } from "vue-router"
const router = useRouter()
const route = useRoute()
// 接收座位id
let props = defineProps(['seatid'])
let emits = defineEmits(['getSeatCode'])
const globalStore = useGlobalStore()

// 今日预约信息
let today = <any>ref(null)



let seatCode = ref(null)

// 预约起始时间
let startTime = <any>ref(null)
// 预约离开时间
let endTime = <any>ref(null)

onMounted(() => {
    // 如果没有student，就跳转到登录页面
    if (Object.keys(globalStore.getStudent()).length === 0) {
        router.push({ path: '/login' })
        return
    }
    // 根据stuId和日期获取预约信息
    getToday()
})
// 获取今日本学生预约信息
function getToday() {
    getReseRecord(globalStore.getStudent()['stuId'], getDate()).then(res => {
        console.log(res);
        if (res['code'] == 200) {
            today.value = res['data'][0]
        }
    })
}

// 获取座位信息
function getSeatF(id) {
    console.log("获取座位的id:", id);

    getSeatById(id).then(res => {
        console.log(res.data);
        // 如果id为空，则不执行
        if (!res.data) {
            return
        }
        seatCode.value = res.data['SCode']
    })
}


// 发送增加请求
// 加载变量
let loading = ref(false)
// 预约成功失败变量
let status = <any>ref(-1)
// 失败原因
let reason = <any>ref(null)
function addRecord() {
    status.value = -1
    // 如果seatCode为空，则不执行
    if (!seatCode.value) {
        return
    }
    if (!startTime.value || !endTime.value) {
        return
    }
    // 如果store的student不存在，就跳转到登录页面
    if (Object.keys(globalStore.getStudent()).length === 0) {
        router.push({ path: '/login' })
        return
    }
    loading.value = true
    addReseRecord(
        props.seatid,
        globalStore.getStudent()['stuId'],
        startTime.value,
        endTime.value,
        getDate()
    ).then(res => {
        if (res['code'] == 200) {
            status.value = 1
            reason.value = null
            // 重新获取今日预约信息
            getToday()
        } else {
            status.value = 0
            reason.value = res['msg']
        }
        console.log(res);
    }).finally(() => {
        loading.value = false
    })

}


// 点击让座位高亮显示
function clickSeat(code) {
    emits('getSeatCode', '')
    setTimeout(() => {
        emits('getSeatCode', code)
    }, 100);
}

watchEffect(() => {
    getSeatF(props.seatid)
})
</script>

<style lang="less" scoped>
.detail {
    padding: 15px;
    box-sizing: border-box;


    .today {
        padding: 10px;
        box-sizing: border-box;
    }

    .cancelBtn {
        margin: 0px 0px 10px 10px;
    }
}
</style>