package com.hello.service;

import com.hello.entry.UserLike;
import org.apache.ibatis.annotations.Param;

public interface UserLikeService {

    void insert(@Param("userID") Long userID,@Param("postID") Long postID);

    void delete(@Param("userID") Long userID, @Param("postID") Long postID);

    UserLike getUserLike(UserLike userLike);

    // 其他需要的方法根据业务需求添加
}

