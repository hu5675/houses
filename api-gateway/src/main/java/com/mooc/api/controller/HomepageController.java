package com.mooc.api.controller;

import java.util.List;

import com.google.common.collect.Lists;
import com.mooc.api.dao.UserDao;
import com.mooc.api.model.House;
import com.mooc.api.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HomepageController {

    @Autowired
    private HouseService houseService;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private UserDao userDao;

    @RequestMapping("index")
    public String accountsRegister(ModelMap modelMap) {
//        List<House> houses = houseService.getLastest();
//        modelMap.put("recomHouses", houses);
        modelMap.put("recomHouses", Lists.newArrayList());
        return "/homepage/index";
    }

    @RequestMapping("")
    public String index(ModelMap modelMap) {
        return "redirect:/index";
    }
}
