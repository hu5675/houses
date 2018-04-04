package com.mooc.house.biz.mapper;

import com.mooc.house.common.model.Community;
import com.mooc.house.common.model.House;
import com.mooc.house.common.page.PageParams;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HouseMapper {

    public List<House> selectPageHouses(@Param("house") House house, @Param("pageParams") PageParams pageParams);

    public Long selectPageCount(@Param("house") House query);

    List<Community> selectCommunity(Community community);
}
