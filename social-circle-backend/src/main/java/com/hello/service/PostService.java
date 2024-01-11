package com.hello.service;

import com.hello.entry.Post;

import java.util.List;

public interface PostService {

    void addPost(Post post);

    Post getPostByID(Long postID);

    List<Post> getAllPosts();

    // 其他需要的方法根据业务需求添加
}
