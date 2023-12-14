import request from "../../api/request.js"

export const adminLogin = (access, pwd) => {
    return request({
        url: "/admin/login",
        method: "post",
        data: {
            access,
            pwd
        }
    })
}


// 获取学生信息
export const getAllStudent = () => {
    return request({
        url: "/student/getAllStudent",
        method: "get",
    })
}

// 添加学生
export const createStudent = (stuNumber, stuName, stuPwd) => {
    return request({
        url: "/student/addStudent",
        method: "post",
        data: {
            stuNumber,
            stuName,
            stuPwd
        }
    })
}


// 获取所有的座位
export const getAllSeat = () => {
    return request({
        url: "/seat/seats",
        method: "get",
    })
}
// 增加座位
export const addSeat = (floor, code, state, can, instruction, condition) => {
    let seat = {
        SFloor: floor,
        SCode: code,
        SState: state || 0,
        SCanBeBooked: can || 1,
        SInstruction: instruction || "无说明",
        SCondition: condition || "正常使用"
    }
    return request({
        url: "/seat/addSeat",
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        data: JSON.stringify(seat)
    })
}

// 获取所有预约记录
export const getAllRecord = () => {
    return request({
        url: "/record/getAllRecord",
        method: "get",
    })
}

// 获取所有兑换品
export const getAllExchange = () => {
    return request({
        url: "/exchange/getAllExchange",
        method: "get",
    })
}

// 获取所有兑换记录
export const getAllExchangeRecord = () => {
    return request({
        url: "/exchange/getAllExchangeLog",
        method: "get",
    })
}

// 获取开通的积分
export const getOpenScore = () => {
    return request({
        url: "/points/getAll",
        method: "get",
    })
}

// 获取所有积分记录
export const getAllScoreRecord = () => {
    return request({
        url: "/points/getAllPointsLog",
        method: "get",
    })
}





// 获取公告
export const getAllGg = () => {
    return request({
        url: "/admin/getGg",
        method: "get",
    })
}
// 获取通知
export const getAllTz = () => {
    return request({
        url: "/admin/getTz",
        method: "get",
    })
}
// 添加公告
export const addGg = (content) => {
    return request({
        url: "/admin/addGg",
        method: "post",
        data: {
            content
        }
    })
}
// 添加通知
export const addTz = (content) => {
    return request({
        url: "/admin/addTz",
        method: "post",
        data: {
            content
        }
    })
}
// 删除公告
export const deleteGg = (id) => {
    return request({
        url: "/admin/deleteGg",
        method: "post",
        data: {
            id
        }
    })
}
// 删除通知
export const deleteTz = (id) => {
    return request({
        url: "/admin/deleteTz",
        method: "post",
        data: {
            id
        }
    })
}

// 修改预约记录的状态
export const changeRecordState = (RId, RState, date) => {
    return request({
        url: "/record/updateRecord",
        method: "post",
        data: {
            RId,
            RState,
            date
        }
    })
}
// 修改预约记录的完成状态
export const changeRecordFinish = (RId, RComplete, date) => {
    return request({
        url: "/record/updateComplete",
        method: "post",
        data: {
            RId,
            RComplete,
            date
        }
    })
}


// 更新座位数据
export const updateSeat = (SId, SCanBeBooked, SCode, SFloor, SInstruction, SCondition) => {
    return request({
        url: "/seat/updateSeat",
        method: "post",
        data: {
            SId,
            SCanBeBooked,
            SCode,
            SFloor,
            SInstruction,
            SCondition
        }
    })
}
// 更新兑换品数据
export const updateExchange = (EId, EName, EPoints, Einventory, Edescription, EType) => {
    return request({
        url: "/exchange/updateExchange",
        method: "post",
        data: {
            EId,
            EName,
            EPoints,
            Einventory,
            Edescription,
            EType
        }
    })
}
// 删除座位
export const deleteSeat = (SId) => {
    return request({
        url: "/seat/deleteSeat",
        method: "DELETE",
        data: {
            SId
        }
    })
}
// 删除兑换品
export const deleteExchange = (EId) => {
    return request({
        url: "/exchange/deleteExchange",
        method: "DELETE",
        data: {
            EId
        }
    })
}
// 重置
export const reset = () => {
    return request({
        url: "/seat/resetSeat",
        method: "POST",
    })
}