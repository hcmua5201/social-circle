package com.hello.entry.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateComment {

    private Long commentID;
    private Long postID;
    private Long userID;
    private String content;
    private String nickname;
}
