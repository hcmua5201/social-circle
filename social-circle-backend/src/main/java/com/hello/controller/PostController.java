package com.hello.controller;

import com.hello.entry.Post;
import com.hello.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/add")
    public Result addPost(@RequestParam Long userID, @RequestParam String content, @RequestParam String image, @RequestParam String time,@RequestParam String address) {
//        System.out.println(userID);
//        System.out.println(content);
//        System.out.println(image);
//        System.out.println(time);
//        System.out.println(address);
        int add = postService.add(userID, content, image, time, address);
        if (add==1){
            return new Result(222,"添加动态成功",add);
        }
        return new Result(444, "动态添加失败", null);
    }

    @GetMapping("/{postID}")
    public Result getPostByID(@PathVariable Long postID) {
        Post post = postService.getPostByID(postID);
        return new Result(200, "动态获取成功", post);
    }

    @GetMapping("/all")
    public Result getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        System.out.println(posts);
        return new Result(222, "动态获取成功", posts);
    }

    // 根据业务需求添加其他接口
}


