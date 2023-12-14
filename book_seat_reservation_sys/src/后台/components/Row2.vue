<template>
    <div class="row2">
        <div ref="chart" class="chart"></div>
        <div ref="chart1" class="chart"></div>
    </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from "vue"
import * as echarts from 'echarts';
import { getAllRecord, getAllExchangeRecord, getAllScoreRecord } from "../apis/admin.js"
let chart = ref()
let chart1 = ref()
onMounted(() => {
    getAllRecord().then(res => {
        console.log(res)
        // 根据返回值的rdate分组, 统计每组的数量，把键和值放到两个数组，索引对应
        let rdate = res.data.map(item => item.r_date)
        let rdateSet = new Set(rdate)
        let rdateArr = Array.from(rdateSet)
        let rdateCount = <any>[]
        rdateArr.forEach(item => {
            rdateCount.push(rdate.filter(i => i === item).length)
        })
        console.log(rdateArr, rdateCount)
        initCharts(rdateArr, rdateCount)

    })

    // 获取兑换品兑换记录
    getAllExchangeRecord().then(res => {
        console.log(res)
        // 根据返回res中的e_name分组, 统计每组的数量，把键和值放到两个数组，索引对应
        let e_name = res.data.map(item => item.e_name)
        let e_nameSet = new Set(e_name)
        let e_nameArr = Array.from(e_nameSet)
        let e_nameCount = <any>[]
        e_nameArr.forEach(item => {
            e_nameCount.push(e_name.filter(i => i === item).length)
        })
        console.log(e_nameArr, e_nameCount)
        // 获取所有积分记录
        getAllScoreRecord().then(res1 => {
            console.log(res1.data)
            // 返回数据中e_id对应的e_name,并且去重
            // let result = res.data.reduce((result, item) => {
            //     result[item.e_id] = item.e_name;
            //     return result;
            // }, {});
            // console.log(result)
            // 对res1分组，查找所有pl_type="撤销"的项，按e_id分组，统计每组的数量，把键和值放到两个数组，索引对应
            let e_id = res1.data.filter(item => item.pl_type === "撤销").map(item => item.e_id)
            console.log(e_id);

            let e_idSet = new Set(e_id)
            let e_idArr = Array.from(e_idSet)
            let e_idCount = <any>[]
            e_idArr.forEach(item => {
                e_idCount.push(e_id.filter(i => i === item).length)
            })

            let sortedIndices = e_idArr.map((item, index) => index).sort((a, b) => e_idArr[a] < e_idArr[b] ? -1 : (e_idArr[a] > e_idArr[b] ? 1 : 0));

            e_idArr = sortedIndices.map(i => e_idArr[i]);
            e_idCount = sortedIndices.map(i => e_idCount[i]);
            let maxId = Math.max(...e_idArr);
            for (let i = 1; i <= maxId; i++) {
                let index = e_idArr.indexOf(i);
                if (index === -1) {
                    e_idArr.splice(i - 1, 0, 0);
                    e_idCount.splice(i - 1, 0, 0);
                }
            }
            initCharts1(e_nameArr, e_nameCount, e_idCount)
        })

    })

})
// 初始化图表
function initCharts(xAxis, series) {
    let myChart = echarts.init(chart.value);
    let option = {
        color: ['#80FFA5', '#00DDFF', '#37A2FF', '#FF0087', '#FFBF00'],
        title: {
            text: '近期预约情况图表'
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                label: {
                    backgroundColor: '#6a7985'
                }
            }
        },
        legend: {
            data: ['预约数量']
        },
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: [
            {
                type: 'category',
                boundaryGap: false,
                data: xAxis
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],
        series: [
            {
                name: '预约数',
                type: 'line',
                stack: 'Total',
                smooth: true,
                lineStyle: {
                    width: 0
                },
                showSymbol: false,
                areaStyle: {
                    opacity: 0.8,
                    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                        {
                            offset: 0,
                            color: 'rgb(128, 255, 165)'
                        },
                        {
                            offset: 1,
                            color: 'rgb(1, 191, 236)'
                        }
                    ])
                },
                emphasis: {
                    focus: 'series'
                },
                data: series
            }
        ]
    };
    myChart.setOption(option);

}
function initCharts1(xAxis, series, lineSeries) {
    let myChart = echarts.init(chart1.value);
    const colors = ['#5470C6', '#91CC75', '#EE6666'];
    let option = {
        color: colors,
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross'
            }
        },
        grid: {
            right: '20%'
        },
        toolbox: {
            feature: {
                dataView: { show: true, readOnly: false },
                restore: { show: true },
                saveAsImage: { show: true }
            }
        },
        legend: {
            data: ['兑换次数', '撤销次数']
        },
        xAxis: [
            {
                type: 'category',
                axisTick: {
                    alignWithLabel: true
                },
                // prettier-ignore
                data: xAxis
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: '兑换次数',
                position: 'right',
                alignTicks: true,
                axisLine: {
                    show: true,
                    lineStyle: {
                        color: colors[0]
                    }
                },
                axisLabel: {
                    formatter: '{value}'
                }
            },
            {
                type: 'value',
                name: '',
                position: 'right',
                alignTicks: true,
                offset: 80,
                axisLine: {
                    show: false,
                    lineStyle: {
                        color: colors[1]
                    }
                },
                axisLabel: {
                    formatter: '{value} ml'
                }
            },
            {
                type: 'value',
                name: '撤销次数',
                position: 'left',
                alignTicks: true,
                axisLine: {
                    show: true,
                    lineStyle: {
                        color: colors[2]
                    }
                },
                axisLabel: {
                    formatter: '{value}'
                }
            }
        ],
        series: [
            {
                name: '兑换次数',
                type: 'bar',
                // yAxisIndex: 1,
                data: series
            },
            {
                name: '撤销次数',
                type: 'line',
                yAxisIndex: 2,
                data: lineSeries
            }
        ]
    };
    myChart.setOption(option);
}
</script>

<style lang="less" scoped>
.row2 {
    margin-top: 10px;
    height: 400px;
    background-color: white;
    padding: 5px;
    box-sizing: border-box;
    border-radius: 5px;
    display: flex;

    .chart {
        height: 100%;
        flex: 1;
    }
}
</style>