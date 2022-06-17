package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.Role;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RoleDao extends BaseDao<Role>{
    List<Role> findAll();
}