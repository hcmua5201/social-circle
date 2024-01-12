package com.hello.mapper;

import com.hello.entry.User;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT backgroundImagePath FROM User WHERE userId = #{userId}")
    String getBackgroundImagePathById(@Param("userId") Long userId);


    @Select("select * from user where username=#{username} and password=#{password}")
    User loginByUserAndPwd(@Param("username") String username,@Param("password") String password);

    @Insert("INSERT INTO User (nickname, avatar) VALUES (#{nickname}, #{avatar})")
    @Options(useGeneratedKeys = true, keyProperty = "userID")
    void insert(User user);

    @Select("SELECT * FROM User WHERE userID = #{userID}")
    User findByID(Long userID);

    @Select("SELECT * FROM User")
    List<User> findAll();

    // 其他需要的方法根据业务需求添加
}

