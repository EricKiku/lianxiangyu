package com.example.book_seat_reservation_sys_api.controller;

import com.example.book_seat_reservation_sys_api.mapper.PointsMapper;
import com.example.book_seat_reservation_sys_api.pojo.Points;
import com.example.book_seat_reservation_sys_api.pojo.Res;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/points")
public class PointsController {

    @Autowired
    private PointsMapper pointsMapper;
    // 开通积分
    @RequestMapping(value = "/open",method = RequestMethod.POST)
    public Res openPoints(@RequestBody Map<String,String> data) {
        int i = pointsMapper.openPoints(data.get("stuId"));
        if (i == 1) {
            return new Res(200, "开通积分成功", null);
        }else{
            return new Res(400, "开通积分失败", null);
        }
    }
    // 获取stuId的积分
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public Res getPoints(@RequestParam String stuId) {
        Points points = pointsMapper.getPoints(stuId);
        //判断points是否是空对象，如果是，说明该学生没有开通积分，则返回Res对象
        if (points == null) {
            return new Res(0, "该学生没有开通积分", null);
        }else{
            return new Res(1, "获取积分成功", points);
        }
    }

    // 获取学生对应积分明细
    @RequestMapping(value = "/getPointsLogByStuId",method = RequestMethod.GET)
    public Res getPointsLogByStuId(@RequestParam String stuId) {
        return new Res(200, "success", pointsMapper.getPointsLog(stuId));
    }

    // 获取所有已开通积分的学生
    @RequestMapping(value = "/getAll",method = RequestMethod.GET)
    public Res getAllPoints() {
        return new Res(200, "success", pointsMapper.getAllPoints());
    }

    //获取所有积分记录
    @RequestMapping(value = "/getAllPointsLog",method = RequestMethod.GET)
    public Res getAllPointsLog() {
        return new Res(200, "success", pointsMapper.getAllPointsLog());
    }
}
