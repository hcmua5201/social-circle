package com.hello.service;

import com.hello.entry.UserLike;

public interface UserLikeService {

    void addUserLike(UserLike userLike);

    void removeUserLike(UserLike userLike);

    UserLike getUserLike(UserLike userLike);

    // 其他需要的方法根据业务需求添加
}

