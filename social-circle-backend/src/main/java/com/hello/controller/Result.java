package com.hello.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private int code;   //自定义返回封装响应码
    private String msg;  //自定义返回处理消息
    private Object obj;  //自定义返回数据
}
