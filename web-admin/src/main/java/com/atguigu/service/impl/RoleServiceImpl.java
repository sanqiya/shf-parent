package com.atguigu.service.impl;

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
public class RoleServiceImpl implements RoleService {

   @Autowired  //Spring框架提供的依赖注入注解  先byType再byName
//   @Resource  //JDK提供的依赖注入注解   先byName再byType
   RoleDao roleDao;
   @Override
   public List<Role> findAll() {
      return roleDao.findAll();
   }

   @Override
   public Integer insert(Role role) {
      return roleDao.insert(role);
   }
   @Override
   public Role getById(Long id) {
      return roleDao.getById(id);
   }

   @Override
   public Integer update(Role role) {
      return roleDao.update(role);
   }
   @Override
   public void delete(Long id) {
      roleDao.delete(id);
   }

   @Override
   public PageInfo<Role> findPage(Map<String, Object> filters) {
      //当前页数
      int pageNum = CastUtil.castInt(filters.get("pageNum"), 1);
      //每页显示的记录条数
      int pageSize = CastUtil.castInt(filters.get("pageSize"), 10);
//      开启分页功能 将这两个数据绑定到当前线程上
//      startIndex = （pageNum-1）* pageSize
//      select 语句后会自动添加limit     limit startIndex，pageSize
      PageHelper.startPage(pageNum, pageSize);

      return new PageInfo<Role>(roleDao.findPage(filters), 10);
   }
}