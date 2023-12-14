<template>
    <div class="row3">
        <div class="charts">
            <div ref="chart" class="chart"></div>
            <div ref="chart1" class="chart"></div>
        </div>
        <div class="notict">
            <div class="gg">
                <div class="title">公告</div>
                <div class="content">
                    <div class="item" @mouseenter="hoverGgFunc(gg['g_id'])" @mouseleave="leaveGgFunc" v-for="(gg) in ggs"
                        :key="gg['g_id']">
                        <img src="../../assets/icons/notice.png" alt="">
                        {{ gg['g_content'] }}
                        <span class="delete" v-if="hoverGg === gg['g_id']" @click="deleteGgFunc(gg['g_id'])">删除</span>
                    </div>
                </div>
                <el-input @keydown.enter="addGgFunc" v-model="inputGg" placeholder="输入公告，回车确认" />
            </div>
            <div class="tongzhi">
                <div class="title">通知</div>
                <div class="content">
                    <div class="item" @mouseenter="hoverTzFunc(tz['n_id'])" @mouseleave="leaveTzFunc" v-for="(tz) in tzs"
                        :key="tz['n_id']">
                        <img src="../../assets/icons/notification.png" alt="">
                        {{ tz['n_content'] }}
                        <span class="delete" v-if="hoverTz === tz['n_id']" @click="deleteTzFunc(tz['n_id'])">删除</span>
                    </div>
                </div>
                <el-input @keydown.enter="addTzFunc" v-model="inputTz" placeholder="输入通知，回车确认" />
            </div>
        </div>
    </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from "vue"
import * as echarts from 'echarts';
import { getAllRecord, getAllGg, getAllTz, addGg, addTz, deleteGg, deleteTz } from "../apis/admin.js"
let chart = ref()
let chart1 = ref()
onMounted(() => {
    // 预约常用座位图表
    getAllRecord().then(res => {
        let series = []
        let groups = res.data.reduce((groups, item) => {
            let group = groups[item.s_code] || { name: item.s_code, value: 0 };
            group.value++;
            groups[item.s_code] = group;
            return groups;
        }, {});

        series = Object.values(groups);
        // 把series按value排序，取前10个
        series.sort((a: any, b: any) => b.value - a.value)
        series = series.slice(0, 9)
        initCharts(series)






        // 处理第二个图表数据
        let series1 = []
        // 取出结果的r_start_time和r_end_time，使用'-'连接，作为key，value为出现次数
        let groups1 = res.data.reduce((groups, item) => {
            let group = groups[item.r_start_time + '-' + item.r_end_time] || { name: item.r_start_time + '-' + item.r_end_time, value: 0 };
            group.value++;
            groups[item.r_start_time + '-' + item.r_end_time] = group;
            return groups;
        }, {});
        series1 = Object.values(groups1);
        // 把series1按value排序，取前五个
        series1.sort((a: any, b: any) => b.value - a.value)
        series1 = series1.slice(0, 5)


        initCharts1(series1)
    })
    getGg()
    getTz()
})
// 初始化图表
function initCharts(series) {
    let myChart = echarts.init(chart.value);
    let option = {
        title: {
            text: '座位预约频率',
            subtext: 'Fake Data',
            left: 'center'
        },
        tooltip: {
            trigger: 'item'
        },
        legend: {
            orient: 'vertical',
            left: 'left'
        },
        series: [
            {
                name: '座位使用次数',
                type: 'pie',
                radius: '50%',
                data: series,
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    myChart.setOption(option);
}
function initCharts1(series) {
    let myChart = echarts.init(chart1.value);
    let option = {
        title: {
            text: '预约时间段',
            subtext: 'Fake Data',
            left: 'center'
        },
        tooltip: {
            trigger: 'item'
        },
        legend: {
            orient: 'vertical',
            left: 'right'
        },
        series: [
            {
                name: '预约时间段',
                type: 'pie',
                radius: '50%',
                data: series,
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    myChart.setOption(option);
}

let ggs = ref([])
let tzs = ref([])
const inputGg = ref('')
const inputTz = ref('')
// 获取公告
function getGg() {
    getAllGg().then(res => {
        ggs.value = res.data.reverse()
        console.log(ggs)
    })
}
// 获取通知
function getTz() {
    getAllTz().then(res => {
        tzs.value = res.data
    })
}
// 添加公告
function addGgFunc() {
    if (inputGg.value === '') return
    addGg(inputGg.value).then(res => {
        getGg()
        inputGg.value = ''
    })
}
// 添加通知
function addTzFunc() {
    if (inputTz.value === '') return
    addTz(inputTz.value).then(res => {
        getTz()
        inputTz.value = ''
    })
}

// 鼠标悬浮删除按钮
let hoverGg = ref()
let hoverTz = ref()
function hoverGgFunc(id) {
    hoverGg.value = id
}
function hoverTzFunc(id) {
    hoverTz.value = id
}
function leaveGgFunc() {
    hoverGg.value = ''
}
function leaveTzFunc() {
    hoverTz.value = ''
}
// 删除公告
function deleteGgFunc(id) {
    deleteGg(id).then(res => {
        getGg()
    })
}
// 删除通知
function deleteTzFunc(id) {
    deleteTz(id).then(res => {
        getTz()
    })
}

</script>

<style lang="less" scoped>
.row3 {
    margin-top: 10px;
    height: 400px;
    background-color: white;
    padding: 5px;
    box-sizing: border-box;
    border-radius: 5px;
    display: flex;

    .charts {
        height: 100%;
        flex: 1;
        display: flex;

        .chart {
            height: 100%;
            flex: 1;
        }
    }

    .notict {
        flex: 1;
        display: flex;
        padding: 10px;
        box-sizing: border-box;

        div {
            flex: 1;

            .content {
                height: 250px;
                overflow-y: auto;

                .item {
                    padding: 5px;
                    border-bottom: 1px solid var(--border-color);

                    &:last-child {
                        border-bottom: none;
                    }

                    img {
                        height: 20px;
                        width: 20px;
                        vertical-align: middle;
                        margin-right: 10px;
                    }

                    .delete {
                        float: right;
                        color: red;
                        cursor: pointer;

                    }
                }
            }
        }

        .gg {
            padding: 10px;
            border: 2px solid var(--border-color);
            border-radius: 5px;
            margin-right: 10px;
            box-sizing: border-box;
            height: 100%;

        }

        .tongzhi {
            padding: 10px;
            border: 2px solid var(--border-color);
            border-radius: 5px;
            margin-right: 10px;
            box-sizing: border-box;
            height: 100%;
        }
    }

}
</style>