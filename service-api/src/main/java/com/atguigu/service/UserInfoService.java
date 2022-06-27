package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.UserInfo;

public interface UserInfoService extends BaseService<UserInfo> {

    UserInfo getByPhone(String phone);
}
