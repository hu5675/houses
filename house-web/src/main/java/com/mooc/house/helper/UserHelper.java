package com.mooc.house.helper;

import com.mooc.house.common.model.User;
import com.mooc.house.common.result.ResultMsg;
import org.apache.commons.lang3.StringUtils;

public class UserHelper {

    public static ResultMsg validate(User account) {
        if (StringUtils.isBlank(account.getEmail())) {
            return ResultMsg.errorMsg("Email 有误");
        }
        if (StringUtils.isBlank(account.getName())) {
            return ResultMsg.errorMsg("名字有误");
        }
        if (StringUtils.isBlank(account.getConfirmPasswd()) || !account.getPasswd().equals(account.getConfirmPasswd())) {
            return ResultMsg.errorMsg("密码不一致");
        }
        if (account.getPasswd().length() < 6) {
            return ResultMsg.errorMsg("密码大于6位");
        }
        return ResultMsg.successMsg("");
    }

}
