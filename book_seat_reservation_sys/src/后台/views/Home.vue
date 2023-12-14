<template>
    <div class="home">
        <SiderBar />
        <div class="main">
            <Header></Header>
            <Container />
        </div>
    </div>
</template>

<script lang="ts" setup>
import { ref } from "vue"
import SiderBar from "../components/SiderBar.vue"
import Header from "../components/Header.vue"
import Container from "../components/Container.vue"
</script>

<style lang="less" scoped>
.home {
    display: flex;
    height: 100%;

    .main {
        flex: 1;
        display: flex;
        flex-direction: column;
    }
}
</style>