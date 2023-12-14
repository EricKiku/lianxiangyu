<template>
    <div class="container">
        <router-view></router-view>
    </div>
</template>

<script lang="ts" setup>
import { ref } from "vue"

</script>

<style lang="less" scoped>
.container {
    flex: 1;
    padding: 20px;
    box-sizing: border-box;
    background-color: #EDEFF5;
}
</style>