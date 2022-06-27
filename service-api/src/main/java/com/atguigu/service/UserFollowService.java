package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.UserFollow;
import com.atguigu.vo.UserFollowVo;
import com.github.pagehelper.PageInfo;

public interface UserFollowService extends BaseService<UserFollow> {
    /**
     * 关注房源
     * @param userId
     * @param houseId
     */
    Boolean follow(Long userId, Long houseId);
    boolean isFollow(Long userInfoId, Long houseId);
    PageInfo<UserFollowVo> findListPage(int pageNum, int pageSize, Long userId);
    /**
     * 取消关注
     * @param id
     * @return
     */
    Boolean cancelFollow(Long id);
}
