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


    //查询头像
    @GetMapping("/searchAvatarByUsername/{username}")
    public Result searchAvatarByUsername(@PathVariable String username){
        System.out.println("登录页面用户输入的用户名:"+username);
        String s = userService.searchAvatarByUsername(username);
        if (s!=null){
            return new Result(222,"根据用户名查询头像成功",s);
        }
        return new Result(444,"根据用户名查询头像失败","/src/assets/icon.png");
    }

    @GetMapping("/searchAvatarByEmail/{email}")
    public Result searchAvatarByEmail(@PathVariable String email){
        System.out.println("登录页面用户输入的邮箱:"+email);
        String s = userService.searchAvatarByEmail(email);
        if (s!=null){
            return new Result(222,"根据邮箱查询头像成功",s);
        }
        return new Result(444,"根据邮箱查询头像失败","/src/assets/icon.png");
    }

    @PostMapping("/loginByUserAndPwd")
    public Result loginByUserAndPwd(@RequestParam("username")String username,
                                @RequestParam("password") String password){
        System.out.println("登录用户名："+username);
        System.out.println("登录密码："+password);
        User login = userService.loginByUserAndPwd(username, password);
        if (login!=null){
            return new Result(222,"登录成功",login);
        }
        return new Result(444,"用户不存在,用户名或密码错误",null);
    }


    @PostMapping("/loginByEmailAndPwd")
    public Result loginByEmailAndPwd(@RequestParam("email")String email,
                                    @RequestParam("password") String password){
        System.out.println("登录邮箱："+email);
        System.out.println("登录密码："+password);
        User emaillogin = userService.loginByEmailAndPwd(email, password);
        if (emaillogin!=null){
            return new Result(222,"登录成功",emaillogin);
        }
        return new Result(444,"用户不存在,邮箱或密码错误",null);
    }



    @GetMapping("/background-image/{userId}")
    public String getBackgroundImage(@PathVariable Long userId) {
        System.out.println("登录用户id"+userId);
        return userService.getBackgroundImagePathById(userId);
    }


    @PostMapping("/add")
    public Result addUser(@RequestBody User user) {
        user.setNickname(user.getUsername());
        //检查用户名是否被注册
        User checkByUsername = userService.checkByUsername(user.getUsername());
        if (checkByUsername != null){
            return new Result(444,"用户名被注册",null);
        }
        //检查邮箱是否被注册
        User checkByEmail = userService.checkByEmail(user.getEmail());
        if (checkByEmail != null){
            return new Result(444,"邮箱已被注册",null);
        }
        //检查昵称是否被注册
        User checkByNickname = userService.checkByNickname(user.getNickname());
        if (checkByNickname != null){
            return new Result(444,"昵称已被注册",null);
        }
        System.out.println("注册信息通过，可以注册");
        System.out.println("新增数据："+user);
        //设置默认头像和默认背景壁纸
        user.setBackgroundImagePath("https://s2.loli.net/2024/01/13/EoTMmLukXPOetAh.png");
        if (user.getAvatar() == null||user.getAvatar().equals("")){
            user.setAvatar("https://s2.loli.net/2024/01/13/lfUKxHnX3wOFWJT.jpg");
        }
        userService.addUser(user);
        return new Result(222, "用户添加成功", null);
    }

    @GetMapping("/{userID}")
    public Result getUserByID(@PathVariable Long userID) {
        User user = userService.getUserByID(userID);
        return new Result(444, "用户获取成功", user);
    }

    @GetMapping("/all")
    public Result getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new Result(222, "用户获取成功", users);
    }

    // 根据业务需求添加其他接口
}


