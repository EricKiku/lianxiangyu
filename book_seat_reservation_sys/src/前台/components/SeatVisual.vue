<template>
    <Reservation @getSeatCode="getSeatCode" :seatid="seatid"></Reservation>
    <v-card>
        <v-toolbar color="primary p-bgcolor ">
            <v-toolbar-title class="s-color">座位可视化</v-toolbar-title>
        </v-toolbar>
        <div v-show="loading" class="" style="height: 50px;">
            <v-progress-linear class="w-50 r-all p-color" indeterminate></v-progress-linear>
        </div>
        <div class="d-flex flex-row" v-if="!loading">
            <v-tabs v-model="tab" direction="vertical" color="primary">
                <v-tab v-for="(floor, index) in floors" :key="index" :value="floor" @click="unvisual">

                    <img src="../../assets/icons/floor.png" alt="">
                    {{ floor }}楼
                </v-tab>
            </v-tabs>
            <v-window ref="window" class="flex-1" v-model="tab">
                <v-window-item class="h-auto" v-for="(floor, index) in floors" :key="index" :value="floor">
                    <div class="seatLeyout h-100">
                        <div class="title s-bgcolor r-h text-h text-v  fz-18 radius-10">
                            <span style="margin: 0px 5px;" class="p-color">{{ tab == 1 ? "一" : tab == 2 ? "二" : "三"
                            }}楼</span>
                            -共有 <span class="n-color">{{ seats[tab - 1].seats.length }}</span> 个座位-
                            {{ emptySeats.length }}个可预约座位
                        </div>

                        <div style="height: 25px;display: flex;line-height: 20px;margin-top: 5px;">
                            <v-btn style="height: 100%;width: 70px;" @click="refreshSeat" color="blue"
                                prepend-icon="mdi-refresh">刷新</v-btn>
                            <div
                                style="padding: 0px 5px;border: 1px solid #ccc;border-radius: 5px;display: flex;margin-left: 5px;">
                                <img style="height: 20px;width: 20px;margin-top: 2px;" src="../../assets/icons/chair.png"
                                    alt="" />
                                <span class="r-v">空座位</span>
                            </div>
                            <div
                                style="padding: 0px 5px;border: 1px solid green;border-radius: 5px;display: flex;margin-left: 5px;">
                                <img style="height: 20px;width: 20px;margin-top: 2px;" src="../../assets/icons/chair-1.png"
                                    alt="" />
                                <span class="r-v">正在预约</span>
                            </div>
                            <div
                                style="padding: 0px 5px;border: 1px solid #484848;border-radius: 5px;display: flex;margin-left: 5px;">
                                <img style="height: 20px;width: 20px;margin-top: 2px;" src="../../assets/icons/chair-2.png"
                                    alt="" />
                                <span class="r-v">正在使用</span>
                            </div>
                            <div
                                style="padding: 0px 5px;border: 1px solid red;border-radius: 5px;display: flex;margin-left: 5px;">
                                <img style="height: 20px;width: 20px;margin-top: 2px;"
                                    src="../../assets/icons/chair-warning.png" alt="" />
                                <span class="r-v">不可预约</span>
                            </div>
                        </div>
                        <SeatLayout :heightLightSeatCode="seatCode" @setSeatid="setSeatid" :seats="seats[tab - 1]['seats']">
                        </SeatLayout>
                    </div>
                </v-window-item>
            </v-window>
        </div>
    </v-card>
    <SeatDetain :seatid="seatid"></SeatDetain>
</template>

<script lang="ts" setup>
import { ref, onMounted, computed } from "vue"
import { getSeat } from "../apis/seat"
import SeatLayout from "./SeatLayout.vue";
import SeatDetain from "./SeatDetain.vue";
import Reservation from "./Reservation.vue";
let tab = ref(1)
let window = ref()
// 获取座位
onMounted(() => {
    refreshSeat()
})

// 加载中
let loading = ref(false)
// 所有座位所占的楼层数
let floors = ref(0)

// 所有的座位
let seats = <any>ref()
// 计算属性，没有预约的座位
const emptySeats = computed(() => {
    // 如果seats为空，返回0
    if (Object.keys(seats.value).length == 0) {
        return []
    } else {
        return seats.value[tab.value - 1]['seats'].filter((seat) => {
            return seat['SState'] == 0
        })
    }
})


// 刷新座位数据
const refreshSeat = async () => {
    loading.value = true
    await getSeat().then(res => {
        console.log(res.data)
        //获取楼层数量
        getFloors(res.data)
        // 分组座位
        seats.value = groupByFloor(res.data)
    }).finally(() => {
        loading.value = false
    })
}
// 获取楼层数量
const getFloors = (res) => {
    // 根据res中的SFloor键来划分楼层，返回一个int值
    let arr = <any>[]
    res.forEach((item) => {
        if (!arr.includes(item['SFloor'])) {
            arr.push(item['SFloor'])
        }
    })
    floors.value = arr.length
}
// 遍历所有数据，根据`SFloor`分组，分组对象是{floor:1,seats:[...]}类型

const groupByFloor = (seats) => {
    let groups = {}
    seats.forEach((seat) => {
        const floor = seat['SFloor']
        if (!groups[floor]) {
            groups[floor] = { floor: floor, seats: [] }
        }
        groups[floor].seats.push(seat)
    })

    return Object.values(groups)
}

// 座位布局是否显示
let seatLayout = ref(true)
// 先不显示200ms
function unvisual() {
    seatLayout.value = false
    setTimeout(() => {
        seatLayout.value = true
    }, 200);
}


// 座位id
let seatid = ref(0)
function setSeatid(id) {
    seatid.value = id
}


// 从预约组件传来的座位code
let seatCode = ref("")
// 获取座位的code
function getSeatCode(code) {
    seatCode.value = code
    console.log("高亮座位", code);

}
</script>

<style lang="less" scoped>
.title {}
</style>