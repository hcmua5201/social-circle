package com.hello.controller;

import com.hello.entry.UserLike;
import com.hello.service.UserLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
@CrossOrigin
public class UserLikeController {

    @Autowired
    private UserLikeService userLikeService;


    @PostMapping("/remove")
    public Result removeUserLike(@RequestParam Long userID, @RequestParam Long postID) {
        System.out.println(userID);
        System.out.println(postID);
        userLikeService.delete(userID, postID);
        return new Result(222,"取消点赞成功",null);
    }

    @PostMapping("/addLike")
    public Result addLike(@RequestParam Long userID, @RequestParam Long postID) {
        System.out.println("添加点赞");
        System.out.println(userID);
        System.out.println(postID);
        userLikeService.insert(userID, postID);
        return new Result(222,"点赞成功",null);
    }

    @GetMapping("/{postID}/{userID}")
    public Result getUserLike(@PathVariable Long postID, @PathVariable Long userID) {
        UserLike userLike = userLikeService.getUserLike(new UserLike(postID, userID));
        return new Result(200, "获取点赞信息成功", userLike);
    }

    // 根据业务需求添加其他接口
}


