<template>
    <div class="total flex-column v-100 h-100">
        <div class="header w-100 flex-row">
            <div class="flex-1 kaiti n-color fz-26 bold r-v h-text h-100 v-text">
                图书馆座位预约
            </div>
            <MenuBar></MenuBar>
        </div>
        <div class="container flex-1">
            <router-view></router-view>
        </div>
    </div>
</template>

<script lang="ts" setup>
import { ref } from "vue"
import MenuBar from "../components/MenuBar.vue"

</script>

<style lang="less" scoped>
.total {

    .header {
        // height: 10%;
        height: fit-content;
        border-bottom: 1px solid #e0e0e0;
        padding: 10px 20px;
        box-sizing: border-box;
    }
}
</style>