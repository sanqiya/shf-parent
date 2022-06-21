package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.HouseUser;

import java.util.List;

public interface HouseUserDao extends BaseDao<HouseUser> {
    List<HouseUser> findListByHouseId(Long houseId);
}