package com.hello.service.impl;

import com.hello.entry.Comment;
import com.hello.entry.Post;
import com.hello.mapper.CommentMapper;
import com.hello.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public void addComment(Comment comment) {
        commentMapper.insert(comment);
    }

    @Override
    public Comment getCommentByID(Long commentID) {
        return commentMapper.findByID(commentID);
    }

    @Override
    public List<Comment> getCommentsByPostID(Post post) {
        return commentMapper.findByPostID(post);
    }

    // 其他需要的方法根据业务需求添加
}

