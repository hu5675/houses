package com.mooc.api.controller;

import com.mooc.api.common.RestResponse;
import com.mooc.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiUserController {

    @Autowired
    private UserService userService;

    @RequestMapping("test/getUserName")
    public RestResponse<String> getUserName(Long id) {
        return RestResponse.success(userService.getUserName(id));
    }
}
