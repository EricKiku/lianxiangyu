package com.example.book_seat_reservation_sys_api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.book_seat_reservation_sys_api.pojo.Points;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface PointsMapper extends BaseMapper<Points> {
    @Select("select * from points where stu_id = '${stuId}'")
    Points getPoints(String stuId);

    // 开通积分
     @Insert("insert into points(stu_id,p_amount) values('${stuId}',5)")
     int openPoints(String stuId);

    // 获取积分明细
    @Select("select * from pointslog,exchange where pointslog.e_id=exchange.e_id AND stu_id = '${stuId}'")
    List<Map<String,String>> getPointsLog(String stuId);

    @Select("select * from points,student where points.stu_id=student.stu_id")
    List<Map<String,String>> getAllPoints();

    @Select("select * from pointslog,exchange,student where pointslog.e_id=exchange.e_id AND pointslog.stu_id=student.stu_id")
    List<Map<String,String>> getAllPointsLog();
}
