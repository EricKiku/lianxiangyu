import request from "../../api/request.js"

export const getSeat = () => {
    return request({
        url: "/seat/seats",
        method: "GET"
    })
}

export const addSeat = (floor, code, state, can, instruction, condition) => {
    let seat = {
        SFloor: floor,
        SCode: code,
        SState: state || 0,
        SCanBeBooked: can || 1,
        SInstruction: instruction || "",
        SCondition: condition || ""
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


// 根据seatid获取座位信息
export const getSeatById = (seatid) => {
    return request({
        url: "/seat/seat/" + seatid,
        method: "GET"
    })
}

// 增加预约记录
export const addReseRecord = (sId, stuId, startTime, endTime, date) => {
    return request({
        url: "/record/addRecord",
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        data: {
            SId: sId,
            StuId: stuId,
            StartTime: startTime,
            EndTime: endTime,
            Date: date
        }
    })
}

// 根据stuId和date获取预约记录
export const getReseRecord = (stuId, date) => {
    return request({
        url: "/record/getRecord/",
        method: "GET",
        params: {
            stuId,
            date
        }
    })
}


// 根据id和date，查询预约记录
export const getRecordByIdAndDate = (sId, rDate) => {
    return request({
        url: "/seat/seatsByIdAndDate",
        method: "GET",
        params: {
            sId,
            rDate
        }
    })
}