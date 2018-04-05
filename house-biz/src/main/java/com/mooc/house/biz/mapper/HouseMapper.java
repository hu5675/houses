package com.mooc.house.biz.mapper;

import com.mooc.house.common.model.Community;
import com.mooc.house.common.model.House;
import com.mooc.house.common.model.HouseUser;
import com.mooc.house.common.model.UserMsg;
import com.mooc.house.common.page.PageParams;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HouseMapper {

    public List<House> selectPageHouses(@Param("house") House house, @Param("pageParams") PageParams pageParams);

    public Long selectPageCount(@Param("house") House query);

    List<Community> selectCommunity(Community community);

    void insertUserMsg(UserMsg userMsg);

    HouseUser selectSaleHouseUser(Long houseId);

    void insert(House house);

    HouseUser selectHouseUser(@Param("userId") Long userId, @Param("id") Long houseId, @Param("type") Integer type);

    Integer insertHouseUser(HouseUser houseUser);

    void updateHouse(House updateHouse);

    void deleteHouseUser(@Param("id") Long id, @Param("userId") Long userId, @Param("type") Integer type);

    void downHouse(Long id);
}

