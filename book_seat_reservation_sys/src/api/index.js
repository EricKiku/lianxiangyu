import request from "./request"

export const login = (stuNumber,stuPwd) => {
    return request({
        url: "/student/login",
        method: "post",
        data:{
            stuNumber,
            stuPwd
        }
    })
}