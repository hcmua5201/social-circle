package com.hello.controller;

import com.hello.entry.Post;
import com.hello.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/add")
    public Result addPost(@RequestBody Post post) {
        postService.addPost(post);
        return new Result(200, "动态添加成功", null);
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


