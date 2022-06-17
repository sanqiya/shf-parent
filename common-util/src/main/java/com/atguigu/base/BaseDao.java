package com.atguigu.base;

import com.github.pagehelper.Page;

import java.io.Serializable;
import java.util.Map;
//通用接口 增删改查
public interface BaseDao<T> {
    Integer insert(T t);
    T getById(Serializable id);
    void update(T t);
    void delete(Serializable id);
    Page<T> findPage(Map<String, Object> filters);
}
