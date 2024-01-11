package com.hello.controller;

import com.hello.entry.User;
import com.hello.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/background-image/{userId}")
    public String getBackgroundImage(@PathVariable Long userId) {
        System.out.println("登录用户id"+userId);
        return userService.getBackgroundImagePathById(userId);
    }


    @PostMapping("/add")
    public Result addUser(@RequestBody User user) {
        System.out.println("新增数据："+user);
        userService.addUser(user);
        return new Result(200, "用户添加成功", null);
    }

    @GetMapping("/{userID}")
    public Result getUserByID(@PathVariable Long userID) {
        User user = userService.getUserByID(userID);
        return new Result(200, "用户获取成功", user);
    }

    @GetMapping("/all")
    public Result getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new Result(200, "用户获取成功", users);
    }

    // 根据业务需求添加其他接口
}


