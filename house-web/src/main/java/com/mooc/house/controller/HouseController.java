package com.mooc.house.controller;

import com.google.common.collect.Lists;
import com.mooc.house.biz.service.HouseService;
import com.mooc.house.common.constant.CommonConstants;
import com.mooc.house.common.model.House;
import com.mooc.house.common.page.PageData;
import com.mooc.house.common.page.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HouseController {

    @Autowired
    private HouseService houseService;

    /**
     * 1、分页
     * 2、支持小区搜索、类型搜索
     * 3、支持排序
     * 4、支持展示图片、价格、标题
     * @return
     */
    @RequestMapping("/house/list")
    public String houseList(Integer pageSize, Integer pageNum, House query, ModelMap modelMap){
        PageData pageData = houseService.queryHouse(query, PageParams.build(pageSize, pageNum));
        modelMap.put("ps",pageData);
        modelMap.put("vo",query);
        List<House> hotHouses = Lists.newArrayList();// recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
        modelMap.put("recomHouses", hotHouses);

        return "house/listing";
    }
}
