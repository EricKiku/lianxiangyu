package com.example.book_seat_reservation_sys_api.controller;

import com.example.book_seat_reservation_sys_api.mapper.ReserecordMapper;
import com.example.book_seat_reservation_sys_api.pojo.Res;
import com.example.book_seat_reservation_sys_api.pojo.Reserecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "record")
public class ReserecordController {
    @Autowired
    private ReserecordMapper reserecordMapper;

    //添加预约记录
    @Transactional
    @RequestMapping(value = "addRecord",method = RequestMethod.POST)
    public Res addReserecord(@RequestBody Map<String,String> data) {
        Reserecord reserecord = new Reserecord();
        reserecord.setSId(Integer.parseInt(data.get("SId")));
        reserecord.setStuId(data.get("StuId"));
        reserecord.setRStartTime(data.get("StartTime"));
        reserecord.setREndTime(data.get("EndTime"));
        reserecord.setRDate(data.get("Date"));

        List<Map<String,String>> reserecordByStuIdAndRDate = reserecordMapper.getReserecordByStuIdAndRDate(reserecord.getStuId(), reserecord.getRDate());
        // 遍历reserecordByStuIdAndRDate,根据学号和日期查询预约记录，如果有记录并且RComplete为0则返回错误信息
        for (Map<String,String> map : reserecordByStuIdAndRDate) {
            if (map.get("r_complete").equals("0")) {
                return new Res(400,"您已经预约过了",null);
            }
        }
        //把seat的SState设置为1
        reserecordMapper.updateSeatSState(reserecord.getSId());
        // 插入
        int insert = reserecordMapper.insert(reserecord);
        //判断插入是否成功，返回Res对象
        if (insert == 1) {
            return new Res(200,"添加成功",null);
        } else {
            return new Res(400,"预约失败",null);
        }
    }

    // 获取预约记录
    @RequestMapping(value = "getRecord",method = RequestMethod.GET)
    public Res getReserecord(@RequestParam Map<String,String> data) {
        String stuId = data.get("stuId");
        String rDate = data.get("date");

        // 查询
        List<Map<String,String>> reserecord = reserecordMapper.getReserecordByStuIdAndRDate(stuId, rDate );
        //判断查询结果是否是空数组，返回Res对象
        if (reserecord.isEmpty()) {
            return new Res(400,"查询失败",null);
        } else {
            return new Res(200,"查询成功",reserecord);
        }
    }
    // 获取所有预约记录
    @RequestMapping(value = "getAllRecord",method = RequestMethod.GET)
    public Res getAllReserecord() {
        // 查询
        List<Map<String,String>> reserecord = reserecordMapper.getAllReserecord();
        //判断查询结果是否是空数组，返回Res对象
        if (reserecord.isEmpty()) {
            return new Res(400,"查询失败",null);
        } else {
            return new Res(200,"查询成功",reserecord);
        }
    }

    // 修改预约记录的r_state字段
    @RequestMapping(value = "updateRecord",method = RequestMethod.POST)
    public Res updateReserecord(@RequestBody Map<String,String> data) {
        // 修改
        int update = reserecordMapper.update(data.get("RId"),data.get("RState"),data.get("date"));
        //修改座位的状态字段
        int sId = reserecordMapper.getSid(data.get("RId"));
        reserecordMapper.updateSeatSState2(sId);
        //判断修改是否成功，返回Res对象
        if (update == 1) {
            return new Res(200,"修改成功",null);
        } else {
            return new Res(400,"修改失败",null);
        }
    }
    // 修改预约记录的r_complete字段
    @RequestMapping(value = "updateComplete",method = RequestMethod.POST)
    public Res updateComplete(@RequestBody Map<String,String> data) {
        // 修改
        int update = reserecordMapper.updateComplete(data.get("RId"),data.get("RComplete"),data.get("date"));
        int update1 = reserecordMapper.updateNoDate(data.get("RId"),"2");
        // 把座位状态改为0
        int sId = reserecordMapper.getSid(data.get("RId"));
        reserecordMapper.updateSeatSState0(sId);
        //判断修改是否成功，返回Res对象
        if (update == 1) {
            return new Res(200,"修改成功",null);
        } else {
            return new Res(400,"修改失败",null);
        }
    }
}
