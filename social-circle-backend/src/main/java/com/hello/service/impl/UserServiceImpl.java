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
    public String searchAvatarByUsername(String username) {
        return userMapper.searchAvatarByUsername(username);
    }

    @Override
    public String searchAvatarByEmail(String email) {
        return userMapper.searchAvatarByEmail(email);
    }

    @Override
    public User loginByUserAndPwd(String username, String password) {
        return userMapper.loginByUserAndPwd(username,password);
    }

    @Override
    public User loginByEmailAndPwd(String email, String password) {
        return userMapper.loginByEmailAndPwd(email, password);
    }

    @Override
    public String getBackgroundImagePathById(Long userId) {
        return userMapper.getBackgroundImagePathById(userId);
    }

    @Override
    public User checkByUsername(String username) {
        return userMapper.checkByUsername(username);
    }

    @Override
    public User checkByNickname(String nickname) {
        return userMapper.checkByNickname(nickname);
    }

    @Override
    public User checkByEmail(String email) {
        return userMapper.checkByEmail(email);
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

