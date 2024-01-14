package com.hello.entry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    private Long commentID;
    private Long postID;
    private Long userID;
    private String content;

    private User user;  // Add user property to store commenter information
}
