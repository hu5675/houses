package com.mooc.micro.user.controller;

import com.mooc.micro.user.common.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @RequestMapping("getUserName")
    public RestResponse<String> getUserName(Long id){
        LOGGER.info("into - getUserName");
        return RestResponse.success("test-username");
    }
}
