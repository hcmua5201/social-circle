package com.hello.mapper;

import com.hello.entry.Comment;
import com.hello.entry.Post;
import com.hello.entry.vo.UpdateComment;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface CommentMapper {

    @Insert("INSERT INTO Comment (postID, userID, content) VALUES (#{postID}, #{userID}, #{content})")

    int insert(@Param("postID") Long postID,@Param("userID") Long userID,@Param("content") String content);

    @Select("SELECT * FROM Comment WHERE commentID = #{commentID}")
    Comment findByID(Long commentID);

    @Select("SELECT * FROM Comment WHERE postID = #{post.postID}")
    List<Comment> findByPostID(Post post);

    @Select("SELECT * FROM comment c, user u, post p WHERE c.userID = u.userID AND c.postID = p.postID ORDER BY c.commentID")
    List<UpdateComment> queryAll();
    // 其他需要的方法根据业务需求添加
}
