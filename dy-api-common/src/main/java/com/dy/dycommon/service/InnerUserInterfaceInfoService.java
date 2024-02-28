package com.dy.dycommon.service;

/**
 * @Author: dy
 * @Date: 2024/2/19 21:24
 * @Description: 负责用户-接口信息表的更改
 */
public interface InnerUserInterfaceInfoService {
     /**
      * 根据接口 id 和用户 id, 增加接口调用次数
      *
      * @param interfaceInfoId
      * @param userId
      * @return
      */
     boolean invokeCount(Long interfaceInfoId, Long userId);
}
