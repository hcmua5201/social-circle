package com.hello.mapper;

import com.hello.config.ListTypeHandler;
import com.hello.entry.Post;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface PostMapper {

    @Insert("INSERT INTO Post (userID, content, image, time) VALUES (#{author.userID}, #{content}, #{image}, #{time})")
    @Options(useGeneratedKeys = true, keyProperty = "postID")
    void insert(Post post);

    @Select("SELECT * FROM Post WHERE postID = #{postID}")
    Post findByID(Long postID);

    @Select("SELECT p.postID, p.content, p.image, p.time, u.avatar, u.nickname, u.backgroundImagePath, " +
            "COUNT(l.postID) as likeCount, " +
            "GROUP_CONCAT(DISTINCT c.content) as comments, " +
            "GROUP_CONCAT(DISTINCT u1.nickname) as commenters " +
            "FROM post p " +
            "INNER JOIN user u ON p.userID = u.userID " +
            "LEFT JOIN userlike l ON p.postID = l.postID " +
            "LEFT JOIN comment c ON p.postID = c.postID " +
            "LEFT JOIN user u1 ON c.userID = u1.userID " +
            "GROUP BY p.postID")
    @Results({
            @Result(property = "postID", column = "postID"),
            @Result(property = "content", column = "content"),
            @Result(property = "image", column = "image"),
            @Result(property = "time", column = "time"),
            @Result(property = "author.avatar", column = "avatar"),
            @Result(property = "author.nickname", column = "nickname"),
            @Result(property = "author.backgroundImagePath", column = "backgroundImagePath"),
            @Result(property = "likeCount", column = "likeCount"),
            @Result(property = "comments", column = "comments", typeHandler = ListTypeHandler.class),
            @Result(property = "commenters", column = "commenters", typeHandler = ListTypeHandler.class)
    })
    List<Post> getAllPosts();







    // 其他需要的方法根据业务需求添加
}

