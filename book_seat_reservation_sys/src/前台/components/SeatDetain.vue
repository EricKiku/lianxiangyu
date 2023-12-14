<template>
    <v-card :loading="loading" class="detail flex-row p-bdcolor" variant="outlined">
        <template v-slot:loader="{ isActive }">
            <v-progress-linear :active="isActive" color="#1E90FF" height="4" indeterminate></v-progress-linear>
        </template>
        <div class="fz-24 heiti p-color text-h" v-show="!seatInfo">
            请点击座位查看详细信息
        </div>
        <div class="details flex-1" style="height: 375px;" v-show="!seatInfo"></div>
        <div class="details flex-1" v-if="seatInfo">
            <div class="title fz-20"><v-icon class="p-color" icon="mdi-seat-outline" />详细数据</div>
            <div class="content">
                <div class="item">
                    <v-icon class="p-color" icon="mdi-star-three-points" />
                    编号:
                    <v-chip class="s-bgcolor p-bdcolor" prepend-icon="mdi-pound" variant="outlined">
                        <span class="bold p-color"> {{ seatInfo['SCode'] }}</span>
                    </v-chip>
                </div>
                <div class="item">
                    <v-icon class="p-color" icon="mdi-star-three-points" />
                    楼层:
                    <v-chip class="s-bgcolor i-bdcolor" prepend-icon="mdi-heating-coil" variant="outlined">
                        <span class="bold p-color"> {{ seatInfo['SFloor'] }}楼</span>
                    </v-chip>
                </div>
                <div class="item">
                    <v-icon class="p-color" icon="mdi-star-three-points" />
                    是否可预约:
                    <v-chip v-show="seatInfo['SCanBeBooked'] == 1" class="ma-2" color="success" variant="outlined">
                        <v-icon start icon="mdi-timeline-check"></v-icon>
                        可以预约
                    </v-chip>
                    <v-chip v-show="seatInfo['SCanBeBooked'] == 0" class="ma-2" color="#DC143C" variant="outlined">
                        <v-icon start icon="mdi-timeline-remove"></v-icon>
                        不可预约
                    </v-chip>
                </div>
                <div class="item">
                    <v-icon class="p-color" icon="mdi-star-three-points" />
                    使用情况:
                    <v-chip :variant="seatInfo['SState'] == 0 ? 'elevated' : 'outlined'"
                        :color="seatInfo['SState'] == 0 ? '#1E90FF' : ''">
                        空座位
                    </v-chip>

                    <v-chip :variant="seatInfo['SState'] == 1 ? 'elevated' : 'outlined'"
                        :color="seatInfo['SState'] == 1 ? '#1E90FF' : ''">
                        已被预约
                    </v-chip>

                    <v-chip :variant="seatInfo['SState'] == 2 ? 'elevated' : 'outlined'"
                        :color="seatInfo['SState'] == 2 ? '#1E90FF' : ''">
                        已被占用
                    </v-chip>
                </div>
                <div class="item">
                    <v-icon class="p-color" icon="mdi-star-three-points" />
                    座位说明:
                    <span class="n-color"> {{ seatInfo['SInstruction'] }}</span>
                </div>
                <div class="item">
                    <v-icon class="p-color" icon="mdi-star-three-points" />
                    座位情况:
                    <span class=""> {{ seatInfo['SCondition'] }}</span>
                </div>
            </div>
        </div>
        <!-- 记录 -->
        <div class="record flex-1" v-if="isSearch">
            <div class="title title fz-20"><v-icon class="p-color" icon="mdi-table-clock" />预约记录
                <v-chip class="fz-14 i-bgcolor">
                    只显示当天记录
                </v-chip>
            </div>
            <div class="content scroll-y" style="height: 340px;">
                <v-table class="text-h" fixed-header height="330px">
                    <thead>
                        <tr>
                            <th class="text-center">
                                预约开始时间
                            </th>
                            <th class="text-center">
                                预约结束时间
                            </th>

                            <th class="text-center">
                                离开时间
                            </th>
                            <th class="text-center">
                                状态
                            </th>
                        </tr>
                    </thead>
                    <tbody v-show="isSearch && reserverRecord.length == 0">
                        <tr>
                            <td colspan="4" class="text-center">
                                <v-icon class="p-color" icon="mdi-table-large"></v-icon>
                                暂无记录
                            </td>
                        </tr>
                    </tbody>
                    <tbody v-show="isSearch && reserverRecord.length > 0">
                        <tr v-for="item in reserverRecord" :key="item['r_id']">
                            <td class="text-align">{{ item['r_start_time'] }}</td>
                            <td class="text-align">{{ item['r_end_time'] }}</td>
                            <td class="text-align">{{ item['r_leave_time'] }}</td>
                            <td v-if="item['r_complete'] == 1" class="text-align">
                                <v-chip color="green" prepend-icon="mdi-alarm-check">
                                    已完成
                                </v-chip>
                            </td>

                            <td v-else class="text-align">

                                <v-chip class="p-color" v-show="item['s_state'] == 1" prepend-icon="mdi-alarm">
                                    正在预约
                                </v-chip>
                                <v-chip color="success" v-show="item['s_state'] == 2" prepend-icon="mdi-account-convert">
                                    正在使用
                                </v-chip>
                            </td>
                        </tr>
                    </tbody>
                </v-table>
            </div>
        </div>
    </v-card>
</template>

<script lang="ts" setup>
import { ref, onMounted, watchEffect } from "vue"
import { getSeatById, getRecordByIdAndDate } from "../apis/seat"
import { getDate } from "../../utils/getDate.js"
let props = defineProps({
    seatid: {
        type: Number,
        required: true
    }
})
let seatid = ref(0)
let loading = ref(false)

// 座位信息
let seatInfo = ref()
// 获取座位详情
const getSeatDetail = async (seatid) => {
    loading.value = true
    await getSeatById(seatid).then(res => {
        seatInfo.value = res['data']
    }).finally(() => {
        loading.value = false
    })
}
// 获取座位的预约记录
const getSeatRecord = (seatid) => {
    isSearch.value = false
    reserverRecord.value = []
    getRecordByIdAndDate(seatid, getDate()).then(res => {
        if (res['code'] == 200) {
            reserverRecord.value = res['data']
            isSearch.value = true
        }
    }).finally(() => {
        isSearch.value = true
    })
}

// 预约记录表格
let reserverRecord = ref([])
// 是否查询完成
let isSearch = ref(false)


watchEffect(() => {
    seatid.value = props.seatid
    seatInfo.value = null
    if (seatid.value != 0) {

        getSeatDetail(seatid.value)
        getSeatRecord(seatid.value)
    }
})
</script>

<style lang="less" scoped>
.detail {
    margin: 10px 0px;
    // padding: 10px; 
    box-sizing: border-box;
}

.item {
    margin: 5px 0px;
    margin-left: 15px;
}
</style>                   