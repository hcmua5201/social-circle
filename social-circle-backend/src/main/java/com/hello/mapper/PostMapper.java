package com.hello.mapper;

import com.hello.config.ListTypeHandler;
import com.hello.config.UserListTypeHandler;
import com.hello.entry.Post;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface PostMapper {

    @Insert("INSERT INTO Post (userID, content, image, time) VALUES (#{author.userID}, #{content}, #{image}, #{time})")
    @Options(useGeneratedKeys = true, keyProperty = "postID")
    void insert(Post post);

    @Insert("insert into post (userID, content, image, time, address) VALUES (#{userID},#{content},#{image},#{time},#{address})")
    int add(@Param("userID") Long userID,@Param("content") String content,@Param("image") String image,@Param("time") String time,@Param("address") String address);

    @Select("SELECT * FROM Post WHERE postID = #{postID}")
    Post findByID(Long postID);

    @Select("SELECT p.postID, p.content, p.image, p.time,p.address, u.avatar, u.nickname, u.backgroundImagePath, " +
            "COUNT(DISTINCT l.postID) as likeCount, " +
            "(SELECT GROUP_CONCAT(c1.content) FROM comment c1 WHERE c1.postID = p.postID) as comments, " +
            "(SELECT GROUP_CONCAT(u1.nickname) FROM comment c1 LEFT JOIN user u1 ON c1.userID = u1.userID WHERE c1.postID = p.postID) as commenters, " +
            "GROUP_CONCAT(DISTINCT u2.userId) as likeUserIds, " +
            "GROUP_CONCAT(DISTINCT u2.nickname) as likeUsernames, " +
            "GROUP_CONCAT(DISTINCT u2.avatar) as likeUserAvatars, " +
            "GROUP_CONCAT(DISTINCT u2.backgroundImagePath) as likeUserBackgroundImagePaths " +
            "FROM post p " +
            "INNER JOIN user u ON p.userID = u.userID " +
            "LEFT JOIN userlike l ON p.postID = l.postID " +
            "LEFT JOIN comment c ON p.postID = c.postID " +
            "LEFT JOIN user u1 ON c.userID = u1.userID " +
            "LEFT JOIN user u2 ON l.userID = u2.userID " +
            "GROUP BY p.postID")
    @Results({
            @Result(property = "postID", column = "postID"),
            @Result(property = "content", column = "content"),
            @Result(property = "image", column = "image"),
            @Result(property = "time", column = "time"),
            @Result(property = "address", column = "address"),
            @Result(property = "author.avatar", column = "avatar"),
            @Result(property = "author.nickname", column = "nickname"),
            @Result(property = "author.backgroundImagePath", column = "backgroundImagePath"),
            @Result(property = "likeCount", column = "likeCount"),
            @Result(property = "comments", column = "comments", typeHandler = ListTypeHandler.class),
            @Result(property = "commenters", column = "commenters", typeHandler = ListTypeHandler.class),
            @Result(property = "likeUserIds", column = "likeUserIds", typeHandler = ListTypeHandler.class),
            @Result(property = "likeUsernames", column = "likeUsernames", typeHandler = ListTypeHandler.class),
            @Result(property = "likeUserAvatars", column = "likeUserAvatars", typeHandler = ListTypeHandler.class),
            @Result(property = "likeUserBackgroundImagePaths", column = "likeUserBackgroundImagePaths", typeHandler = ListTypeHandler.class)
    })
    List<Post> getAllPosts();














    // 其他需要的方法根据业务需求添加
}

