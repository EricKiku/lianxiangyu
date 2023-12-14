<template>
    <div style="height: 120px;">
        <div class="header">
            <div class="header-left">
                <div class="title">图书馆座位预约后台管理系统</div>
            </div>
            <div class="header-right">
                <div class="user">
                    <span>admin</span>
                </div>
                <div class="logout">
                    <v-btn @click="logout" prepend-icon="mdi-power" color="red">
                        退出
                    </v-btn>
                </div>
            </div>
        </div>
        <div class="routerTab">
            <div class="tab r-v" @click="tabClick(tab)" @mouseenter="showClose(tab['name'])" @mouseleave="hideClose()"
                :class="{ selected: route.name == tab['name'] }" v-for="(tab) in adminStore.getTabs()" :key="tab['id']">
                {{ tab['title'] }}
                <v-icon v-show="hoverTab == tab['name']" @click.stop="removeTab(tab['name'])" class="r-v "
                    icon="mdi-close-box"></v-icon>
            </div>
        </div>
    </div>
</template>

<script lang="ts" setup>
import { ref } from "vue"
import { useAdminStore } from "../store/admin.js"
import { useRouter, useRoute } from "vue-router"
const router = useRouter()
const route = useRoute()
const adminStore = useAdminStore()

// 标签点击
const tabClick = (tab: any) => {
    router.push({ name: tab['name'] })
    console.log(route);
    console.log(tab);


}
// 鼠标悬浮显示关闭按钮
let hoverTab = ref<any>("")
const showClose = (name: any) => {
    hoverTab.value = name
}
// 鼠标离开隐藏关闭按钮
const hideClose = () => {
    hoverTab.value = ""
}
// 删除标签
const removeTab = (name: string) => {
    adminStore.removeTab(name)
}

// 退出
const logout = () => {
    router.push({ name: "login" })
}
</script>

<style lang="less" scoped>
.header {
    height: 80px;
    background-color: #fff;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0 20px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);

    .header-left {
        display: flex;
        flex-direction: column;
        align-items: center;

        .title {
            font-size: 20px;
            font-weight: bold;
        }


    }

    .header-right {
        display: flex;
        align-items: center;

        .user {
            display: flex;
            align-items: center;
            margin-right: 20px;

            img {
                width: 40px;
                height: 40px;
                margin-right: 10px;
            }
        }

        .logout {
            cursor: pointer;
        }
    }
}

.routerTab {
    height: 35px;
    display: flex;

    .selected {
        background-color: var(--primary-color);
        color: var(--second-color);
        border-color: var(--primary-color);
    }

    .tab {
        padding: 5px;
        margin: 0 5px;
        box-sizing: border-box;
        border: 1px solid #ccc;
        border-radius: 5px;
        margin-top: 2.5px;
        width: fit-content;
        cursor: pointer;
        display: flex;
        transition: all 0.3s;

        &:hover {}
    }
}
</style>