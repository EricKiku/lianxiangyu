<template>
    <div class="sidebar">
        <div class="logo"></div>
        <div class="menulist">
            <div class="title" v-for="(group) in menu" :key="group['menuGroup']">
                <div class="bold">{{ group['title'] }}</div>
                <div class="menu-item" @click="selectMenu(item['name'], item['text'])"
                    :class="{ selected: route.name == item['name'] }" v-for="item in group['menuChildren']"
                    :key="item.name">
                    <div class="menu-item-icon">
                        <img :src="route.name == item['name'] ? item['selectedIcon'] : item['icon']" alt="">
                    </div>
                    <div class="menu-item-text">{{ item.text }}</div>
                </div>
                <v-divider class="border-opacity-25" color="info"></v-divider>
            </div>
        </div>
    </div>
</template>

<script lang="ts" setup>
import { ref } from "vue"
import { useRouter, useRoute } from "vue-router"
import { useAdminStore } from "../store/admin.js"
const router = useRouter()
const route = useRoute()
const adminStore = useAdminStore()
// 选中的菜单的path
const selectedMenu = ref("")
// 侧边栏菜单
const menu = ref([
    {
        menuGroup: 1,
        title: "首页",
        menuChildren: [
            {
                icon: "/src/assets/icons/home.png",
                selectedIcon: "/src/assets/icons/home-s.png",
                text: "首页",
                name: "/home"
            },
        ]
    },
    {
        menuGroup: 2,
        title: "座位预约",
        menuChildren: [
            {
                icon: "/src/assets/icons/user.png",
                selectedIcon: "/src/assets/icons/user-s.png",
                text: "用户管理",
                name: "/user"
            },
            {
                icon: "/src/assets/icons/adminseat.png",
                selectedIcon: "/src/assets/icons/adminseat-s.png",
                text: "座位管理",
                name: "/seat"
            },
            {
                icon: "/src/assets/icons/order.png",
                selectedIcon: "/src/assets/icons/order-s.png",
                text: "预约管理",
                name: "/order"
            },
            {
                icon: "/src/assets/icons/orderrecord.png",
                selectedIcon: "/src/assets/icons/orderrecord-s.png",
                text: "预约记录",
                name: "/orderRecord"
            },
        ]
    },
    {
        menuGroup: 3,
        title: "积分兑换",
        menuChildren: [
            {
                icon: "/src/assets/icons/exchange.png",
                selectedIcon: "/src/assets/icons/exchange-s.png",
                text: "兑换品管理",
                name: "/exchange"
            },
            {
                icon: "/src/assets/icons/exchangerecord.png",
                selectedIcon: "/src/assets/icons/exchangerecord-s.png",
                text: "兑换记录",
                name: "/exchangeRecord"
            },
            {
                icon: "/src/assets/icons/adminpoints.png",
                selectedIcon: "/src/assets/icons/adminpoints-s.png",
                text: "积分管理",
                name: "/points"
            },
            {
                icon: "/src/assets/icons/pointsrecord.png",
                selectedIcon: "/src/assets/icons/pointsrecord-s.png",
                text: "积分记录",
                name: "/pointsRecord"
            },
        ]
    },



])
// 选择菜单,跳转路由
const selectMenu = (name: string, title: string) => {
    // 添加路由tab
    adminStore.addTab(
        name,
        title
    )
    router.push({ name })
}
</script>

<style scoped lang="less"> .sidebar {
     width: 250px;
     height: 100%;

     .logo {
         width: 100%;
         height: 80px;
         background-color: #fff;
     }

     .menulist {
         height: fit-content;
         padding: 15px 10px;
         box-sizing: border-box;

         .title {
             margin-top: 10px;
         }

         .selected {
             background-color: var(--light-primary-color) !important;
             color: var(--primary-color) !important;
             font-weight: bold;
             border-left: 8px solid var(--primary-color);
             padding-left: 30px !important;
         }

         .menu-item {
             width: 100%;
             padding: 10px 20px;
             margin: 5px 0px;
             border-radius: 5px;
             cursor: pointer;
             color: var(--unselect-color);
             transition: all 0.3s;
             font-size: 18px;
             display: flex;


             &:hover {
                 background-color: var(--light-primary-color);
                 color: var(--primary-color);
                 font-weight: bold;
                 border-left: 8px solid var(--primary-color);
             }

             .menu-item-icon {
                 img {
                     height: 20px;
                     width: 20px;
                     vertical-align: middle;
                 }

                 margin-right: 10px;
             }
         }
     }
 }
</style>
