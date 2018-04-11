package com.mooc.house.srv.service;

import java.util.List;

import com.mooc.house.srv.mapper.CityMapper;
import com.mooc.house.srv.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {

    @Autowired
    private CityMapper cityMapper;

    public List<City> getAllCitys() {
        City query = new City();
        return cityMapper.selectCitys(query);
    }

}
