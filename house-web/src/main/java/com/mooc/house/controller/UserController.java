package com.mooc.house.controller;

import com.mooc.house.biz.service.UserService;
import com.mooc.house.common.model.User;
import com.mooc.house.common.result.ResultMsg;
import com.mooc.house.helper.UserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("users")
    public List<User> getUsers() {
        return this.userService.getUsers();
    }

    /**
     * 注册提交
     * @param user
     * @return
     */
    @RequestMapping("accounts/register")
    public String accountRegister(User account) {
        if (account == null | account.getName() == null){
            return  "/user/accounts/register";
        }

//        用户验证
        ResultMsg resultMsg = UserHelper.validate(account);

        if (resultMsg.isSuccess() && userService.addAccount(account)){
            return "/user/accounts/registerSubmit";
        }else {
            return "redirect:/accounts/register?" + resultMsg.asUrlParams();
        }
    }
}
