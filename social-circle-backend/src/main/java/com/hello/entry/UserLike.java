package com.hello.entry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLike implements Serializable {


    private Post post;


    private User user;

    public UserLike(Long postID, Long userID) {

    }
}

