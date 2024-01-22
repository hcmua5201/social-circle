package com.hello.service;

import com.hello.entry.Post;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PostService {

    void addPost(Post post);

    int add(@Param("userID") Long userID, @Param("content") String content, @Param("image") String image, @Param("time") String time, @Param("address") String address);

    Post getPostByID(Long postID);

    List<Post> getAllPosts();

    // 其他需要的方法根据业务需求添加
}
