package com.hello.service.impl;

import com.hello.entry.Comment;
import com.hello.entry.Post;
import com.hello.entry.vo.UpdateComment;
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
    public int insert(Long postID, Long userID, String content) {
       return commentMapper.insert(postID, userID, content);
    }

    @Override
    public Comment getCommentByID(Long commentID) {
        return commentMapper.findByID(commentID);
    }

    @Override
    public List<Comment> getCommentsByPostID(Post post) {
        return commentMapper.findByPostID(post);
    }

    @Override
    public List<UpdateComment> queryAll() {
        return commentMapper.queryAll();
    }

    // 其他需要的方法根据业务需求添加
}

