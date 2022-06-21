package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Role;

import java.util.List;

public interface RoleService extends BaseService<Role> {
    List<Role> findAll();

}