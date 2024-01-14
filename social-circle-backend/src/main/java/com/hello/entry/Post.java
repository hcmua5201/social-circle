package com.hello.entry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    private Long postID;

    private User author;

    private String content;

    private String image;

    private Date time;

    private int likeCount;

    private List<User> likeUsers; // Add likeUsers property

    private List<Comment> comments; // Add comments property
    private List<String> commenters;

    // 添加 commenters 的 setter 方法
    public void setCommenters(List<String> commenters) {
        this.commenters = commenters;
    }
    // 其他字段根据需要添加
}
