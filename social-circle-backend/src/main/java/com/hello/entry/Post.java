package com.hello.entry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    private Long postID;

    private User author;

    private String content;

    private String image;

    private Date time;

    private String address;

    private int likeCount;

    private List<User> likeUsers;

    private List<Comment> comments;

    private List<String> commenters;

    // 修改 likeUserIds 的类型为 List<String>
    private List<Long> likeUserIds;
    private List<String> likeUsernames;
    private List<String> likeUserAvatars;
    private List<String> likeUserBackgroundImagePaths;

    // 添加 likeUserIds 的 setter 方法
    // 添加 likeUserIds 的 setter 方法
    // 添加 likeUserIds 的 setter 方法
    public void setLikeUserIds(List<String> likeUserIds) {
        // 在这里手动将 String 转换为 Long
        this.likeUserIds = likeUserIds.stream().map(Long::valueOf).collect(Collectors.toList());
    }


    // 添加 likeUsernames 的 setter 方法
    public void setLikeUsernames(List<String> likeUsernames) {
        this.likeUsernames = likeUsernames;
    }

    // 添加 likeUserAvatars 的 setter 方法
    public void setLikeUserAvatars(List<String> likeUserAvatars) {
        this.likeUserAvatars = likeUserAvatars;
    }

    // 添加 likeUserBackgroundImagePaths 的 setter 方法
    public void setLikeUserBackgroundImagePaths(List<String> likeUserBackgroundImagePaths) {
        this.likeUserBackgroundImagePaths = likeUserBackgroundImagePaths;
    }

    // 添加 commenters 的 setter 方法
    public void setCommenters(List<String> commenters) {
        this.commenters = commenters;
    }

    // 其他字段根据需要添加
}


