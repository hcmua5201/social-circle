package com.hello.service;

import com.hello.entry.Comment;
import com.hello.entry.Post;
import com.hello.entry.vo.UpdateComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentService {

    int insert(@Param("postID") Long postID, @Param("userID") Long userID, @Param("content") String content);

    Comment getCommentByID(Long commentID);

    List<Comment> getCommentsByPostID(Post post);


    List<UpdateComment> queryAll();
    // 其他需要的方法根据业务需求添加
}

