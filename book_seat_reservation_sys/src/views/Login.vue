<template>
    <div class="login">
        <div class="container h-100 w-100">
            <div class="login-container-wrapper r-all clearfix">
                <div class="logo"><v-icon icon="mdi-login-variant" /></div>
                <div class="text-h fz-24 i-color kaiti">图书馆座位预约系统</div>
                <div class="welcome"><strong>欢迎,</strong> 请先登录</div>
                <v-tabs v-model="tab" class="s-color fz-20" align-tabs="center">
                    <v-tab :value="1">学生</v-tab>
                    <v-tab :value="2">管理员</v-tab>
                </v-tabs>
                <v-window v-model="tab">
                    <v-window-item :value="1">
                        <!-- 窗格 -->
                        <v-card class="mx-auto" max-width="500">
                            <v-card-title class="text-h6 font-weight-regular justify-space-between">
                                <span>{{ currentTitle }}</span>
                                <v-avatar color="primary" size="24" v-text="step"></v-avatar>
                            </v-card-title>

                            <v-window v-model="step">
                                <v-window-item :value="1">
                                    <v-card-text>
                                        <v-text-field v-model="stuNumber" label="学号" placeholder=""></v-text-field>
                                        <span class="text-caption text-grey-darken-1">
                                            请输入学号
                                        </span>
                                    </v-card-text>
                                </v-window-item>

                                <v-window-item :value="2">
                                    <v-card-text>
                                        <v-text-field v-model="stuPwd" label="密码" type="password"></v-text-field>
                                        <v-text-field v-model="confirmPwd" label="确认密码 Password"
                                            type="password"></v-text-field>
                                        <span class="text-caption text-grey-darken-1">
                                            请输入密码，默认密码<span class="i-color">123456789</span>
                                        </span>
                                    </v-card-text>
                                </v-window-item>

                                <v-window-item :value="3">
                                    <div class="pa-4 text-center">
                                        <v-icon color="success" icon="mdi-location-enter" />
                                        <h3 class="text-h6 font-weight-light mb-2">
                                            {{ loginMessage }}
                                        </h3>
                                        <v-progress-linear v-show="loading" class="p-color"
                                            indeterminate></v-progress-linear>
                                        <span class="text-caption text-grey">请稍等片刻</span>
                                    </div>
                                </v-window-item>
                            </v-window>

                            <v-divider></v-divider>

                            <v-card-actions>
                                <v-btn v-if="step > 1" variant="text" @click="step--">
                                    上一步
                                </v-btn>
                                <v-spacer></v-spacer>
                                <v-btn v-if="step < 3" color="primary" variant="flat" @click="next">
                                    下一步
                                </v-btn>
                            </v-card-actions>
                        </v-card>
                    </v-window-item>
                    <v-window-item :value="2">
                        <!-- 窗格 -->
                        <v-card class="mx-auto" max-width="500">
                            <v-card-title class="text-h6 font-weight-regular justify-space-between">
                                <span>{{ '账号' }}</span>
                                <v-avatar color="primary" size="24" v-text="step"></v-avatar>
                            </v-card-title>

                            <v-window v-model="step">
                                <v-window-item :value="1">
                                    <v-card-text>
                                        <v-text-field v-model="adminAccess" label="账号" placeholder=""></v-text-field>
                                        <span class="text-caption text-grey-darken-1">
                                            请输入账号
                                        </span>
                                    </v-card-text>
                                </v-window-item>

                                <v-window-item :value="2">
                                    <v-card-text>
                                        <v-text-field v-model="stuPwd" label="密码" type="password"></v-text-field>
                                        <v-text-field v-model="confirmPwd" label="确认密码 Password"
                                            type="password"></v-text-field>
                                        <span class="text-caption text-grey-darken-1">
                                            请输入密码并确认
                                        </span>
                                    </v-card-text>
                                </v-window-item>

                                <v-window-item :value="3">
                                    <div class="pa-4 text-center">
                                        <v-icon color="success" icon="mdi-location-enter" />
                                        <h3 class="text-h6 font-weight-light mb-2">
                                            {{ loginMessage }}
                                        </h3>
                                        <v-progress-linear v-show="loading" class="p-color"
                                            indeterminate></v-progress-linear>
                                        <span class="text-caption text-grey">请稍等片刻</span>
                                    </div>
                                </v-window-item>
                            </v-window>

                            <v-divider></v-divider>

                            <v-card-actions>
                                <v-btn v-if="step > 1" variant="text" @click="step--">
                                    上一步
                                </v-btn>
                                <v-spacer></v-spacer>
                                <v-btn v-if="step < 3" color="primary" variant="flat" @click="next">
                                    下一步
                                </v-btn>
                            </v-card-actions>
                        </v-card>
                    </v-window-item>
                </v-window>

            </div>
        </div>
    </div>
