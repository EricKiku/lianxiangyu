<template>
    <div class="table r-h">
        <div class="heads">
            <div class="headItem bold" v-for="(head, index) in props.headerAlias" :key="index">
                {{ head }}
            </div>
            <div class="headItem bold" v-if="edit || delete1">
                操作
            </div>
        </div>
        <div class="datalist">
            <div class="dataItem"
                v-for="(dataItem, index) in props.data.slice((currentPage - 1) * pageCount, currentPage * pageCount)  "
                :key="dataItem[keys[0]]">
                <div class="dataCell cell-color" v-for="(key, index) in keys" :key="index">



                    <!-- 有规则，使用该代码块 -->
                    <span v-if="rules && rules[index]">
                        <!-- 有样式，使用该代码块 -->
                        <span v-if="cellStyle[index]">
                            <span v-if="!cellStyle[index]['iconText']" :style="cellStyle[index]['style']">
                                <span v-if="rules[index][dataItem[key]]['icon']"
                                    :title="rules[index][dataItem[key]]['title']">
                                    <v-icon :color="rules[index][dataItem[key]]['color']"
                                        :icon="rules[index][dataItem[key]]['icon']"></v-icon>
                                </span>
                                <span v-else :style="rules[index][dataItem[key]]['style']">
                                    {{ rules[index][dataItem[key]]['text'] }}
                                </span>
                            </span>
                            <v-chip :color="cellStyle[index]['color']" v-else :prepend-icon="cellStyle[index]['iconText']">
                                {{ dataItem[key] }}
                            </v-chip>
                        </span>
                        <!-- 没有样式，使用该代码块 -->
                        <span v-else>
                            <span v-if="rules[index][dataItem[key]]['icon']" :title="rules[index][dataItem[key]]['title']">
                                <v-icon :color="rules[index][dataItem[key]]['color']"
                                    :icon="rules[index][dataItem[key]]['icon']"></v-icon>
                            </span>
                            <span v-else :style="rules[index][dataItem[key]]['style']">
                                {{ rules[index][dataItem[key]]['text'] }}
                            </span>
                        </span>
                    </span>
                    <!-- 没有规则，使用该代码块 -->
                    <span v-else>
                        <!-- 有样式 -->
                        <span v-if="cellStyle && cellStyle[index]">
                            <span v-if="!cellStyle[index]['iconText']" class="r-v" style="margin-top: -10px;"
                                :style="cellStyle[index]['style']">

                                <span v-if="(dataItem[key] + '').includes('+')" style="color: green;">{{ dataItem[key]
                                }}</span>
                                <span v-else-if="(dataItem[key] + '').includes('-')" class="w-color">{{ dataItem[key]
                                }}</span>
                                <span v-else>{{ dataItem[key] }}</span>
                            </span>
                            <div v-else-if="cellStyle[index]['img']">
                                <img style="height: 60px;width: 60px;" :src="dataItem[key]" alt="">
                            </div>
                            <v-chip v-else :color="cellStyle[index]['color']" :prepend-icon="cellStyle[index]['iconText']">
                                {{ dataItem[key] }}
                            </v-chip>
                        </span>
                        <!-- 无样式 -->
                        <span v-else>
                            <span v-if="(dataItem[key] + '').includes('+')" style="color: green;">{{ dataItem[key] }}</span>
                            <span v-else-if="(dataItem[key] + '').includes('-')" class="w-color">{{ dataItem[key] }}</span>
                            <span v-else>{{ dataItem[key] }}</span>
                        </span>
                    </span>




                </div>
                <div class="dataCell cell-color options" v-if="edit || delete1">
                    <v-btn prepend-icon="mdi-square-edit-outline" class="edit" v-if="edit"
                        @click="emits('editFun', dataItem)">编辑</v-btn>

                    <v-dialog width="500">
                        <template v-slot:activator="{ props }">
                            <v-btn v-bind="props" prepend-icon="mdi-delete-forever" class="delete" v-if="delete1">删除</v-btn>
                        </template>

                        <template v-slot:default="{ isActive }">
                            <v-card title="警告">
                                <v-card-text>
                                    确认删除吗？删除后不可恢复！
                                </v-card-text>

                                <v-card-actions>
                                    <v-spacer></v-spacer>

                                    <v-btn color="#1E90FF" text="确认" @click="emits('deleteFun', dataItem)"></v-btn>
                                </v-card-actions>
                            </v-card>
                        </template>
                    </v-dialog>
                </div>
            </div>
        </div>
        <div class="bottom">
            <div class="message r-h">
                共 <span class="p-color">{{ data.length }}</span> 条数据-
                共 <span class="p-color">{{ totalPage }}</span> 页-
                当前是第 <span class="p-color">{{ currentPage }}</span> 页
            </div>
            <Pagination class="pagination" v-if="initSuccess" :total-page="totalPage" @change-page="changePage">
            </Pagination>
        </div>
    </div>
