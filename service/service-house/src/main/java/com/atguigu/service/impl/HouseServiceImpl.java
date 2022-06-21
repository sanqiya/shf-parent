package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.HouseDao;
import com.atguigu.entity.House;
import com.atguigu.service.DictService;
import com.atguigu.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Transactional
@Service(interfaceClass = HouseService.class)
public class HouseServiceImpl extends BaseServiceImpl<House> implements HouseService {

   @Autowired
   private DictService dictService;

   @Autowired
   private HouseDao houseDao;

   @Override
   public BaseDao<House> getEntityDao() {
      return houseDao;
   }
   @Override
   public void publish(Long id, Integer status) {
      House house = new House();
      house.setId(id);
      house.setStatus(status);
      houseDao.update(house);
   }
   @Override
   public House getById(Serializable id) {
      House house = houseDao.getById(id);
      if(null == house) return null;

      //户型：
      String houseTypeName = dictService.getNameById(house.getHouseTypeId());
      //楼层
      String floorName = dictService.getNameById(house.getFloorId());
      //建筑结构：
      String buildStructureName = dictService.getNameById(house.getBuildStructureId());
      //朝向：
      String directionName = dictService.getNameById(house.getDirectionId());
      //装修情况：
      String decorationName = dictService.getNameById(house.getDecorationId());
      //房屋用途：
      String houseUseName = dictService.getNameById(house.getHouseUseId());
      house.setHouseTypeName(houseTypeName);
      house.setFloorName(floorName);
      house.setBuildStructureName(buildStructureName);
      house.setDirectionName(directionName);
      house.setDecorationName(decorationName);
      house.setHouseUseName(houseUseName);
      return house;
   }
}