package com.example.book_seat_reservation_sys_api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.book_seat_reservation_sys_api.pojo.Admin;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdminMapper extends BaseMapper<Admin> {
    @Select("select * from admin where a_access='${access}' and a_pwd='${pwd}'")
    Admin login(String access, String pwd);

    // 获取公告
    @Select("select * from gg")
    List<Map<String,String>> getGg();
    // 获取通知
    @Select("select * from notice")
    List<Map<String,String>> getNotice();

    @Insert("insert into gg(g_content) values('${content}')")
    int addGg(String content);

    @Insert("insert into notice(n_content) values('${content}')")
    int addNotice(String content);
    // 删除公告
    @Select("delete from gg where g_id = ${id}")
    void deleteGg(int id);
    // 删除通知
    @Select("delete from notice where n_id = ${id}")
    void deleteNotice(int id);
}
