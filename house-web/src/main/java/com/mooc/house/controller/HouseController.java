package com.mooc.house.controller;

import com.google.common.collect.Lists;
import com.mooc.house.biz.service.AgencyService;
import com.mooc.house.biz.service.HouseService;
import com.mooc.house.biz.service.RecommendService;
import com.mooc.house.common.constant.CommonConstants;
import com.mooc.house.common.model.House;
import com.mooc.house.common.model.HouseUser;
import com.mooc.house.common.model.UserMsg;
import com.mooc.house.common.page.PageData;
import com.mooc.house.common.page.PageParams;
import com.mooc.house.common.result.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HouseController {

    @Autowired
    private HouseService houseService;

    @Autowired
    private AgencyService agencyService;

    @Autowired
    private RecommendService recommendService;

    /**
     * 1、分页
     * 2、支持小区搜索、类型搜索
     * 3、支持排序
     * 4、支持展示图片、价格、标题
     *
     * @return
     */
    @RequestMapping("/house/list")
    public String houseList(Integer pageSize, Integer pageNum, House query, ModelMap modelMap) {
        PageData pageData = houseService.queryHouse(query, PageParams.build(pageSize, pageNum));
        modelMap.put("ps", pageData);
        modelMap.put("vo", query);
        List<House> hotHouses = recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
        modelMap.put("recomHouses", hotHouses);

        return "house/listing";
    }

    @RequestMapping("house/detail")
    public String houseDetail(Long id,ModelMap modelMap) {
        House house = houseService.queryOneHouse(id);
        HouseUser houseUser = houseService.getHouseUser(id);
        recommendService.increase(id);
        if (houseUser.getUserId() != null && !houseUser.getUserId().equals(0)){
            modelMap.put("agent",agencyService.getAgentDetail(houseUser.getUserId()));
        }
        modelMap.put("house",house);
        modelMap.put("recomHouses", recommendService.getHotHouse(CommonConstants.RECOM_SIZE));
        modelMap.put("commentList", Lists.newArrayList());
        return "/house/detail";
    }

    @RequestMapping("house/leaveMsg")
    public String houseMsg(UserMsg userMsg){
        houseService.addUserMsg(userMsg);
        return "redirect:/house/detail?id=" + userMsg.getHouseId() + "&" +ResultMsg.successMsg("留言成功").asUrlParams();
    }
}
