<template>
  <!-- 路由前台或后台 -->
  <router-view></router-view>
</template>

<script lang="ts" setup>
import { ref, onMounted } from "vue"
import { useRouter } from "vue-router"
import { useGlobalStore } from "./global/globalStore.js"
const router = useRouter()
const globalStore = useGlobalStore()

onMounted(() => {
  // 如果localstore中有admin数据，就把globalStore中的admin数据填入，如果没有就跳转到登录页面
  const admin = localStorage.getItem("admin");
  const student = localStorage.getItem("student");
  const role = localStorage.getItem("role");
  console.log("role:", role);

  if (admin && role == "admin") {
    globalStore.setAdmin(JSON.parse(admin))
    // 跳转到后台
    router.push("/back")
  } else if (student && role == "student") {
    globalStore.setStudent(JSON.parse(student))
    router.push("/front")
  }
})


</script>

<style lang="less" scoped></style>