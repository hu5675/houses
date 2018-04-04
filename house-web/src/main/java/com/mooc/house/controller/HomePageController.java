package com.mooc.house.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomePageController {

    @RequestMapping("index")
    public String homeIndex(ModelMap modelMap){
        return "/homepage/index";
    }

    @RequestMapping("")
    public String index(ModelMap modelMap){
        return "redirect:/index";
    }
}
