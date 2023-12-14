// 积分API
import request from "../../api/request"

export const openPoints = (stuId) => {
    return request({
        url: '/points/open',
        method: 'POST',
        data: {
            stuId
        }
    })
}

// 获取积分信息
export const getPoints = (stuId) => {
    return request({
        url: '/points/get',
        method: 'get',
        params: {
            stuId
        }
    })
}

// 获取所有可兑换的商品
export const getExchange = () => {
    return request({
        url: '/exchange/getAllExchange',
        method: 'GET'
    })
}

// 兑换商品，增加一条兑换记录
export const addexchange = (stuId, exchangeId, date, changeValue) => {
    return request({
        url: '/exchange/exchange',
        method: 'POST',
        data: {
            stuId,
            exchangeId,
            date,
            changeValue
        }
    })
}
// 获取用户的所有兑换记录
export const getExchangeLog = (stuId) => {
    return request({
        url: '/exchange/getExchangeLogByStuId',
        method: 'GET',
        params: {
            stuId
        }
    })
}

// 获取积分明细
export const getPointsLog = (stuId) => {
    return request({
        url: '/points/getPointsLogByStuId',
        method: 'GET',
        params: {
            stuId
        }
    })
}

// 撤销兑换
export const revokeExchange = (elId, stuId, eId,date) => {
    return request({
        url: '/exchange/revokeExchange',
        method: 'POST',
        data: {
            elId,
            stuId,
            eId,
            date
        }
    })
}