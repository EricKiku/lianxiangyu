package com.example.book_seat_reservation_sys_api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.book_seat_reservation_sys_api.pojo.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {
    @Select("select * from student where stu_number=#{stuNumber} and stu_pwd=#{stuPwd}")
    Student login(String stuNumber,String stuPwd);

//    根据id获取学生信息
    @Select("select * from student where stu_id=#{stuId}")
    Student getStudentById(String stuId);

    // 根据学号，验证密码
    @Select("select * from student where stu_id=#{stuId} and stu_pwd=#{stuPwd}")
    Student checkPwd(String stuId,String stuPwd);

    @Update("update student set stu_pwd='${stuPwd}' where stu_id='${stuId}'")
    int updatePwd(String stuId, String stuPwd);
}
