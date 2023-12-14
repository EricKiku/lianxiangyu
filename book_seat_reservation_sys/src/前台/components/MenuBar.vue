<template>
    <v-tabs class="menu r-v p-color" v-model="tab" bg-color="" centered stacked>
        <v-tab value="seat" @click="changeTab('seat')">
            <img src="../../assets/icons/seat.png" alt="">
            座位
        </v-tab>

        <v-tab value="points" @click="changeTab('points')">
            <img src="../../assets/icons/coin.png" alt="">
            积分
        </v-tab>

        <v-tab v-show="store.getStudentId()">
            <!-- <img src="../../assets/icons/person.png" alt=""> -->
            <v-menu>
                <template v-slot:activator="{ props }">
                    <v-btn v-bind="props" prepend-icon="mdi-account-settings" variant="plain">
                        {{ store.getStudent()['stuName'] }}({{ store.getStudent()['stuNumber'] }})
                    </v-btn>
                </template>
                <v-list>
                    <v-list-item>
                        <v-btn class="w-100" @click="dialog = true">修改密码</v-btn>
                        <v-btn class="w-100" @click="changeTab('login')">退出登录</v-btn>
                    </v-list-item>
                </v-list>
            </v-menu>
        </v-tab>
    </v-tabs>
    <!-- 修改密码对话框 -->
    <v-dialog class="dialog" v-model="dialog" width="500" height="400">
        <v-card class="w-100 mx-auto">
            <v-card-title class="text-h6 font-weight-regular justify-space-between">
                <span>{{ currentTitle }}</span>
                <!-- <v-avatar color="primary" size="24" v-text="step"></v-avatar> -->
            </v-card-title>

            <v-window v-model="step">
                <v-window-item :value="1">
                    <v-card-text>
                        <v-text-field v-model="currentPassword" class="p-color" label="Password"
                            placeholder=""></v-text-field>
                        <span class="text-caption text-grey-darken-1">
                            请输入您当前使用的密码<br>
                            <span class="fz-16 bold w-color"> {{ tipMessage }}</span>
                        </span>
                    </v-card-text>
                </v-window-item>

                <v-window-item :value="2">
                    <v-card-text>
                        <v-text-field v-model="newPassword" class="p-color" label="新密码" type="password"></v-text-field>
                        <v-text-field v-model="confirmPassword" class="success-color" label="确认密码"
                            type="password"></v-text-field>
                        <span class="text-caption text-grey-darken-1">
                            请输入新的密码并确认<br><span class="fz-16 bold w-color"> {{ tipMessage }}</span>
                        </span>
                    </v-card-text>
                </v-window-item>

                <v-window-item :value="3">
                    <div class="pa-4 text-center">
                        <!-- <v-img class="mb-4" contain height="128" src="https://cdn.vuetifyjs.com/images/logos/v.svg"></v-img> -->
                        <v-icon size="64" class="success-color success--text" icon="mdi-shield-check"></v-icon>
                        <h3 class="text-h6 font-weight-light mb-2">
                            修改成功
                        </h3>
                        <span class="text-caption text-grey">可以安全关闭了!</span>
                    </div>
                </v-window-item>
            </v-window>

            <v-divider></v-divider>

            <v-card-actions>
                <v-btn v-if="step > 1" variant="text" @click="changeStep('back')">
                    上一步
                </v-btn>
                <v-spacer></v-spacer>
                <v-btn v-if="step < 3" color="primary" variant="flat" @click="changeStep('next')">
                    下一步
                </v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script lang="ts" setup>
import { ref, computed } from "vue"
import { useRouter, useRoute } from "vue-router";
import { useGlobalStore } from "../../global/globalStore.js"
import { checkPassword, changePassword } from "../apis/student.js"
const store = useGlobalStore()
const router = useRouter()
const route = useRoute()
// tab计算属性
let tab = computed({
    get() {
        return route.name
    },
    set() { }
})
// 

// 跳转方法
function changeTab(name: string) {
    router.push({
        name
    })
}

const step = ref(1)

const currentTitle = computed(() => {
    switch (step.value) {
        case 1: return '请输入当前密码'
        case 2: return '请输入新的密码'
        default: return '结果'
    }
})
// 切换步数
// 用户输入的当前密码
let currentPassword = ref('')
// 用户输入的新密码
let newPassword = ref('')
// 用户输入的确认密码
let confirmPassword = ref('')
// 加载中
let loading = ref(false)
// 提示
let tipMessage = ref('')
function changeStep(type) {
    tipMessage.value = ''
    // 如果是back则减一，如果是next则加一
    let num = type === 'back' ? step.value - 1 : step.value + 1
    //如果step.value+1==1，则先调用接口验证密码是否正确
    if (num === 2 && type === 'next') {
        console.log("验证密码");
        loading.value = true
        checkPassword(store.getStudent()['stuId'], currentPassword.value).then(res => {
            // 如果验证成功则step.value+1
            if (res['code'] === 200) {
                step.value = num
            } else {
                // 如果验证失败则提示密码错误
                tipMessage.value = '密码错误'
            }
        })
        // 调用接口验证密码是否正确
        // 如果正确则step.value+1
        // 如果不正确则提示密码错误
    } else if (num === 3 && type === 'next') {
        // 如果step.value+1==3，则调用接口修改密码
        changePasswordF()
        // 如果修改成功则提示修改成功
        // 如果修改失败则提示修改失败
    } else {
        step.value = num
    }
}
// 修改密码方法
function changePasswordF() {
    // 如果新密码和确认密码不一致则提示两次密码不一致
    if (newPassword.value !== confirmPassword.value) {
        tipMessage.value = '两次密码不一致'
        return
    }
    // 如果新密码和确认密码一致则调用接口修改密码
    changePassword(store.getStudent()['stuId'], newPassword.value).then(res => {
        // 如果修改成功则step.value+1
        if (res['code'] === 200) {
            step.value = 3
        } else {
            // 如果修改失败则提示修改失败
            tipMessage.value = '修改失败'
        }
    })
    // 如果修改成功则提示修改成功
    // 如果修改失败则提示修改失败
}
// 打开修改密码对话框
let dialog = ref(false)
</script>

<style lang="less" scoped>
.menu {
    padding: 0px 10px 0px 0px;
    font-size: 10px;
}

.dialog {}
</style>