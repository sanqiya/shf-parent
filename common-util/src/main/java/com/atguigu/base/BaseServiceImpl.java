package com.atguigu.base;

import com.atguigu.util.CastUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.Map;

/**
 * 项目名: shf-parent
 * 文件名: BaseServiceImpl
 * 创建者: 曹勇
 * 创建时间:2022/6/17 20:23
 * 描述: TODO
 **/
public abstract class BaseServiceImpl<T> implements BaseService<T> {
    public abstract BaseDao<T> getEntityDao();
    @Override
    public Integer insert(T t) {
        return getEntityDao().insert(t);
    }

    @Override
    public T getById(Serializable id) {
        return getEntityDao().getById(id);
    }

    @Override
    public void update(T t) {
        getEntityDao().update(t);
    }

    @Override
    public void delete(Serializable id) {
        getEntityDao().delete(id);
    }

    @Override
    public PageInfo<T> findPage(Map<String, Object> filters) {
        //当前页数
        int pageNum = CastUtil.castInt(filters.get("pageNum"), 1);
        //每页显示的记录条数
        int pageSize = CastUtil.castInt(filters.get("pageSize"), 10);
//      开启分页功能 将这两个数据绑定到当前线程上
//      startIndex = （pageNum-1）* pageSize
//      select 语句后会自动添加limit     limit startIndex，pageSize
        PageHelper.startPage(pageNum, pageSize);

        return new PageInfo<T>(getEntityDao().findPage(filters), 10);
    }
}
