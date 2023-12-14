// axios二次封装
import axios from 'axios'
// 引入进度条
import nProgress from 'nprogress';
// 引入进度条样式，可以修改css文件中的样式
import "nprogress/nprogress.css"
// 使用axios.create创建一个axios实例
// 创建的实例等于axios
const request = axios.create({
    // 配置
    // 基础路径，发送请求的时候，会自动在路径后加上
    baseURL: '/api',
    // 超时时间
    // timeout: 5000
});


// 请求拦截器
request.interceptors.request.use((config) => {
    // 开始进度条
    nProgress.start();
    // config配置对象，其中有headers等参数
    return config;
})


// 响应拦截器
request.interceptors.response.use((res) => {
    // 结束进度条
    nProgress.done();
    // 成功的回调
    return res.data
}, (err) => {
    // 结束进度条
    nProgress.done();
    // 失败的回调
    return Promise.reject(new Error('fail'));
})


export default request;