</template>

<script lang="ts" setup>
import { ref, onMounted, watchEffect } from "vue"
import Pagination from "./Pagination.vue"

// 接收prps
let props = defineProps(['data', 'keys', 'headerAlias', 'cellStyle', 'rules', 'edit', 'delete1'])
// 接收emits
let emits = defineEmits(['editFun', 'deleteFun'])
// 表头
let tableKeys = <any>ref([])

// 获取表头
function getTableHeader() {
    // 获取所有键名
    for (let key in objectOrder(props.data[0])) {
        tableKeys.value.push(key)
    }
}

// 分页数据
let pageCount = ref(10)
let currentPage = ref(1)
let totalData = ref(0)
let totalPage = ref(0)
let initSuccess = ref(false)
// 初始化分页组件
function initPagination() {
    // 获取总数据
    totalData.value = props.data.length
    // 计算总页数
    totalPage.value = Math.ceil(totalData.value / 10)
    // 初始化成功
    initSuccess.value = true
}
// 切换页数
function changePage(page: number) {
    currentPage.value = page
}
onMounted(() => {
    getTableHeader()
    initPagination()
})



function objectOrder(obj) {//排序的函数
    var newkey = Object.keys(obj).sort(); //先用Object内置类的keys方法获取要排序对象的属性名，再利用Array原型上的sort方法对获取的属性名进行排序，newkey是一个数组，这行是最重要的代码！
    var newObj = {};//创建一个新的对象，用于存放排好序的键值对
    for (var i = 0; i < newkey.length; i++) {//遍历newkey数组
        newObj[newkey[i]] = obj[newkey[i]];//向新创建的对象中按照排好的顺序依次增加键值对
    }
    return newObj;//返回排好序的新对象
}


// 监听数据变化，重新初始化分页组件
watchEffect(() => {
    if (props.data) {
        initPagination()
    }
})
</script>

<style lang="less" scoped>
.table {
    width: 100%;
    height: 100%;
    border: 1px solid var(--border-color);
    background-color: white;
    padding: 10px;
    box-sizing: border-box;
    background-color: white;
    border-radius: 5px;

    .heads {
        display: flex;
        background-color: #F5F7FA;
        border: 1px solid var(--border-color);
        // border: 1px solid var(--primary-color);
        // border-top-left-radius: 5px;
        // border-top-right-radius: 5px;

        .headItem {
            flex: 1;
            text-align: center;
            padding: 10px 0;
            font-size: 16px;
            font-weight: 600;
            color: #000;

            &:nth-child(even) {
                border-left: 1px solid var(--border-color);
                border-right: 1px solid var(--border-color);
            }
        }
    }

    .datalist {
        background-color: white;

        .dataItem {
            display: flex;
            border-bottom: 1px solid var(--border-color);

            // 鼠标悬浮效果
            &:hover {
                background-color: #F5F7FA;
            }

            // 斑马纹

            .dataCell {
                flex: 1;
                text-align: center;
                padding: 10px 0;
                font-size: 16px;

                &:nth-child(even) {
                    border-left: 1px solid var(--border-color);
                    border-right: 1px solid var(--border-color);
                }
            }

            .options {
                display: flex;
                justify-content: space-around;

                span {
                    padding: 5px;
                    border-radius: 5px;
                }

                .edit {
                    &:hover {
                        background-color: var(--primary-color);
                        color: white;
                    }
                }

                .delete {
                    &:hover {
                        background-color: var(--warning-color);
                        color: white;
                    }
                }
            }
        }
    }

    .bottom {
        // display: flex;
        height: 150px;
        position: relative;

        .message {
            height: fit-content;
            text-align: center;
            // position: absolute;
            // top: 50%;
            // left: 10%;
            // transform: translateY(-50%);
        }

        .pagination {
            flex: 1;
        }
    }
}
</style>