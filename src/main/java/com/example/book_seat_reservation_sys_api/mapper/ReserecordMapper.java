package com.example.book_seat_reservation_sys_api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.book_seat_reservation_sys_api.pojo.Reserecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Mapper
@Transactional
public interface ReserecordMapper  extends BaseMapper<Reserecord>{
    //根据stu_id和r_date 获取预约记录接口

    @Select("select * from reserecord,seat where reserecord.s_id = seat.s_id AND stu_id = '${stuId}' and r_date = '${rDate}'&& r_state = '0'")
    List<Map<String,String>> getReserecordByStuIdAndRDate(String stuId, String rDate);

    //根据s_id更新seat表的s_state字段
    @Select("update seat set s_state = 1 where s_id = ${sId}")
    //方法名，并且返回值为int
    void updateSeatSState(int sId);

    @Update("update seat set s_state = 2 where s_id = ${sId}")
    void updateSeatSState2(int sId);

    @Update("update seat set s_state = 0 where s_id = ${sId}")
    void updateSeatSState0(int sId);

    // 获取所有预约记录
    @Select("SELECT * FROM reserecord,seat WHERE reserecord.s_id = seat.s_id")
    List<Map<String,String>> getAllReserecord();


    @Update("UPDATE reserecord SET r_state = '${rstate}',r_enter_time='${date}' WHERE r_id = '${rid}'")
    int update(String rid,String rstate,String date);

    @Update("UPDATE reserecord SET r_complete = '${rComplete}',r_leave_time='${date}' WHERE r_id = '${rId}'")
    int updateComplete(String rId, String rComplete,String date);

    @Update("UPDATE reserecord SET r_state = '${rstate}' WHERE r_id = '${rid}'")
    int updateNoDate(String rid, String rstate);


    @Select("SELECT s_id FROM reserecord WHERE r_id = '${rid}'")
    int getSid(String rid);
}
