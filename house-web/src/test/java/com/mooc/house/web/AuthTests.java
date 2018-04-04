package com.mooc.house.web;

import com.mooc.house.biz.service.UserService;
import com.mooc.house.common.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AuthTests {

    @Autowired
    private UserService userService;

    @Test
    public void testAuth() {
        User auth = userService.auth("hu5675@126.com", "111111");
        assert auth != null;
    }
}
