package com.example.book_seat_reservation_sys_api.controller;

import com.example.book_seat_reservation_sys_api.mapper.StudentMapper;
import com.example.book_seat_reservation_sys_api.pojo.Res;
import com.example.book_seat_reservation_sys_api.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "student")
public class StudentController {
    @Autowired
    private StudentMapper studentMapper;

    //登录
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Res login(@RequestBody Map<String,String> data) {
        Student student = studentMapper.login(data.get("stuNumber"), data.get("stuPwd"));
        if (student == null) {
            return new Res(400,"登录失败",null);
        }
        return new Res(200,"登录成功",student);
    }

    // 根据学号，验证密码
    @RequestMapping(value = "/checkPwd",method = RequestMethod.POST)
    public Res checkPwd(@RequestBody Map<String,String> data) {
        Student student = studentMapper.checkPwd(data.get("stuId"), data.get("stuPwd"));
        if (student == null) {
            return new Res(400,"密码错误",null);
        }
        return new Res(200,"密码正确",student);
    }

    // 修改密码
    @RequestMapping(value = "/updatePwd",method = RequestMethod.POST)
    public Res updatePwd(@RequestBody Map<String,String> data) {
        int i = studentMapper.updatePwd(data.get("stuId"), data.get("stuPwd"));
        if (i == 0) {
            return new Res(400,"修改失败",null);
        }
        return new Res(200,"修改成功",null);
    }

    // 获取所有学生信息
    @RequestMapping(value = "/getAllStudent",method = RequestMethod.GET)
    public Res getAllStudent() {
        return new Res(200,"获取成功",studentMapper.selectList(null));
    }

    // 添加学生
    @RequestMapping(value = "/addStudent",method = RequestMethod.POST)
    public Res addStudent(@RequestBody Map<String,String> data) {
        String stuNumber = data.get("stuNumber");
        String stuName = data.get("stuName");
        String stuPwd = data.get("stuPwd");
        Student student = new Student();
        student.setStuNumber(stuNumber);
        student.setStuName(stuName);
        student.setStuPwd(stuPwd);

        int i = studentMapper.insert(student);
        if (i == 0) {
            return new Res(400,"添加失败",null);
        }
        return new Res(200,"添加成功",null);
    }
}
