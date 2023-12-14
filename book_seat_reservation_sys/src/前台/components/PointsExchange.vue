<template>
    <div class="exchange" v-if="list">
        积分兑换
        <div class="list" v-for="(row, index) in (Math.ceil(list.length / 3))" :key="index">
            <v-card class="item" width="300" v-for="(item) in list.slice((row - 1) * 3, row * 3)" :key="item['eid']">
                <v-img class="align-end text-white" height="150" :src="item['eimg']" cover>
                    <v-card-title class="cover p-bgcolor fz-16">{{ item['ename'] }}</v-card-title>
                </v-img>

                <v-card-subtitle class="point">
                    <v-chip class="w-color">
                        <span class="fz-20">
                            <img src="../../assets/icons/points.png" alt="">
                            {{ item['epoints'] }}
                        </span>
                    </v-chip>
                    <div>
                        <span class="fz-14">
                            剩余：
                            {{ item['einventory'] }}
                        </span>
                    </div>
                </v-card-subtitle>

                <v-card-text>
                    <v-chip>
                        {{ item['etype'] }}
                    </v-chip>
                    <div style="margin-top: 10px;">{{ item['edescription'] }}</div>
                </v-card-text>



                <v-card-actions style="justify-content: right;">
                    <v-btn @click="exchange(item)" class="p-color" variant="outlined" prepend-icon="mdi-swap-horizontal">
                        兑换
                    </v-btn>

                </v-card-actions>


            </v-card>

        </div>
        <v-dialog width="500" v-model="dialogVisual">
            <v-card prepend-icon="mdi-alert-octagram" title="提示">
                <v-card-text>
                    <div v-if="exchangename" style="margin-top:5px;">兑换: <span class="p-color">{{
                        exchangename }}</span></div>
                    <div v-if="exchangeprice" style="margin-top:5px;">需要: <span class="i-color">{{
                        exchangeprice }}</span>积分
                    </div>
                    <div v-if="exchangeprice" style="margin-top:5px;">兑换后剩余: <span class="i-color">{{
                        amount - exchangeprice }}</span>积分</div>
                </v-card-text>

                <v-card-actions>
                    <v-chip :color="requestSuccess ? 'success' : ''" v-show="exchangeResultMessage">
                        {{ exchangeResultMessage }}
                    </v-chip>
                    <v-spacer></v-spacer>
                    <v-btn text="取消" @click="dialogVisual = false"></v-btn>
                    <v-btn v-show="compfirmButton" :loading="loading" @click="confirmExchange" class="p-bgcolor s-color"
                        variant="outlined">
                        确定
                    </v-btn>
                </v-card-actions>
            </v-card>

        </v-dialog>
    </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from "vue"
import { getDate } from "../../utils/getDate.js"
import { getExchange, addexchange } from "../apis/points.js"
import { useGlobalStore } from "../../global/globalStore.js"
import { useRouter, useRoute } from "vue-router"
import request from "../../api/request";
let props = defineProps(['amount'])
let emits = defineEmits(['updatePoints'])
const globalStore = useGlobalStore()
const router = useRouter()
const route = useRoute()
// 物品列表
const list = <any>ref(null)
// 获取兑换品
const getExchangeF = () => {
    // 调用接口获取积分
    getExchange().then((res) => {
        // 如果返回结果是null则不执行
        if (res == null) return
        // 如果没有积分则显示开通积分按钮
        if (res['code'] == 200) {
            console.log(res['data']);
            list.value = res['data']
        }
    })

}

// 对话框显示
let dialogVisual = ref(false)
// 物品信息
let exchangeId = ref(null)
let exchangename = ref(null)
let exchangeprice = ref(null)
// 点击兑换按钮
const exchange = (item) => {
    console.log(item);
    dialogVisual.value = true
    exchangeId.value = item['eid']
    exchangename.value = item['ename']
    exchangeprice.value = item['epoints']
    exchangeResultMessage.value = ""
    requestSuccess.value = false
    compfirmButton.value = true
}
// 确定兑换方法
// 加载中
let loading = ref(false)
// 兑换成功与否提示
let exchangeResultMessage = ref("")
// 请求是否成功
let requestSuccess = ref(false)
// 确定按钮是否显示
let compfirmButton = ref(true)
const confirmExchange = () => {

    if (!globalStore.getStudentId()) {
        router.push({ path: "/login" })
        return
    }
    loading.value = true
    requestSuccess.value = false
    // 调用接口兑换
    addexchange(globalStore.getStudentId(), exchangeId.value, getDate(), exchangeprice.value).then((res) => {
        // 如果返回结果是null则不执行
        if (res == null) return
        // 如果没有积分则显示开通积分按钮
        if (res['code'] == 200) {
            console.log(res['data']);
            getExchangeF()
            // 更新
            emits('updatePoints')
            requestSuccess.value = true
            exchangeResultMessage.value = "兑换成功"

        } else {
            requestSuccess.value = false
            exchangeResultMessage.value = "兑换失败"
        }
    }).finally(() => {
        loading.value = false
        compfirmButton.value = false
    })
}

onMounted(() => {
    // 获取积分兑换信息
    getExchangeF()
})
</script>

<style lang="less" scoped>
.exchange {
    margin-top: 10px;

    .list {
        display: flex;
        // // flex-wrap: wrap;
        justify-content: center;
        // 

        .item {
            // flex: 1;
            margin: 20px;
            box-shadow: rgba(100, 100, 111, 0.2) 0px 7px 29px 0px;
            transition: all 0.3s;

            &:hover {
                box-shadow: rgba(12, 100, 252, 0.5) 0px 7px 29px 0px;
            }
        }
    }


    .cover {
        padding: 0px;
        padding-left: 10px;
        opacity: 0.8;
    }

    .point {
        height: fit-content;
        margin-top: 10px;
        padding: 10px;
        box-sizing: border-box;
        display: flex;
        justify-content: space-between;

        .price {
            margin-left: 10px;

        }

        img {
            height: 20px;
            width: 20px;
            // 图片垂直居中
            vertical-align: middle;
        }
    }
}
</style>