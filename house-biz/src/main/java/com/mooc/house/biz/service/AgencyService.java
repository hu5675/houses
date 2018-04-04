package com.mooc.house.biz.service;

import com.mooc.house.biz.mapper.AgencyMapper;
import com.mooc.house.common.model.Agency;
import com.mooc.house.common.model.House;
import com.mooc.house.common.model.User;
import com.mooc.house.common.page.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgencyService {

    @Autowired
    private AgencyMapper agencyMapper;

    @Value("${file.prefix}")
    private String imgPrefix;

    /**
     * 访问user表获取详情
     *
     * @param house
     * @return
     */
    public User getAgentDetail(Long userId) {
        User user = new User();
        user.setId(userId);
        user.setType(2);
        List<User> list = agencyMapper.selectAgent(user, PageParams.build(1, 1));
        setImg(list);
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    public void setImg(List<User> list) {
        list.forEach(i -> {
            i.setAvatar(imgPrefix + i.getAvatar());
        });
    }

    public List<Agency> getAllAgency() {
        return agencyMapper.select(new Agency());
    }
}
