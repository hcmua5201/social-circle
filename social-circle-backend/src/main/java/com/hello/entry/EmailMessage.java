package com.hello.entry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



//验证码发送，不用创建表
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailMessage {
    private String email;
    private String math;
}
