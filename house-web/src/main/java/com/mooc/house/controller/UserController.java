package com.mooc.house.controller;

import com.mooc.house.biz.service.UserService;
import com.mooc.house.common.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("users")
    public List<User> getUsers(){
        return  this.userService.getUsers();
    }
}
