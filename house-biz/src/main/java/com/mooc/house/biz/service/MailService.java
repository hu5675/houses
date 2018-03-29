package com.mooc.house.biz.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.mooc.house.biz.mapper.UserMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class MailService {

    @Autowired
    private UserMapper userMapper;

    private final Cache<String,String> registerCache = CacheBuilder.newBuilder()
            .maximumSize(100)
            .expireAfterAccess(15, TimeUnit.MINUTES)
            .removalListener(new RemovalListener<String, String>() {
                @Override
                public void onRemoval(RemovalNotification<String, String> notification) {
                    userMapper.delete(notification.getKey());
                }
            }).build();


    @Autowired
    private JavaMailSender mailSender;


    @Value("${spring.mail.username}")
    private String from;

    @Value("${domain.name")
    private String domainName;

    @Async
    public void registerNotify(String email) {
        String randomKey = RandomStringUtils.randomAlphabetic(10);
        registerCache.put(randomKey,email);

        String url =  "http://" + domainName + "/account/verify?key="+randomKey;
        sendMail("房产平台激活邮件",url,email);
    }

    public void sendMail(String title, String url, String email) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setText(url);
        mailSender.send(message);
    }
}
