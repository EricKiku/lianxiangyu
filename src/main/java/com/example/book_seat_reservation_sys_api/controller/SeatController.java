package com.example.book_seat_reservation_sys_api.controller;

import com.example.book_seat_reservation_sys_api.mapper.SeatMapper;
import com.example.book_seat_reservation_sys_api.pojo.Res;
import com.example.book_seat_reservation_sys_api.pojo.Seat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping(value = "seat")
public class SeatController {

    @Autowired
    private SeatMapper seatMapper;

    //查询所有座位
     @RequestMapping(value = "/seats",method = RequestMethod.GET)
     public Res queryAllSeats() {
         List<Seat> seats = seatMapper.selectList(null);
         return new Res(200, "获取成功", seats);
     }

    //增加座位
    @RequestMapping(value = "/addSeat",method = RequestMethod.POST)
    public Res addSeat(@RequestBody Seat seat) {
        int rows = seatMapper.insert(seat);
        if (rows > 0) {
            return new Res(200, "插入成功", seat);
        } else {
            return new Res(500, "插入失败", seat);
        }
    }

    //根据id查询座位信息
    @RequestMapping(value = "/seat/{id}",method = RequestMethod.GET)
    public Res querySeatById(@PathVariable("id") Integer SId) {
        Seat seat = seatMapper.selectById(SId);
        if (Objects.isNull(seat)) {
            return new Res(500, "查询失败", null);
        } else {
            return new Res(200, "查询成功", seat);
        }
    }
    //根据s_id和r_date条件，多表查询座位信息,使用表reserecord和seat
    @RequestMapping(value = "/seatsByIdAndDate",method = RequestMethod.GET)
    public Res querySeatBySIdAndRDate(@RequestParam Map<String,String> data) {
        String sId = data.get("sId");
        String rDate = data.get("rDate");
        List<Map<String,String>> seat = seatMapper.getSeatBySIdAndRDate(sId, rDate);
        if (seat.isEmpty()) {
            return new Res(500, "查询失败", null);
        } else {
            return new Res(200, "查询成功", seat);
        }
    }

    //更新座位数据
    @RequestMapping(value = "/updateSeat",method = RequestMethod.POST)
    public Res updateSeat(@RequestBody Map<String,String> data){
        //SCanBeBooked, SCode, SFloor, SInstruction, SCondition
        String SId = data.get("SId");
        String SCanBeBooked = data.get("SCanBeBooked");
        String SCode = data.get("SCode");
        String SFloor = data.get("SFloor");
        String SInstruction = data.get("SInstruction");
        String SCondition = data.get("SCondition");

        int i = seatMapper.updateSeat(SId, SCanBeBooked, SCode, SFloor, SInstruction, SCondition);
        if (i > 0) {
            return new Res(200, "更新成功", null);
        } else {
            return new Res(500, "更新失败", null);
        }
    }
    // 删除座位
    @RequestMapping(value = "/deleteSeat",method = RequestMethod.DELETE)
    public Res deleteSeat(@RequestBody Map<String,String> data) {
        int rows = seatMapper.deleteById(data.get("SId"));
        if (rows > 0) {
            return new Res(200, "删除成功", null);
        } else {
            return new Res(500, "删除失败", null);
        }
    }

    // 重置座位
    @RequestMapping(value = "/resetSeat",method = RequestMethod.POST)
    public Res resetSeat() {
        int rows = seatMapper.resetSeat();
        if (rows > 0) {
            return new Res(200, "重置成功", null);
        } else {
            return new Res(500, "重置失败", null);
        }
    }
}
