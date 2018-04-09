package com.mooc.micro.user.controller;

import com.mooc.micro.user.common.RestResponse;
import com.mooc.micro.user.exception.IllegalParamsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Value("${server.port}")
    private Integer port;

    @RequestMapping("getUserName")
    public RestResponse<String> getUserName(Long id){
        LOGGER.info("into - getUserName");
        if (id == 1){
            throw new IllegalParamsException(IllegalParamsException.Type.WRONG_PAGE_NUM,"错误分页");
        }
        return RestResponse.success("test-username " + port);
    }
}
