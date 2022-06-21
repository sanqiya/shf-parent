package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleDao extends BaseDao<Role>{
    List<Role> findAll();
}