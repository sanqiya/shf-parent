package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.House;

public interface HouseService extends BaseService<House> {
    void publish(Long id, Integer status);
}