package com.atguigu.base;

import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.Map;

public interface BaseService<T> {
    Integer insert(T t);
    T getById(Serializable id);
    void update(T t);
    void delete(Serializable id);
    PageInfo<T> findPage(Map<String, Object> filters);
}
