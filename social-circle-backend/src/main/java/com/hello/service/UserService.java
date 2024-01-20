package com.hello.service;

import com.hello.entry.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserService {

    String searchAvatarByUsername(@Param("username") String username);
    String searchAvatarByEmail(@Param("email") String email);
    User loginByUserAndPwd(@Param("username") String username, @Param("password") String password);

    User loginByEmailAndPwd(@Param("email") String email,@Param("password") String password);
    String getBackgroundImagePathById(Long userId);

    User checkByUsername(@Param("username") String username);

    User checkByNickname(@Param("nickname") String nickname);

    User checkByEmail(@Param("email") String email);
    void addUser(User user);

    User getUserByID(Long userID);

    List<User> getAllUsers();

    // 其他需要的方法根据业务需求添加
}

