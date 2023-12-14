<template>
    <!-- 后期height改为fit-content -->
    <div class="container w-60 h-auto r-all radius-10">
        <div class="title heiti fz-20">我的积分</div>
        <div class="openPoints" v-if="showOpenPoints">
            <v-dialog width="500">
                <template v-slot:activator="{ props }">
                    <div class="a-all w-auto flex-column">
                        <span class="i-color">你还没有开通积分功能</span>
                        <v-btn class="p-bgcolor s-color" prepend-icon="mdi-open-in-app" v-bind="props" text="立刻开通"> </v-btn>
                    </div>

                </template>

                <template v-slot:default="{ isActive }">
                    <v-card title="提示">
                        <v-card-text>
                            <div class="heiti fz-20">开通积分功能</div>
                            <div class="fz-16">
                                开通积分功能后，你可以在平台上进行积分兑换。可以兑换 <span class="p-color">实物</span>和 <span
                                    class="i-color">预约时长卡。</span>
                                现在开通奖励<span class="i-color bold">5</span>积分
                            </div>

                        </v-card-text>

                        <v-card-actions>
                            <v-btn @click="confirm" :loading="loading" class="r-h p-bgcolor s-color" variant="tonal"
                                text="我知道了"></v-btn>
                        </v-card-actions>
                    </v-card>
                </template>
            </v-dialog>
        </div>
        <div class="points" v-if="!showOpenPoints">
            <PointsHave @updatePoints="getPointsF" v-if="points" :amount="points['pamount']"></PointsHave>
            <v-divider style="margin-top: 15px;"></v-divider>
            <PointsExchange v-if="points" @updatePoints="getPointsF" :amount="points['pamount']"></PointsExchange>
        </div>
    </div>
    <!-- 消息条 -->
    <v-snackbar v-model="snackbar" :timeout="3000" class=" s-color" top>
        {{ snackbarText }}
    </v-snackbar>
</template>

<script lang="ts" setup>
import { ref, onMounted } from "vue"
import { openPoints, getPoints } from "../apis/points.js"
import { useGlobalStore } from "../../global/globalStore.js"
import PointsHave from "../components/PointsHave.vue"
import PointsExchange from "../components/PointsExchange.vue"
const globalStore = useGlobalStore()
// 是否显示开通积分按钮 
const showOpenPoints = ref(true)
onMounted(() => {
    // 获取用户积分信息，如果没有则显示开通按钮
    getPointsF()
})

// 用户的积分信息
const points = ref(null)
// 获取积分
const getPointsF = () => {
    // 调用接口获取积分
    getPoints(globalStore.getStudentId()).then((res) => {
        // 如果返回结果是null则不执行
        if (res == null) return
        // 如果没有积分则显示开通积分按钮
        if (res['code'] == 0) {
            showOpenPoints.value = true


        } else {
            showOpenPoints.value = false
            // 赋值积分信息
            points.value = res['data']
            console.log(points.value);
        }
        // 如果有积分则显示积分信息
    })

}

// 对话框确定方法
// 加载状态
const loading = ref(false)
function confirm() {
    loading.value = true
    // 调用接口开通积分
    openPoints(globalStore.getStudentId()).then((res) => {
        // 如果返回结果是null则不执行
        if (res == null) return
        // 如果开通成功则隐藏开通按钮
        if (res['code'] == 200) {
            setSnackbar("开通成功")
            showOpenPoints.value = false
            // 再次获取积分信息
            getPointsF()
        }
    }).finally(() => {
        loading.value = false
    })
}



// 消息条
let snackbar = ref(false)
let snackbarText = ref("")
// 设置消息条
function setSnackbar(text: string) {
    snackbar.value = true
    snackbarText.value = text
}
</script>

<style lang="less" scoped>
.container {
    background-color: white;
    padding: 20px;
    box-sizing: border-box;
}
</style>