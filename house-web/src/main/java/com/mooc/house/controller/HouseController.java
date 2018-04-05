package com.mooc.house.controller;

import com.google.common.collect.Lists;
import com.mooc.house.biz.service.AgencyService;
import com.mooc.house.biz.service.CityService;
import com.mooc.house.biz.service.HouseService;
import com.mooc.house.biz.service.RecommendService;
import com.mooc.house.common.constant.CommonConstants;
import com.mooc.house.common.constant.HouseUserType;
import com.mooc.house.common.model.House;
import com.mooc.house.common.model.HouseUser;
import com.mooc.house.common.model.User;
import com.mooc.house.common.model.UserMsg;
import com.mooc.house.common.page.PageData;
import com.mooc.house.common.page.PageParams;
import com.mooc.house.common.result.ResultMsg;
import com.mooc.house.interceptor.UserContext;
import com.sun.org.apache.bcel.internal.generic.RET;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class HouseController {

    @Autowired
    private HouseService houseService;

    @Autowired
    private AgencyService agencyService;

    @Autowired
    private RecommendService recommendService;

    @Autowired
    private CityService cityService;

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
    public String houseDetail(Long id, ModelMap modelMap) {
        House house = houseService.queryOneHouse(id);
        HouseUser houseUser = houseService.getHouseUser(id);
        recommendService.increase(id);
        if (houseUser.getUserId() != null && !houseUser.getUserId().equals(0)) {
            modelMap.put("agent", agencyService.getAgentDetail(houseUser.getUserId()));
        }
        modelMap.put("house", house);
        modelMap.put("recomHouses", recommendService.getHotHouse(CommonConstants.RECOM_SIZE));
        modelMap.put("commentList", Lists.newArrayList());
        return "/house/detail";
    }

    @RequestMapping("house/leaveMsg")
    public String houseMsg(UserMsg userMsg) {
        houseService.addUserMsg(userMsg);
        return "redirect:/house/detail?id=" + userMsg.getHouseId() + "&" + ResultMsg.successMsg("留言成功").asUrlParams();
    }

    @RequestMapping("house/toAdd")
    public String toAdd(ModelMap modelMap) {
        modelMap.put("citys", cityService.getAllCity());
        modelMap.put("communitys", houseService.getAllCommunitys());
        return "house/add";
    }

    @RequestMapping("house/add")
    public String doAdd(House house) {
        User user = UserContext.getUser();

        house.setState(CommonConstants.HOUSE_STATE_UP);

        houseService.addHouse(house, user);

        return "redirect:/house/ownlist";
    }

    @RequestMapping("house/ownlist")
    public String ownlist(House house, Integer pageSize, Integer pageNum, ModelMap modelMap) {
        User user = UserContext.getUser();
        house.setUserId(user.getId());
        house.setBookmarked(false);

        modelMap.put("ps", houseService.queryHouse(house, PageParams.build(pageSize, pageNum)));
        modelMap.put("pageType", "own");

        return "house/ownlist";
    }

    @ResponseBody
    @RequestMapping("house/rating")
    public ResultMsg houseRate(Double rating, Long id) {
        houseService.updateRating(id, rating);
        return ResultMsg.successMsg("ok");
    }

    @ResponseBody
    @RequestMapping("house/bookmark")
    public ResultMsg bookmark(Long id) {
        User user = UserContext.getUser();
        houseService.bindUser2House(id, user.getId(), true);
        return ResultMsg.successMsg("ok");
    }

    @ResponseBody
    @RequestMapping("house/unbookmark")
    public ResultMsg unbookmark(Long id) {
        User user = UserContext.getUser();
        houseService.unbindUser2House(id, user.getId(), HouseUserType.BOOKMARK);
        return ResultMsg.successMsg("ok");
    }

    @RequestMapping(value = "house/del")
    public String delsale(Long id, String pageType) {
        User user = UserContext.getUser();
        houseService.unbindUser2House(id, user.getId(), pageType.equals("own") ? HouseUserType.SALE : HouseUserType.BOOKMARK);
        return "redirect:/house/ownlist";
    }

    @RequestMapping("house/bookmarked")
    public String bookmarded(House house, Integer pageNum, Integer pageSize, ModelMap modelMap) {
        User user = UserContext.getUser();

        house.setBookmarked(true);
        house.setUserId(user.getId());
        modelMap.put("ps", houseService.queryHouse(house, PageParams.build(pageSize, pageNum)));

        modelMap.put("pageType", "book");
        return "/house/ownlist";
    }
}
