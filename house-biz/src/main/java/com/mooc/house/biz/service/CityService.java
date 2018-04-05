package com.mooc.house.biz.service;

import com.google.common.collect.Lists;
import com.mooc.house.common.model.City;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    public List<City> getAllCity(){
        City city = new City();
        city.setCityCode("110000");
        city.setCityName("北京市");
        city.setId(1);
        return Lists.newArrayList(city);
    }
}
