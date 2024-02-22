package com.dy.common.service;

import com.dy.common.model.entity.InterfaceInfo;

/**
 * @Author: dy
 * @Date: 2024/2/19 21:24
 * @Description: 查看接口信息
 */
public interface InnerInterfaceInfoService {
    /**
     * 根据接口路径和方法获取请求接口
     *
     * @param path
     * @param method
     * @return
     */
    InterfaceInfo getInterfaceInfo(String path, String method);
}
