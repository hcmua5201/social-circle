package com.hello.mapper;

import com.hello.entry.Comment;
import com.hello.entry.Post;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface CommentMapper {

    @Insert("INSERT INTO Comment (postID, userID, content) VALUES (#{post.postID}, #{user.userID}, #{content})")
    @Options(useGeneratedKeys = true, keyProperty = "commentID")
    void insert(Comment comment);

    @Select("SELECT * FROM Comment WHERE commentID = #{commentID}")
    Comment findByID(Long commentID);

    @Select("SELECT * FROM Comment WHERE postID = #{post.postID}")
    List<Comment> findByPostID(Post post);

    // 其他需要的方法根据业务需求添加
}
