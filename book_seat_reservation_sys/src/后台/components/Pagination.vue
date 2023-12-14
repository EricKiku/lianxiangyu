<template>
    <div class="text-center">
        <v-container>
            <v-row justify="center">
                <v-col cols="8">
                    <v-container class="max-width">
                        <v-pagination v-model="page" class="page my-4" :length="totalPage"></v-pagination>
                    </v-container>
                </v-col>
            </v-row>
        </v-container>
    </div>
</template>

<script lang="ts" setup>
import { ref, watchEffect } from "vue"
// props参数
let props = defineProps(["totalPage"])
// emits事件
let emits = defineEmits(['changePage'])
const page = ref(1)
watchEffect(() => {
    emits("changePage", page.value)
})
</script>

<style lang="less" scoped>
</style>