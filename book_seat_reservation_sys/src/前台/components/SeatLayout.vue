<template>
    <div class="containerSelf">
        <div class="area flex-column" v-for="(area, index) in  areas " :key="index">
            <div class="flex-row">
                <div @click="clickChair(chair['SId'], chair['SCode'])" @mouseenter="touchChair('enter', chair['SCode'])"
                    @mouseleave="touchChair('leave', chair['SCode'])" class="chair flex-column"
                    :class="{ 'p-color': touchCode == chair['SCode'], 'p-bdcolor': touchCode == chair['SCode'] }"
                    v-for="(chair, index) in  seatsGroup[area].slice(0, seatsGroup[area].length / 2) " :key="index">

                    <span style="display: flex;flex-direction: column;" v-if="chair['SCanBeBooked'] == 0">
                        <img class="r-h" src="../../assets/icons/chair-warning.png" alt="">
                        {{ chair['SCode'] }}
                    </span>
                    <span style="display: flex;flex-direction: column;" v-else>
                        <img class="r-h" v-show="chair['SState'] == 1" src="../../assets/icons/chair-1.png" alt="">
                        <img class="r-h" v-show="chair['SState'] == 2" src="../../assets/icons/chair-2.png" alt="">

                        <img v-show="touchCode != chair['SCode'] && selectedSeat != chair['SCode'] && chair['SState'] == 0"
                            class="r-h" src="../../assets/icons/chair.png" alt="">


                        <img v-show="(touchCode == chair['SCode'] || selectedSeat == chair['SCode']) && chair['SState'] == 0"
                            class="r-h" src="../../assets/icons/chair-touch.png" alt="">
                        {{ chair['SCode'] }}
                    </span>



                </div>
            </div>
            <div class="text-h fz-24 p-color">{{ area }}区</div>
            <div class="flex-row">
                <div @click="clickChair(chair['SId'], chair['SCode'])" @mouseenter="touchChair('enter', chair['SCode'])"
                    @mouseleave="touchChair('leave', chair['SCode'])" class="chair flex-column"
                    :class="{ 'p-color': touchCode == chair['SCode'], 'p-bdcolor': touchCode == chair['SCode'] }"
                    v-for="( chair, index ) in  seatsGroup[area].slice(seatsGroup[area].length / 2) " :key="index">

                    <img class="r-h" v-show="chair['SState'] == 1" src="../../assets/icons/chair-1.png" alt="">
                    <img class="r-h" v-show="chair['SState'] == 2" src="../../assets/icons/chair-2.png" alt="">

                    <img v-show="touchCode != chair['SCode'] && selectedSeat != chair['SCode'] && chair['SState'] == 0"
                        class="r-h" src="../../assets/icons/chair.png" alt="">


                    <img v-show="(touchCode == chair['SCode'] || selectedSeat == chair['SCode']) && chair['SState'] == 0"
                        class="r-h" src="../../assets/icons/chair-touch.png" alt="">
                    {{ chair['SCode'] }}
                </div>
            </div>

        </div>
    </div>
</template>

<script lang="ts" setup>
import { onMounted, ref, watchEffect } from "vue"
let props = defineProps({
    // 传入的座位信息
    seats: {
        type: Object,
        required: true
    },
    heightLightSeatCode: {
        type: String,
        default: ""
    }
})
let emits = defineEmits(['setSeatid'])
let seats = <any>ref([])
watchEffect(() => {
    seats.value = []
    seats.value = props.seats
})
// 所有区域
let areas = <any>ref([])

function getAreas() {
    // 遍历props.seats,获取键SCode的第二个字符，即是分区名
    seats.value.forEach((item: { [x: string]: any[]; }) => {
        if (!areas.value.includes(item['SCode'][1])) {
            areas.value.push(item['SCode'][1])
        }
    })
}

// 分组座位
let seatsGroup = <any>ref({})
// 根据区域划分座位，返回对象数组，键为区域名，值为座位数组
function groupByArea() {
    let res = <any>{}
    areas.value.forEach((area: string | number) => {
        res[area] = getAreaSeats(area)
    })
    return res
}
// 获取某个区域的座位
function getAreaSeats(area: any) {
    return seats.value.filter((seat: { [x: string]: any[]; }) => {
        return seat['SCode'][1] == area
    })
}


// 鼠标触摸方法
let touchCode = ref(props.heightLightSeatCode || "")
function touchChair(action: string, code: string) {
    if (action == 'enter') {
        touchCode.value = code
    } else if (action == 'leave') {
        touchCode.value = ""
    }

}
// 选中座位
let selectedSeat = ref("")

// 点击座位，触发父组件的方法
function clickChair(id: any, code: any) {
    emits('setSeatid', id)
    selectedSeat.value = code
}

onMounted(() => {
    getAreas()
    seatsGroup.value = groupByArea()
})
// 监听props.heightLightSeatCode变化，如果变化，就高亮座位
watchEffect(() => {
    touchCode.value = props.heightLightSeatCode
})
</script>

<style lang="less" scoped>
.opcity {
    opacity: 0.8;
}

.containerSelf {
    display: flex;
    justify-content: center;
    flex-wrap: wrap;
    padding: 10px;
    margin: 10px 0px;
    box-sizing: border-box;
    border: 0.2px solid var(--second-color);
    border-radius: 5px;

    .area {
        flex: 0 0 45%;
        box-sizing: border-box;
        padding: 10px;
        margin: 10px;
        box-sizing: border-box;
        box-shadow: rgba(17, 17, 26, 0.05) 0px 4px 16px, rgba(17, 17, 26, 0.05) 0px 8px 32px;
        border-radius: 5px;

        .chair {
            width: fit-content;
            height: fit-content;
            border: 2px solid var(--border-color);
            margin: 5px;
            padding: 5px;
            box-sizing: border-box;
            text-align: center;
            border-radius: 5px;
            cursor: pointer;
            transition: all 0.2s ease-in-out;

            img {
                height: 30px;
                width: 30px;
            }
        }
    }
}
</style>