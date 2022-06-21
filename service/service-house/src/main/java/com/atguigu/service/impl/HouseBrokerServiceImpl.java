package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.HouseBrokerDao;
import com.atguigu.entity.HouseBroker;
import com.atguigu.service.HouseBrokerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(interfaceClass = HouseBrokerService.class)
public class HouseBrokerServiceImpl extends BaseServiceImpl<HouseBroker> implements HouseBrokerService {

   @Autowired
   private HouseBrokerDao houseBrokerDao;

   @Override
   public BaseDao<HouseBroker> getEntityDao() {
      return houseBrokerDao;
   }

   @Override
   public List<HouseBroker> findListByHouseId(Long houseId) {
      return houseBrokerDao.findListByHouseId(houseId);
   }
}