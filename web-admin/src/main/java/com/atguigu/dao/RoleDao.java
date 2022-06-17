package com.atguigu.dao;

import com.atguigu.entity.Role;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RoleDao {
    List<Role> findAll();
    Integer insert(Role role);
    Role getById(Long id);
    Integer update(Role role);
    void delete(Long id);
    Page<Role> findPage(Map<String, Object> filters);
}