package com.hello.service.impl;

import com.hello.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MessageServiceimpl implements MessageService {
    @Autowired
    JavaMailSender javaMailSender;

    //读取配置文件中username的值并赋值给setPeople
    @Value("${spring.mail.username}")
    String setPeople;

    public boolean sendMail(String code, String getPeople) {
        SimpleMailMessage message = new SimpleMailMessage();
        // 设置邮件发送者
        message.setFrom(setPeople);
        // 设置邮件接收者
        message.setTo(getPeople);
        // 设置邮件的主题
        message.setSubject("注册验证码");

        // 设置邮件的正文内容
        String text = "您的验证码为：" + code + ",5分钟后失效,请勿泄露给他人。";
        message.setText(text);
        // 发送邮件
        try {
            javaMailSender.send(message);
            return true;
        } catch (MailException e) {
            e.printStackTrace();
        }
        return false;
    }
}
