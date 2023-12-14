package com.example.book_seat_reservation_sys_api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.book_seat_reservation_sys_api.pojo.Exchange;
import org.apache.ibatis.annotations.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Mapper
@Transactional
public interface ExchangeMapper extends BaseMapper<Exchange> {

    // 增加兑换记录
    @Insert("insert into exchangelog(stu_id,e_id) values('${stuId}','${exchangeId}')")
    int addExchangeLog(String stuId,String exchangeId);

    // 库存减一
    @Update("update exchange set e_inventory = e_inventory - 1 where e_id = '${exchangeId}'")
    int lessExchangeInventory(String exchangeId);

    @Select("select * from exchangelog,exchange where exchange.e_id=exchangelog.e_id AND  stu_id = '${stuId}'")
    List<Map<String,String>> getAllExchangeLog(String stuId);
    @Select("select * from exchangelog,exchange,student where exchange.e_id=exchangelog.e_id AND exchangelog.stu_id=student.stu_id")
    List<Map<String,String>> getExchangeLog();
    // 获取学生的积分n
    @Select("select p_amount from points where stu_id = '${stuId}'")
    int getStudentIntegral(String stuId);

    // 添加一条积分记录
    @Insert("insert into pointslog(e_id,stu_id,pl_add_less,pl_date,pl_current_amount,pl_type) values('${eId}','${stuId}','${addLessm}','${date}','${pCurrentAmount}','${type}')")
    int addPointsLog(String eId,String stuId,String addLessm,String date,String pCurrentAmount,String type);

    // 获取商品，根据e_id
    @Select("select e_points from exchange where e_id = '${exchangeId}'")
    int selectById(String exchangeId);

    // 更新学生积分
    @Update("update points set p_amount = '${pAmount}' where stu_id = '${stuId}'")
    int updateStudentPoint(String stuId,String pAmount);

    // 根据el_id删除exchangelog数据
    @Update("delete from exchangelog where el_id = '${elId}'")
    int deleteExchangeLog(String elId);


    @Update("UPDATE exchange SET e_name = '${eName}', e_points = '${ePoints}', e_inventory = '${einventory}', e_description = '${edescription}', e_type = '${eType}' WHERE e_id = '${eId}'")
    int updateExchange(String eId, String eName, String ePoints, String einventory, String edescription, String eType);


    @Delete("DELETE FROM exchange WHERE e_id = '${eId}'")
    int deleteById(String eId);
}
