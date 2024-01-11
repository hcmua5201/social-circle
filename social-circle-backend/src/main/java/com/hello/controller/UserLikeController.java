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

    @PostMapping("/add")
    public Result addUserLike(@RequestBody UserLike userLike) {
        userLikeService.addUserLike(userLike);
        return new Result(200, "点赞成功", null);
    }

    @DeleteMapping("/remove")
    public Result removeUserLike(@RequestBody UserLike userLike) {
        userLikeService.removeUserLike(userLike);
        return new Result(200, "取消点赞成功", null);
    }

    @GetMapping("/{postID}/{userID}")
    public Result getUserLike(@PathVariable Long postID, @PathVariable Long userID) {
        UserLike userLike = userLikeService.getUserLike(new UserLike(postID, userID));
        return new Result(200, "获取点赞信息成功", userLike);
    }

    // 根据业务需求添加其他接口
}


