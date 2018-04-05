package com.mooc.house.controller;

import com.google.common.collect.Lists;
import com.mooc.house.biz.service.AgencyService;
import com.mooc.house.biz.service.HouseService;
import com.mooc.house.common.model.House;
import com.mooc.house.common.model.User;
import com.mooc.house.common.page.PageData;
import com.mooc.house.common.page.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class AgencyController {

    @Autowired
    private AgencyService agencyService;

    @Autowired
    private HouseService houseService;

    @RequestMapping("/agency/agentList")
    public String agentList(Integer pageSize, Integer pageNum, ModelMap modelMap) {

        PageData<User> allAgent = agencyService.getAllAgent(PageParams.build(pageSize, pageNum));

        modelMap.put("ps", allAgent);
        modelMap.put("recomHouses", Lists.newArrayList());

        return "/user/agent/agentList";
    }

    @RequestMapping("agency/agentDetail")
    public String agentDetail(Long id, ModelMap modelMap) {
        User user = agencyService.getAgentDetail(id);

        House query = new House();
        query.setUserId(id);
        query.setBookmarked(false);

        PageData<House> bindHouse = houseService.queryHouse(query, new PageParams(3, 1));
        if (bindHouse != null) {
            modelMap.put("bindHouses", bindHouse.getList());
        }
        modelMap.put("agent", user);
        modelMap.put("recomHouses", Lists.newArrayList());
        modelMap.put("agencyName",user.getAgencyName());
        return "/user/agent/agentDetail";
    }
}
