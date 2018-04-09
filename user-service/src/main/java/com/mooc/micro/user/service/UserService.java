package com.mooc.micro.user.service;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.mooc.micro.user.common.UserException;
import com.mooc.micro.user.controller.UserController;
import com.mooc.micro.user.mapper.UserMapper;
import com.mooc.micro.user.model.User;
import com.mooc.micro.user.utils.BeanHelper;
import com.mooc.micro.user.utils.HashUtils;
import com.mooc.micro.user.utils.JwtHelper;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MailService mailService;


    @Value("${file.prefix}")
    private String imgPrefix;

    public User getUserById(Long id) {
        String key = "user:"+id;
        String json =  redisTemplate.opsForValue().get(key);
        User user = null;
        if (Strings.isNullOrEmpty(json)) {
            user =  userMapper.selectById(id);
            user.setAvatar(imgPrefix + user.getAvatar());
            String string  = JSON.toJSONString(user);
            redisTemplate.opsForValue().set(key, string);
            redisTemplate.expire(key, 5, TimeUnit.MINUTES);
        }else {
            user = JSON.parseObject(json,User.class);
        }
        return user;
    }

    public List<User> getUserByQuery(User user) {
        List<User> users = userMapper.select(user);
        users.forEach(u -> {
            u.setAvatar(imgPrefix + u.getAvatar());
        });
        return users;
    }

    /**
     * 注册
     * @param user
     * @param enableUrl
     * @return
     */
    public boolean addAccount(User user, String enableUrl) {
        user.setPasswd(HashUtils.encryPassword(user.getPasswd()));
        BeanHelper.onInsert(user);
        userMapper.insert(user);
        registerNotify(user.getEmail(),enableUrl);
        return true;
    }

    /**
     * 发送注册激活邮件
     * @param email
     * @param enableUrl
     */
    private void registerNotify(String email, String enableUrl) {
        String randomKey = HashUtils.hashString(email) + RandomStringUtils.randomAlphabetic(10);
        redisTemplate.opsForValue().set(randomKey, email);
        redisTemplate.expire(randomKey, 1,TimeUnit.HOURS);
        String content = enableUrl +"?key="+  randomKey;

        LOGGER.info("发送邮件:" + content);
        mailService.sendSimpleMail("房产平台激活邮件", content, email);
    }

    public boolean enable(String key) {
        String email = redisTemplate.opsForValue().get(key);
        if (StringUtils.isBlank(email)) {
            throw new UserException(UserException.Type.USER_NOT_FOUND, "无效的key");
        }
        User updateUser = new User();
        updateUser.setEmail(email);
        updateUser.setEnable(1);
        userMapper.update(updateUser);
        return true;
    }

    public User auth(String email, String passwd) {

        if (StringUtils.isBlank(email) || StringUtils.isBlank(passwd)) {
            throw new UserException(UserException.Type.USER_AUTH_FAIL,"User Auth Fail");
        }
        User user = new User();
        user.setEmail(email);
        user.setPasswd(HashUtils.encryPassword(passwd));
        user.setEnable(1);
        List<User> list =  getUserByQuery(user);
        if (!list.isEmpty()) {
            User retUser = list.get(0);
            onLogin(retUser);
            return retUser;
        }
        throw new UserException(UserException.Type.USER_AUTH_FAIL,"User Auth Fail");

    }

    private void onLogin(User user) {
        String token =  JwtHelper.genToken(ImmutableMap.of("email", user.getEmail(), "name", user.getName(),"ts", Instant.now().getEpochSecond()+""));
        renewToken(token,user.getEmail());
        user.setToken(token);
    }

    private String renewToken(String token, String email) {
        redisTemplate.opsForValue().set(email, token);
        redisTemplate.expire(email, 30, TimeUnit.MINUTES);
        return token;
    }


    public User getLoginedUserByToken(String token) {
        Map<String, String> map = null;
        try {
            map = JwtHelper.verifyToken(token);
        } catch (Exception e) {
            throw new UserException(UserException.Type.USER_NOT_LOGIN,"User not login");
        }

        String email =  map.get("email");
        Long expired = redisTemplate.getExpire(email);
        if (expired > 0L) {
            renewToken(token, email);
            User user = getUserByEmail(email);
            user.setToken(token);
            return user;
        }
        throw new UserException(UserException.Type.USER_NOT_LOGIN,"user not login");
    }

    private User getUserByEmail(String email) {
        User user = new User();
        user.setEmail(email);
        List<User> list = getUserByQuery(user);
        if (!list.isEmpty()) {
            return list.get(0);
        }
        throw new UserException(UserException.Type.USER_NOT_FOUND,"User not found for " + email);
    }

}
