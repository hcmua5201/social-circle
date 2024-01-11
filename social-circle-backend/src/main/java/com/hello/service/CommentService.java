package com.hello.service;

import com.hello.entry.Comment;
import com.hello.entry.Post;

import java.util.List;

public interface CommentService {

    void addComment(Comment comment);

    Comment getCommentByID(Long commentID);

    List<Comment> getCommentsByPostID(Post post);

    // 其他需要的方法根据业务需求添加
}

