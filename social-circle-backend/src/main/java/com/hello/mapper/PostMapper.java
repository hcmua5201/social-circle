package com.hello.mapper;

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

    @Select("SELECT * FROM Post")
    List<Post> findAll();

    // 其他需要的方法根据业务需求添加
}