</template>

<script lang="ts" setup>
import { ref, computed } from "vue"
import { login } from "../api/index.js"
import { useRouter, useRoute } from "vue-router"
import { useGlobalStore } from "../global/globalStore.js"
import { adminLogin } from "../后台/apis/admin.js"
const router = useRouter()
const route = useRoute()
const gStore = useGlobalStore()
let tab = ref(0)

let stuNumber = ref("202041656")
let adminAccess = ref("lian")
let stuPwd = ref("123456789")
let confirmPwd = ref("123456789")

// 登录
// 加载中
let loading = ref(false)
// 学生登录
function clickLogin() {
    loading.value = true
    loginMessage.value = "正在登录"
    login(stuNumber.value, stuPwd.value).then(res => {
        console.log(res)
        if (res['code'] == 200) {
            loginMessage.value = "登录成功，即将进入"
            // 存储student信息
            gStore.setStudent(res['data'])
            // 把登录权限存储到localStorage中
            localStorage.setItem("role", 'student')
            // 在localStorage中存储student信息
            localStorage.setItem("student", JSON.stringify(res['data']))
            setTimeout(() => {
                router.push({ path: "/" })
            }, 2000);

        } else {
            loginMessage.value = "登录失败，请检查学号和密码"
        }
    }).catch(err => {
        console.log(err)
        loginMessage.value = "登录失败，请检查学号和密码"
    }).finally(() => {
        loading.value = false
    })
}
// 管理员登录
function AdminLogin() {
    loading.value = true
    loginMessage.value = "正在登录"
    adminLogin(adminAccess.value, stuPwd.value).then(res => {
        console.log(res)
        if (res['code'] == 200) {
            loginMessage.value = "登录成功，即将进入"
            // 存储student信息
            gStore.setAdmin(res['data'])
            // 在localStorage中存储admin信息
            localStorage.setItem("admin", JSON.stringify(res['data']))
            // 把登录权限存储到localStorage中
            localStorage.setItem("role", 'admin')
            setTimeout(() => {
                router.push({ path: "/back" })
            }, 2000);

        } else {
            loginMessage.value = "登录失败，请检查账号和密码"
        }
    }).catch(err => {
        console.log(err)
        loginMessage.value = "登录失败，请检查账号和密码"
    }).finally(() => {
        loading.value = false
    })
}

// 窗格效果变量方法
const step = ref(1)

const currentTitle = computed(() => {
    switch (step.value) {
        case 1: return '学号'
        case 2: return '密码'
        default: return '正在登录'
    }
})
// 下一步方法
function next() {
    step.value++
    console.log(step.value);
    if (step.value == 3) {
        if (tab.value == 2) {
            AdminLogin()
        } else {
            clickLogin()
        }
    }

}
// 登录提示
let loginMessage = ref("")
</script>

<style lang="less" scoped>
// @import 'https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css';
// @import 'https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css';


.login {
    background-image: url("../assets/img/loginbg.jpg");
    /* 背景图垂直、水平均居中 */
    background-position: center center;
    /* 背景图不平铺 */
    background-repeat: no-repeat;
    /* 当内容高度大于图片高度时，背景图像的位置相对于viewport固定 */
    background-attachment: fixed;
    /* 让背景图基于容器大小伸缩 */
    background-size: cover;
    font-family: 'Varela', sans-serif;
    font-size: 14px;
    color: #333;
    height: 100%;
    position: relative;

    .container {}
}

