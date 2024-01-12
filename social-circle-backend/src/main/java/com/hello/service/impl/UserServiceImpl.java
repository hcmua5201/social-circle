package com.hello.service.impl;

import com.hello.entry.User;
import com.hello.mapper.UserMapper;
import com.hello.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User loginByUserAndPwd(String username, String password) {
        return userMapper.loginByUserAndPwd(username,password);
    }

    @Override
    public String getBackgroundImagePathById(Long userId) {
        return userMapper.getBackgroundImagePathById(userId);
    }

    @Override
    public void addUser(User user) {
        userMapper.insert(user);
    }

    @Override
    public User getUserByID(Long userID) {
        return userMapper.findByID(userID);
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.findAll();
    }

    // 其他需要的方法根据业务需求添加
}

