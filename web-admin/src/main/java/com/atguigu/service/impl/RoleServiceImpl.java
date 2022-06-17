package com.atguigu.service.impl;

import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.RoleDao;
import com.atguigu.entity.Role;
import com.atguigu.service.RoleService;
import com.atguigu.util.CastUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

   @Autowired  //Spring框架提供的依赖注入注解  先byType再byName
//   @Resource  //JDK提供的依赖注入注解   先byName再byType
   RoleDao roleDao;
   @Override
   public List<Role> findAll() {
      return roleDao.findAll();
   }


   @Override
   public BaseDao<Role> getEntityDao() {
      return roleDao;
   }
}