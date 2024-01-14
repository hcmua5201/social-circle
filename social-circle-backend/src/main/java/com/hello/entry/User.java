package com.hello.entry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;








@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    private Long userId;

    private String username;

    private String password;

    private String nickname;

    private String avatar;
    // 新添加的字段
    private String backgroundImagePath;


    private String email;


}

