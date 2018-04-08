package com.mooc.api.service;

import com.mooc.api.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public String getUserName(Long id){
        return userDao.getUserName(id);
    }
}
