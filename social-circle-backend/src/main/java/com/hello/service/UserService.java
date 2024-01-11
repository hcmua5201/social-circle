package com.hello.service;

import com.hello.entry.User;

import java.util.List;

public interface UserService {


    String getBackgroundImagePathById(Long userId);
    void addUser(User user);

    User getUserByID(Long userID);

    List<User> getAllUsers();

    // 其他需要的方法根据业务需求添加
}

