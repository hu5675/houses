package com.mooc.micro.user.controller;

import com.mooc.micro.user.common.RestResponse;
import com.mooc.micro.user.exception.IllegalParamsException;
import com.mooc.micro.user.model.User;
import com.mooc.micro.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    //-------------------查询---------------------


    @RequestMapping("getById")
    public RestResponse<User> getUserById(Long id){
        User user = userService.getUserById(id);
        return RestResponse.success(user);
    }

    @RequestMapping("getList")
    public RestResponse<List<User>> getUserList(@RequestBody User user){
        List<User> users = userService.getUserByQuery(user);
        return RestResponse.success(users);
    }

    //----------------------注册----------------------------------

    @RequestMapping("add")
    public RestResponse<User> add(@RequestBody User user){
        userService.addAccount(user,user.getEnableUrl());
        return RestResponse.success();
    }

    /**
     * 主要激活key的验证
     */
    @RequestMapping("enable")
    public RestResponse<Object> enable(String key){
        userService.enable(key);
        return RestResponse.success();
    }

    //------------------------登录/鉴权--------------------------

    @RequestMapping("auth")
    public RestResponse<User> auth(@RequestBody User user){
        User finalUser = userService.auth(user.getEmail(),user.getPasswd());
        return RestResponse.success(finalUser);
    }

    @RequestMapping("get")
    public RestResponse<User> getUser(String token){
        User finalUser = userService.getLoginedUserByToken(token);
        return RestResponse.success(finalUser);
    }

    @RequestMapping("logout")
    public RestResponse<Object> logout(String token){
        userService.invalidate(token);
        return RestResponse.success();
    }
}
