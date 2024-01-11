package com.hello.entry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLike implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "postID")
    private Post post;

    @Id
    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;

    public UserLike(Long postID, Long userID) {

    }
}

