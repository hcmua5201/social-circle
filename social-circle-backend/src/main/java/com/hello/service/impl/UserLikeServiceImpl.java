package com.hello.service.impl;

import com.hello.entry.UserLike;
import com.hello.mapper.UserLikeMapper;
import com.hello.service.UserLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserLikeServiceImpl implements UserLikeService {

    @Autowired
    private UserLikeMapper userLikeMapper;



    @Override
    public void insert(Long userID, Long postID) {
        userLikeMapper.insert(userID, postID);
    }

    @Override
    public void delete(Long userID, Long postID) {
        userLikeMapper.delete(userID, postID);
    }



    @Override
    public UserLike getUserLike(UserLike userLike) {
        return userLikeMapper.findByID(userLike);
    }

    // 其他需要的方法根据业务需求添加
}

