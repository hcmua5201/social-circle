package com.hello.controller;

import com.hello.entry.Comment;
import com.hello.entry.Post;
import com.hello.entry.vo.UpdateComment;
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


    @GetMapping("/UpdateComment")
    public Result UpdateComment(){
        List<UpdateComment> updateComments = commentService.queryAll();
        System.out.println(updateComments);
        return new Result(222,"查询成功",updateComments);
    }

    @PostMapping("/add")
    public Result addComment( @RequestParam Long postID,@RequestParam Long userID,@RequestParam String content) {
        System.out.println("添加评论信息："+"用户id"+userID+"帖子id:"+postID+"评论内容："+content);
        int insert = commentService.insert(postID,userID, content);
        if (insert == 1){
            return new Result(222, "评论成功", insert);
        }
        return new Result(444, "评论失败", insert);
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

