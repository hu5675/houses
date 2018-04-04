package com.mooc.house.controller;

import com.mooc.house.biz.service.UserService;
import com.mooc.house.common.constant.CommonConstants;
import com.mooc.house.common.model.User;
import com.mooc.house.common.result.ResultMsg;
import com.mooc.house.common.utils.HashUtils;
import com.mooc.house.helper.UserHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("users")
    @ResponseBody
    public List<User> getUsers() {
        return this.userService.getUsers();
    }

    /**
     * 注册提交
     *
     * @param user
     * @return
     */
    @RequestMapping("accounts/register")
    public String accountRegister(User account, ModelMap modelMap) {
        if (account == null || account.getName() == null) {
            return "/user/accounts/register";
        }

//        用户验证
        ResultMsg resultMsg = UserHelper.validate(account);

        if (resultMsg.isSuccess() && userService.addAccount(account)) {
            modelMap.put("email", account.getEmail());
            return "/user/accounts/registerSubmit";
        } else {
            return "redirect:/accounts/register?" + resultMsg.asUrlParams();
        }
    }

    @RequestMapping("accounts/verify")
    public String verify(String key) {
        boolean result = userService.enable(key);
        if (result) {
            return "redirect:/index?" + ResultMsg.successMsg("激活成功").asUrlParams();
        } else {
            return "redirect:/accounts/register?" + ResultMsg.errorMsg("激活失败,请确认链接是否过期");
        }
    }

    @RequestMapping("accounts/signin")
    public String signin(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String target = request.getParameter("target");
        if (username == null || password == null) {
            request.setAttribute("target", target);
            return "/user/accounts/signin";
        }

        User user = userService.auth(username, password);
        if (user == null) {
            return "redirect:/accounts/signin?" + "target=" + target + "&username=" + username + "&" + ResultMsg.errorMsg("用户名或密码错误").asUrlParams();
        }
        HttpSession session = request.getSession(true);
        session.setAttribute(CommonConstants.USER_ATTRIBUTE, user);
//        session.setAttribute(CommonConstants.PLAIN_USER_ATTRIBUTE, user);
        return StringUtils.isNoneBlank(target) ? "redirect:" + target : "redirect:/index";
    }

    @RequestMapping("accounts/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.invalidate();
        return "redirect:/index";
    }

    @RequestMapping("accounts/profile")
    public String profile(HttpServletRequest request, User updateUser,ModelMap modelMap){
        if (updateUser.getEmail() == null) {
//            User user = (User)request.getSession(true).getAttribute(CommonConstants.USER_ATTRIBUTE);
//            modelMap.put(CommonConstants.USER_ATTRIBUTE,user);
            return "/user/accounts/profile";
        }
        userService.updateUser(updateUser,updateUser.getEmail());

        User query = new User();
        query.setEmail(updateUser.getEmail());
        List<User> list = userService.getUserByQuery(query);
        request.getSession(true).setAttribute(CommonConstants.USER_ATTRIBUTE,list.get(0));
        return "redirect:/accounts/profile?"+ResultMsg.successMsg("更新成功").asUrlParams();
    }

    @RequestMapping("accounts/changePassword")
    public  String changePassword(String email,String password,String newPassword,String confirmPassword,ModelMap modelMap){
        User user = userService.auth(email,password);
        if (user == null || !confirmPassword.equals(newPassword)){
            return  "redirect:/accounts/profile?" + ResultMsg.errorMsg("密码错误").asUrlParams();
        }
        User updateUser = new User();
        updateUser.setPasswd(HashUtils.encryPassword(newPassword));
        userService.updateUser(updateUser,email);
        return "redirect:/accounts/profile?" + ResultMsg.successMsg("更新成功").asUrlParams();
    }
}
