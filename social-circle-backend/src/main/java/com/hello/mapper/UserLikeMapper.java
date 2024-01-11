package com.hello.mapper;

import com.hello.entry.UserLike;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface UserLikeMapper {

    @Insert("INSERT INTO UserLike (postID, userID) VALUES (#{post.postID}, #{user.userID})")
    void insert(UserLike userLike);

    @Delete("DELETE FROM UserLike WHERE postID = #{post.postID} AND userID = #{user.userID}")
    void delete(UserLike userLike);

    @Select("SELECT * FROM UserLike WHERE postID = #{post.postID} AND userID = #{user.userID}")
    UserLike findByID(UserLike userLike);

    // 其他需要的方法根据业务需求添加
}

