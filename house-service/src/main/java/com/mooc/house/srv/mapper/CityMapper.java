package com.mooc.house.srv.mapper;


import com.mooc.house.srv.model.City;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CityMapper {

    public List<City> selectCitys(City city);

}
