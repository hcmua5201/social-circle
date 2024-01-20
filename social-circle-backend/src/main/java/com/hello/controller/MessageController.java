package com.hello.controller;

import cn.hutool.core.util.RandomUtil;

import com.hello.entry.EmailMessage;
import com.hello.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;


@RestController
@CrossOrigin
@RequestMapping("/email")
public class MessageController {


    @Autowired
    MessageService messageService;

    //*不用redis可以删去该自动装配
    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @GetMapping("sendEmail/{getPeople}")
    public Result sendSimpleMail(@PathVariable("getPeople") String getPeople) {

        System.out.println(getPeople);

        //从Redis中获取code
        String RandomMath = redisTemplate.opsForValue().get(getPeople);
        System.out.println("redis根据输入的邮箱查询库中是否存在验证码，返回值:" + RandomMath);
        if (!StringUtils.isEmpty(RandomMath)) {  //验证码还未过期，在库中，可以直接登录
            return new Result(444,"验证码还未过期，请输入", RandomMath);
        }

        //验证码过期或者第一次登录生成验证码
        RandomMath = RandomUtil.randomNumbers(6);
        System.out.println("随机验证码为" + RandomMath);
        // 构建一个邮件对象
        Boolean mail = messageService.sendMail(RandomMath, getPeople);
        if (mail) {
            //设置有效时间,这里是2分钟，
            redisTemplate.opsForValue().set(getPeople, RandomMath,120 , TimeUnit.SECONDS);
            return new Result(222,"验证码发送成功，2分钟之后失效", RandomMath);
        }
        return new Result(444,"验证码发送失败", RandomMath);
    }

    @PostMapping("/QueryVerificationCode")
    public Result loginE(@RequestBody EmailMessage emailMessage) {
        System.out.println("前端传递的邮箱号:" + emailMessage.getEmail());
        System.out.println("前端传递的验证码:" + emailMessage.getMath());
        //查询redis数据库中根据邮箱号查询验证码
        String s = redisTemplate.opsForValue().get(emailMessage.getEmail());
        System.out.println(s);
        if (emailMessage.getMath().equals(s)) {   //输入的验证码和redis数据库中生成的验证码相同时
            return new Result(222,"验证码正确", s);
        }
        return new Result(444,"验证码不一致", null);
    }


}
