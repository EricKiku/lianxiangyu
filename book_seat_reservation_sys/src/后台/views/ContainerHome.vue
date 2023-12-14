<template>
    <div class="home">
        首页
        <v-btn @click="resetFun" prepend-icon="mdi-lock-reset" style="margin-bottom: 10px;">重置</v-btn>
        <Row1></Row1>
        <Row2></Row2>
        <Row3></Row3>
        <!-- <Row4></Row4> -->
    </div>
</template>

<script lang="ts" setup>
import { ref } from "vue"
import { reset } from "../apis/admin.js"
import Row1 from "../components/Row1.vue";
import Row2 from "../components/Row2.vue";
import Row3 from "../components/Row3.vue";
import Row4 from "../components/Row4.vue";
function resetFun() {
    reset().then(res => {
        console.log(res)
    })

}
</script>

<style lang="less" scoped></style>