package com.mooc.house.service;

import com.mooc.house.common.model.User;
import com.mooc.house.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public List<User> getUsers(){
        return  this.userMapper.selectUsers();
    }
}
