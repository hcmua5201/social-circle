package com.hello.mapper;

import com.hello.entry.User;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select avatar from user where username=#{username}")
    String searchAvatarByUsername(@Param("username") String username);

    @Select("select avatar from user where email=#{email}")
    String searchAvatarByEmail(@Param("email") String email);

    @Select("SELECT backgroundImagePath FROM User WHERE userId = #{userId}")
    String getBackgroundImagePathById(@Param("userId") Long userId);


    @Select("select * from user where username=#{username} and password=#{password}")
    User loginByUserAndPwd(@Param("username") String username,@Param("password") String password);

    @Select("select * from user where email=#{email} and password=#{password}")
    User loginByEmailAndPwd(@Param("email") String email,@Param("password") String password);

    @Select("select * from user where username=#{username}")
    User checkByUsername(@Param("username") String username);

    @Select("select * from user where nickname=#{nickname}")
    User checkByNickname(@Param("nickname") String nickname);

    @Select("select * from user where email=#{email}")
    User checkByEmail(@Param("email") String email);
    @Insert("INSERT INTO User (nickname, avatar,backgroundImagePath,username,password,email) VALUES (#{nickname}, #{avatar},#{backgroundImagePath},#{username},#{password},#{email})")
    void insert(User user);

    @Select("SELECT * FROM User WHERE userID = #{userID}")
    User findByID(Long userID);

    @Select("SELECT * FROM User")
    List<User> findAll();

    // 其他需要的方法根据业务需求添加
}

