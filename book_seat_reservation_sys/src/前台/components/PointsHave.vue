<template>
    <div class="pointshave">
        <div class="points_amount flex-row p-bdcolor radius-10">
            <img src="../../assets/icons/points.png" alt="">
            <div class="amount p-color bold">{{ amount }}</div>
            <div class="detailBtn">
                <v-btn @click="getExchangeLogF" class="p-bgcolor s-color" text="兑换记录"></v-btn>
                <v-btn @click="getPointsLogF" class="p-bgcolor s-color" text="积分明细"></v-btn>
            </div>
        </div>
        <v-dialog width="500" height="600" v-model="logDia">
            <v-card class="card">
                <div class="title">兑换记录</div>
                <v-table fixed-header height="350px">
                    <thead>
                        <tr>
                            <th class=" p-bgcolor s-color">
                                <span class="r-h" style="margin-left: -20px;">兑换物</span>
                            </th>
                            <th class=" p-bgcolor s-color">
                                <span class="r-h" style="margin-left: -20px;">是否领取</span>
                            </th>
                            <th class=" p-bgcolor s-color">
                                <span class="r-h" style="margin-left: -20px;">操作</span>
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="log in exchangelogList" :key="log['el_id']">
                            <td class="text-align p-color">{{ log['e_name'] }}</td>
                            <td>
                                <v-chip class="r-h w-color" v-show="log['e_get'] == 0">
                                    未领取
                                </v-chip>
                                <v-chip class="r-h" color="success" v-show="log['e_get'] == 1">
                                    已领取
                                </v-chip>
                            </td>
                            <td class="text-align ">
                                <v-btn :loading="loading" @click="cancelExchange(log['el_id'], log['e_id'])"
                                    v-show="log['e_get'] == 0" class="s-color w-bgcolor">撤销</v-btn>
                            </td>
                        </tr>
                    </tbody>
                </v-table>
                <div style="padding: 5px;width: fit-content;border-radius: 5px;" class="p-bgcolor s-color r-h">
                    <v-icon icon="mdi-reminder" />
                    请前往领取处领取
                </div>
            </v-card>
        </v-dialog>
        <v-dialog width="700" height="500" v-model="detailDia">
            <v-card class="card">
                <div class="title">积分明细</div>
                <v-table fixed-header height="350px">
                    <thead>
                        <tr>
                            <th class=" p-bgcolor s-color">
                                <span class="r-h" style="margin-left: -20px;">兑换物</span>
                            </th>
                            <th class=" p-bgcolor s-color">
                                <span class="r-h" style="margin-left: -20px;">需要积分</span>
                            </th>
                            <th class=" p-bgcolor s-color">
                                <span class="r-h" style="margin-left: -20px;">积分处理</span>
                            </th>
                            <th class=" p-bgcolor s-color">
                                <span class="r-h" style="margin-left: -20px;">剩余积分</span>
                            </th>
                            <th class=" p-bgcolor s-color">
                                <span class="r-h" style="margin-left: -20px;">日期</span>
                            </th>
                            <th class=" p-bgcolor s-color">
                                <span class="r-h" style="margin-left: -20px;">类型</span>
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="log in pointslogList" :key="log['e_id']">
                            <td class="text-align p-color bold">{{ log['e_name'] }}</td>
                            <td class="text-align i-color">{{ log['e_points'] }}</td>
                            <td class="text-align" :class="log['pl_add_less'][0] == '-' ? 'w-color' : 'success-color'">{{
                                log['pl_add_less'] }}</td>
                            <td class="text-align i-color">{{ log['pl_current_amount'] }}</td>
                            <td class="text-align">{{ log['pl_date'] }}</td>
                            <td class="text-align">{{ log['pl_type'] }}</td>
                        </tr>
                    </tbody>
                </v-table>
            </v-card>
        </v-dialog>
    </div>
</template>

<script lang="ts" setup>
import { ref } from "vue"
import { getExchangeLog, getPointsLog, revokeExchange } from "../apis/points.js"
import { getDate } from "../../utils/getDate.js"
import { useGlobalStore } from "../../global/globalStore.js"
import { useRouter, useRoute } from "vue-router"
const store = useGlobalStore()
const router = useRouter()
const route = useRoute()
let props = defineProps(['amount'])
let emits = defineEmits(['updatePoints'])

// 兑换记录对话框
let logDia = ref(false)
// 积分记录
let exchangelogList = ref(null)
// 查询兑换记录
const getExchangeLogF = () => {
    // 如果没有登录则跳转到登录页面
    if (!store.getStudentId()) {
        router.push({ name: 'login' })
        return
    }
    logDia.value = true
    // 调用接口获取兑换记录
    getExchangeLog(store.getStudentId()).then((res) => {
        // 如果返回结果是null则不执行
        if (res == null) return
        // 如果没有积分则显示开通积分按钮
        if (res['code'] == 200) {
            // 赋值兑换记录
            exchangelogList.value = res['data'].reverse()
        }
    })
}
// 积分明细对话框
let detailDia = ref(false)
// 积分明细数组
let pointslogList = ref(null)
// 获取积分明细
const getPointsLogF = () => {
    // 清空积分明细
    pointslogList.value = null
    // 如果没有登录则跳转到登录页面
    if (!store.getStudentId()) {
        router.push({ name: 'login' })
        return
    }
    detailDia.value = true
    // 调用接口获取积分明细
    getPointsLog(store.getStudentId()).then((res) => {
        // 如果返回结果是null则不执行
        if (res == null) return
        // 如果没有积分则显示开通积分按钮
        if (res['code'] == 200) {
            // 赋值积分明细
            pointslogList.value = res['data'].reverse()
        }
    })
}

// 撤销加载
let loading = ref(false)
// 撤销兑换
function cancelExchange(elId, eId) {
    loading.value = true
    revokeExchange(elId, store.getStudentId(), eId, getDate()).then((res) => {
        // 如果返回结果是null则不执行
        if (res == null) return
        // 如果没有积分则显示开通积分按钮
        if (res['code'] == 200) {
            // 更新兑换记录
            getExchangeLogF()
            // 更新积分
            emits('updatePoints')
        }
    }).finally(() => {
        loading.value = false
    })
}

</script>

<style lang="less" scoped>
.card {
    height: 500px;
    width: 100%;
    box-sizing: border-box;
    padding: 20px;

    .title {
        font-size: 20px;
        font-weight: bold;
        text-align: center;
        margin: 20px 0px;
    }

}

.points_amount {
    justify-content: left !important;
    padding: 10px;
    box-sizing: border-box;
    margin: 10px 0px;

    img {
        width: 60px;
        height: 60px;
    }

    .amount {
        height: 60px;
        line-height: 60px;
        font-size: 30px;
        margin-left: 10px;
        font-style: italic;
    }

    .detailBtn {
        flex: 1;
        text-align: right;
        margin-right: 10px;

        .v-btn {
            width: 100px;
            height: 40px;
            line-height: 40px;
            border-radius: 5px;
            font-size: 16px;
            padding: 0px;
            margin: 0px 10px;
            margin-top: 10px;
        }
    }
}
</style>