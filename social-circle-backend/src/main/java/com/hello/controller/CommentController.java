package com.hello.controller;

import com.hello.entry.Comment;
import com.hello.entry.Post;
import com.hello.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/add")
    public Result addComment(@RequestBody Comment comment) {
        commentService.addComment(comment);
        return new Result(200, "评论成功", null);
    }

    @GetMapping("/{commentID}")
    public Result getCommentByID(@PathVariable Long commentID) {
        Comment comment = commentService.getCommentByID(commentID);
        return new Result(200, "获取评论成功", comment);
    }

    @GetMapping("/post/{postID}")
    public Result getCommentsByPostID(@PathVariable Long postID) {
        Post post = new Post();
        post.setPostID(postID);
        List<Comment> comments = commentService.getCommentsByPostID(post);
        return new Result(200, "获取评论成功", comments);
    }

    // 根据业务需求添加其他接口
}

