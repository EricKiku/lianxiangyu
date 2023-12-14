<template>
    <div class="seat flex-h w-100 h-100">
        <div class="notice between">
            <div class="title">公告</div>
            <div class="content">
                <div class="item" v-for="(notice, index) in notices" :key="index">
                    <img src="../../assets/icons/notice.png" alt="">
                    {{ notice }}
                </div>
            </div>
        </div>
        <div class="containerSelf">
            <SeatVisual></SeatVisual>
        </div>
        <div class="notification between">
            <div class="title">通知</div>
            <div class="content">
                <div class="item" v-for="(notification, index) in notifications" :key="index">
                    <img src="../../assets/icons/notification.png" alt="">
                    {{ notification }}
                </div>
            </div>
        </div>
    </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from "vue"
import SeatVisual from "../components/SeatVisual.vue"
import { addSeat } from "../apis/seat.js"
import { getAllGg, getAllTz } from "../../后台/apis/admin.js"
// 公告
let notices = ref([])

// 通知
let notifications = ref([])
onMounted(() => {
    getAllGg().then(res => {
        notices.value = res.data.map(item => item.g_content)
    })
    getAllTz().then(res => {
        notifications.value = res.data.map(item => item.n_content)
    })

})
// 添加座位方法
// const addSeatf = async () => {
//     let ares = ["A", "B", "C", "D", "E", "F"]
//     // 循环，有三楼层，每层有6个区，分别是ABCDEF区，每个区有10个座位
//     let i = 3
//     for (let j = 0; j <= 5; j++) {
//         for (let k = 1; k <= 10; k++) {
//             // addSeat(i, `${j}${k}`)
//             // seats.push(`${i}${ares[j]}-${k}`)
//             await addSeat(i, `${i}${ares[j]}-${k}`).then(res => {
//                 console.log(res)
//             })

//         }
//     }
// }
</script>

<style lang="less" scoped>
.seat {
    padding: 20px;
    box-sizing: border-box;

    .between {
        width: 220px;
        background-color: white;
        margin: 0px 15px;
        height: fit-content;
        border-radius: 10px;

        .title {
            text-align: center;
            font-size: 20px;
            font-weight: bold;
            padding: 10px;
            box-sizing: border-box;
            border-bottom: 1px solid #e0e0e0;

        }

        .item {
            padding: 10px;
            box-sizing: border-box;
            border-bottom: 1px solid #e0e0e0;
            display: flex;
            align-items: center;

            img {
                width: 20px;
                height: 20px;
                margin-right: 10px;
            }

            &:last-child {
                border-bottom: none;
            }
        }
    }

    .notice {}

    .containerSelf {
        background-color: white;
        border-radius: 15px;
        width: 50%;
        min-height: 99%;
        height: fit-content;
        padding: 20px;
    }

    .notification {}
}
</style>