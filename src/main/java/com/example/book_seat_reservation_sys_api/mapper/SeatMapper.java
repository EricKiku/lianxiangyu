package com.example.book_seat_reservation_sys_api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.book_seat_reservation_sys_api.pojo.Seat;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface SeatMapper extends BaseMapper<Seat> {
    //根据s_id和r_date条件，多表查询座位信息,使用表reserecord和seat
    @Select("select * from reserecord,seat where reserecord.s_id = seat.s_id AND reserecord.s_id = '${sId}' and r_date = '${rDate}'")
    List<Map<String, String>> getSeatBySIdAndRDate(String sId, String rDate);

    // 更新seat
    @Update("UPDATE seat SET s_can_be_booked = '${SCanBeBooked}', s_code = '${SCode}', s_floor = '${SFloor}', s_instruction = '${SInstruction}', s_condition = '${SCondition}' WHERE s_id = '${SId}'")
    int updateSeat(String SId,String SCanBeBooked, String SCode, String SFloor, String SInstruction, String SCondition );

    // 删除座位
    @Delete("DELETE FROM seat WHERE s_id = '${SId}'")
    int deleteById(String SId);

    @Update("UPDATE seat SET s_state = '0'")
    int resetSeat();
}
