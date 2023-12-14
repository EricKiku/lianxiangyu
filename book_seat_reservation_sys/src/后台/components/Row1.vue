<template>
    <div class="row1">
        <div class="item">
            <div class="title">用户总数</div>
            <div class="num">
                <img src="../../assets/icons/adminuser.png" alt="">
                {{ students }}
            </div>
        </div>
        <div class="item">
            <div class="title">今日预约总数</div>
            <div class="num">
                <img src="../../assets/icons/adminorder.png" alt="">
                {{ orders }}
            </div>
        </div>
        <div class="item">
            <div class="title">正在使用座位数</div>
            <div class="num">
                <img src="../../assets/icons/adminuse.png" alt="">
                {{ useings }}
            </div>
        </div>
        <div class="item">
            <div class="title">开通积分用户</div>
            <div class="num">
                <img src="../../assets/icons/adminopen.png" alt="">
                {{ openPoints }}
            </div>
        </div>
    </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from "vue"
import { getAllStudent, getAllRecord, getAllSeat, getOpenScore } from "../apis/admin.js"
import { getDate } from "../../utils/getDate.js"
let students = ref(0)
let orders = ref(0)
let useings = ref(0)
let openPoints = ref(0)
onMounted(async () => {
    getAllStudent().then(res => {
        students.value = res.data.length
    })
    getAllRecord().then(res => {
        // 遍历返回结果，把所有键值rdate的值，和日期对比，一样的累加给orders
        res.data.forEach(item => {
            if (item['r_date'] == getDate()) {
                orders.value++
            }
        })
    })
    getAllSeat().then(res => {
        // console.log(res.data)
        // 如果返回的数据中，键值SState为2，那么useings++
        res.data.forEach(item => {
            if (item['SState'] == 2) {
                useings.value++
            }
        })
    })
    getOpenScore().then(res => {
        openPoints.value = res.data.length
    })
})
</script>

<style lang="less" scoped>
.row1 {
    height: fit-content;
    display: flex;
    justify-content: center;

    .item {
        margin-right: 10px;
        height: 150px;
        background-color: white;
        flex: 1;
        border-radius: 5px;
        padding: 20px;
        box-sizing: border-box;

        .title {
            font-size: 24px;
            font-family: "黑体";
            font-weight: bold;
        }

        .num {
            font-size: 22px;
            margin-top: 20px;
            height: 35px;
            line-height: 35px;
            margin-top: 5px;
            color: var(--primary-color);

            img {
                margin-right: 5px;
                vertical-align: middle;
            }
        }
    }
}
</style>