input {
    outline: none;
    transition: all 0.3s ease-in-out;
    position: relative;
    left: 50%;
    transform: translateX(-50%);
    margin: 5px 0px;
    padding: 10px !important;

    &:focus {
        // 输入框焦点动画
        box-shadow: 0 0 15px rgba(81, 203, 238, 1);
        border-color: rgba(81, 203, 238, 1);
        padding: 0px 25px !important;
        border-radius: 5px;
    }
}

label {
    margin-top: 6px;
    line-height: 17px;
}

hr {
    border-top: 1px solid #BAC8D6;
}

a {
    color: #05A09A;
}

a:focus,
a:hover {
    color: #ccc;
}

.checkbox-inline+.checkbox-inline,
.radio-inline+.radio-inline {
    margin-top: 6px;
}

.logo .fa {
    font-size: 50px;
}

.top-bar h1 {
    font-size: 25px;
    color: #ffffff;
    text-shadow: 0px 0px 12px #aaa;
}

.wrapper {
    padding: 10px;
    padding-bottom: 100px;
}

.logo img {
    width: 100%;
}

/******* Login Page *******/

.relative {
    position: relative;
}

.login-container-wrapper .logo,
.login-container-wrapper .welcome {
    margin: 0 0 20px 0;
    font-size: 16px;
    color: #fff;
    text-align: center;
    letter-spacing: 1px;
    text-shadow: 0px 0px 5px #000;
}

.login-container-wrapper .logo {
    text-align: center;
    position: absolute;
    top: -38px;
    margin: 0 auto;
    width: 90px;
    left: 50%;
    margin-left: -45px;
    border-radius: 10px;
    background-color: #1d5256;
    padding: 20px;
    box-shadow: 0px 3px 2px 0px #000;
}

.login-container-wrapper {
    max-width: 400px;
    // margin: 15% auto;
    padding: 40px;
    border-radius: 50px 0;
    box-sizing: border-box;
    background: rgb(29, 82, 86);
    box-shadow: 1px 1px 4px 0px #000000, 8px 8px 0px 0px #a2703f, 9px 9px 4px 0px #000000, 16px 16px 0px 0px rgb(172, 65, 31), 17px 17px 7px 1px #000000;
    position: relative;
    padding-top: 80px;
}

.login-form .form-group {
    margin-right: 0;
    margin-left: 0;
}

.login input:focus+.fa {
    color: #1d5256;
}

.login-form i {
    position: absolute;
    top: 18px;
    right: 20px;
    color: #a2703f;
}

.login-form .input-lg {
    font-size: 16px;
    height: 52px;
    padding: 10px 25px;
    border-radius: 0;
}

.login input[type="text"],
.login input[type="password"],
.login input:focus {
    background-color: rgba(255, 255, 255, 0.9);
    border: 1px solid #a2703f;
    color: #eee;
    border-left: 4px solid rgba(150, 103, 58, 0.99);
}

.login input:focus {
    border-left: 4px solid #ccd8da;
}

input:focus i {
    color: #fff !important;
}

input:-webkit-autofill,
textarea:-webkit-autofill,
select:-webkit-autofill {
    background-color: rgba(40, 52, 67, 0.75) !important;
    background-image: none;
    color: rgb(0, 0, 0);
    border-color: #FAFFBD;
}

.login .checkbox label,
.login .checkbox a {
    color: #ddd;
}

.btn-success {
    background-color: transparent;
    background-image: none;
    padding: 8px 50px;
    border-radius: 0;
    border: 2px solid #93a5ab;
    box-shadow: inset 0 0 0 0 #7692A7;
    -webkit-transition: all ease 0.8s;
    -moz-transition: all ease 0.8s;
    transition: all ease 0.8s;
}

.btn-success:focus,
.btn-success:hover,
.btn-success.active,
.btn-success:active,
.btn-success.active.focus,
.btn-success.active:focus,
.btn-success.active:hover,
.btn-success:active.focus,
.btn-success:active:focus,
.btn-success:active:hover,
.open>.dropdown-toggle.btn-success.focus,
.open>.dropdown-toggle.btn-success:focus,
.open>.dropdown-toggle.btn-success:hover {
    background-color: transparent;
    border-color: #fff;
    box-shadow: inset 0 0 100px 0 #7692A7;
    color: #FFF;
}
</style>