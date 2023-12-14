<template>
    <div class="total flex-column v-100 h-100">
        <div class="header w-100 flex-row">
            <div class="flex-1 kaiti text-h n-color fz-26 bold r-v h-text  v-text">
                <span class=" text-v">图书馆座位预约</span>
            </div>
            <MenuBar></MenuBar>
        </div>
        <div class="containerSelf flex-1">
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
        background-color: white;
        background: rgba(255, 255, 255, 0.2);
        -webkit-backdrop-filter: blur(10px);
        backdrop-filter: blur(10px);
        // opacity: 0.8;
    }

    .containerSelf {
        background-image: url("../../assets/img/bg.jpg");
        /* 背景图垂直、水平均居中 */
        background-position: center center;
        /* 背景图不平铺 */
        background-repeat: no-repeat;
        /* 当内容高度大于图片高度时，背景图像的位置相对于viewport固定 */
        background-attachment: fixed;
        /* 让背景图基于容器大小伸缩 */
        background-size: cover;
    }
}
</style>