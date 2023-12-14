package com.example.book_seat_reservation_sys_api.controller;

import com.example.book_seat_reservation_sys_api.mapper.ExchangeMapper;
import com.example.book_seat_reservation_sys_api.mapper.StudentMapper;
import com.example.book_seat_reservation_sys_api.pojo.Res;
import com.example.book_seat_reservation_sys_api.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/exchange")
public class ExchangeController {
    @Autowired
    private ExchangeMapper exchangeMapper;
    @Autowired
    private StudentMapper studentMapper;
    // 获取所有兑换品
    @RequestMapping(value = "/getAllExchange",method = RequestMethod.GET)
    public Res getAllExchange() {
        return new Res(200, "success", exchangeMapper.selectList(null));
    }

    // 兑换品兑换
    @RequestMapping(value = "/exchange",method = RequestMethod.POST)
    @Transactional
    public Res exchange(@RequestBody Map<String,String> data) {
        System.out.println(data.get("stuId"));
        System.out.println(data.get("exchangeId"));
        // 获取当前用户的积分
        int point = exchangeMapper.getStudentIntegral(data.get("stuId"));
        // 获取当前商品价格
        int price = exchangeMapper.selectById(data.get("exchangeId"));
        // 如果积分不足，返回错误
        if (point < price) {
            return new Res(400, "积分不足", null);
        }
        // 插入兑换记录
        int i = exchangeMapper.addExchangeLog(data.get("stuId"), data.get("exchangeId"));
        // 库存减一
        int j = exchangeMapper.lessExchangeInventory(data.get("exchangeId"));

        // 添加积分记录记录减传过来的商品价格
        int k = exchangeMapper.addPointsLog(data.get("exchangeId"),data.get("stuId"), "-"+price, data.get("date"), String.valueOf(point - price),"兑换");
        // 更新学生积分
        int l = exchangeMapper.updateStudentPoint(data.get("stuId"), String.valueOf(point - price));
        if (i == 0 || j == 0) {
            return new Res(400, "fail", null);
        }
        return new Res(200, "success", null);
    }
    // 获取学生对应兑换记录
    @RequestMapping(value = "/getExchangeLogByStuId",method = RequestMethod.GET)
    public Res getAllExchangeLog(@RequestParam String stuId) {
        return new Res(200, "success", exchangeMapper.getAllExchangeLog(stuId));
    }
    // 获取所有兑换记录
    @RequestMapping(value = "/getAllExchangeLog",method = RequestMethod.GET)
    public Res getAllExchangeLog() {
        return new Res(200, "success", exchangeMapper.getExchangeLog());
    }
    // 撤销兑换
    @RequestMapping(value = "/revokeExchange",method = RequestMethod.POST)
    @Transactional
    public Res revokeExchange(@RequestBody Map<String,String> data) {
        // 删除一个兑换记录
        int i = exchangeMapper.deleteExchangeLog(data.get("elId"));
        // 获取当前用户的积分
        int point = exchangeMapper.getStudentIntegral(data.get("stuId"));
        // 获取当前商品价格
        int price = exchangeMapper.selectById(data.get("eId"));
        // 添加一个积分记录，加上商品积分
        int j = exchangeMapper.addPointsLog(data.get("eId"),data.get("stuId"), "+"+price, data.get("date"),String.valueOf(point + price),"撤销");
        // 同时学生的积分也要加上商品积分
        int k = exchangeMapper.updateStudentPoint(data.get("stuId"), String.valueOf(point + price));
        if (i == 0) {
            return new Res(400, "fail", null);
        }
        return new Res(200, "success", null);
    }


    // 更新兑换品数据
    @RequestMapping(value = "/updateExchange",method = RequestMethod.POST)
    public Res updateExchange(@RequestBody Map<String,String> data){
        //SCanBeBooked, SCode, SFloor, SInstruction, SCondition
        //EId, EName, EPoints, Einventory, Edescription, EType
        String EId = data.get("EId");
        String EName = data.get("EName");
        String EPoints = data.get("EPoints");
        String Einventory = data.get("Einventory");
        String Edescription = data.get("Edescription");
        String EType = data.get("EType");

        int i = exchangeMapper.updateExchange(EId, EName, EPoints, Einventory, Edescription, EType);
        if (i > 0) {
            return new Res(200, "更新成功", null);
        } else {
            return new Res(500, "更新失败", null);
        }
    }
    // 删除兑换品
    @RequestMapping(value = "/deleteExchange",method = RequestMethod.DELETE)
    public Res deleteExchange(@RequestBody Map<String,String> data) {
        int rows = exchangeMapper.deleteById(data.get("EId"));
        if (rows > 0) {
            return new Res(200, "删除成功", null);
        } else {
            return new Res(500, "删除失败", null);
        }
    }
}
