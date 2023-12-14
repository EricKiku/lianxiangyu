import request from "../../api/request.js"

// 验证密码
export const checkPassword = (stuId, stuPwd) => {
    return request({
        url: "/student/checkPwd",
        method: "POST",
        data: {
            stuId,
            stuPwd
        }
    })
}

// 修改密码
export const changePassword = (stuId, stuPwd) => {
    return request({
        url: "/student/updatePwd",
        method: "POST",
        data: {
            stuId,
            stuPwd
        }
    })
}