package com.hello.service.impl;

import com.hello.entry.Post;
import com.hello.mapper.PostMapper;
import com.hello.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostMapper postMapper;

    @Override
    public void addPost(Post post) {
        postMapper.insert(post);
    }

    @Override
    public Post getPostByID(Long postID) {
        return postMapper.findByID(postID);
    }

    @Override
    public List<Post> getAllPosts() {
        return postMapper.getAllPosts();
    }

    // 其他需要的方法根据业务需求添加
}

