package com.hello.mapper;

import com.hello.entry.User;
import com.hello.entry.UserLike;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface UserLikeMapper {

    @Insert("INSERT INTO UserLike (postID, userID) VALUES (#{postID}, #{userID})")
    void insert(@Param("userID") Long userID,@Param("postID") Long postID);

    @Delete("DELETE FROM UserLike WHERE postID = #{postID} AND userID = #{userID}")
    void delete(@Param("userID") Long userID,@Param("postID") Long postID);

    @Select("SELECT * FROM UserLike WHERE postID = #{post.postID} AND userID = #{user.userID}")
    UserLike findByID(UserLike userLike);

    @Select("SELECT u.* FROM userlike l " +
            "INNER JOIN user u ON l.userID = u.userID " +
            "WHERE l.postID = #{postID}")
    List<User> getLikeUsersByPostId(Long postID);
    // 其他需要的方法根据业务需求添加
}

