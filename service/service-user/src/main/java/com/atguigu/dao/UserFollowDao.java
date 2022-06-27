package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.UserFollow;
import com.atguigu.vo.UserFollowVo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

public interface UserFollowDao extends BaseDao<UserFollow>{
    Integer countByUserIdAndHouserId(@Param("userId") Long userId, @Param("houseId") Long houseId);
    Page<UserFollowVo> findListPage(@Param("userId")Long userId);
}