package com.example.book_seat_reservation_sys_api.controller;

import com.example.book_seat_reservation_sys_api.mapper.AdminMapper;
import com.example.book_seat_reservation_sys_api.pojo.Admin;
import com.example.book_seat_reservation_sys_api.pojo.Res;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminMapper adminMapper;

    // 登录
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Res login(@RequestBody Map<String, String> data) {
        Admin login = adminMapper.login(data.get("access"), data.get("pwd"));
        if (login != null) {
            return new Res(200, "登录成功", login);
        }
        return new Res(400, "登录失败", null);
    }
    // 获取公告
    @RequestMapping(value = "/getGg",method = RequestMethod.GET)
    public Res getNotice() {

        return new Res(200, "获取成功", adminMapper.getGg());
    }
    // 获取通知
    @RequestMapping(value = "/getTz",method = RequestMethod.GET)
    public Res getAnnouncement() {
        return new Res(200, "获取成功", adminMapper.getNotice());
    }
    // 添加公告
    @RequestMapping(value = "/addGg",method = RequestMethod.POST)
    public Res addAnnouncement(@RequestBody Map<String, String> data) {
        int i = adminMapper.addGg(data.get("content"));
        if (i == 1) {
            return new Res(200, "添加成功", null);
        }
        return new Res(400, "添加失败", null);
    }
    // 添加通知
    @RequestMapping(value = "/addTz",method = RequestMethod.POST)
    public Res addNotice(@RequestBody Map<String, String> data) {
        int i = adminMapper.addNotice(data.get("content"));
        if (i == 1) {
            return new Res(200, "添加成功", null);
        }
        return new Res(400, "添加失败", null);
    }
    // 删除公告
    @RequestMapping(value = "/deleteGg",method = RequestMethod.POST)
    public Res deleteAnnouncement(@RequestBody Map<String, String> data) {
        adminMapper.deleteGg(Integer.parseInt(data.get("id")));
        return new Res(400, "删除成功", null);
    }
    // 删除通知
    @RequestMapping(value = "/deleteTz",method = RequestMethod.POST)
    public Res deleteNotice(@RequestBody Map<String, String> data) {
        adminMapper.deleteNotice(Integer.parseInt(data.get("id")));
        return new Res(400, "删除成功", null);
    }
}
