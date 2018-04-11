package com.mooc.api.service;


import java.util.List;

import com.mooc.api.common.PageData;
import com.mooc.api.common.PageParams;
import com.mooc.api.dao.UserDao;
import com.mooc.api.model.Agency;
import com.mooc.api.model.ListResponse;
import com.mooc.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgencyService {

    @Autowired
    private UserDao userDao;


    public List<Agency> getAllAgency() {
        return userDao.getAllAgency();
    }

    public Agency getAgency(Integer id) {
        return userDao.getAgencyById(id);
    }

    public void add(Agency agency) {
        userDao.addAgency(agency);
    }

    public PageData<User> getAllAgent(PageParams pageParams) {
        ListResponse<User> result = userDao.getAgentList(pageParams.getLimit(), pageParams.getOffset());
        Long count = result.getCount();
        return PageData.<User>buildPage(result.getList(), count, pageParams.getPageSize(), pageParams.getPageNum());
    }


    public User getAgentDetail(Long id) {
        return userDao.getAgentById(id);
    }


}
