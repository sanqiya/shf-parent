package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.Community;

import java.util.List;

public interface CommunityDao extends BaseDao<Community> {
    List<Community> findAll();
}