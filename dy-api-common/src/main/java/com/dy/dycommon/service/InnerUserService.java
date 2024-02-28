package com.dy.dycommon.service;

import com.dy.dycommon.model.entity.User;

/**
 * @Author: dy
 * @Date: 2024/2/19 21:24
 * @Description: 获取用户信息
 */
public interface InnerUserService {
     /**
      * 根据 accessKey 来查询用户信息
      *
      * @param accessKey
      * @return
      */
     User getUserByAccessKey(String accessKey);
}
