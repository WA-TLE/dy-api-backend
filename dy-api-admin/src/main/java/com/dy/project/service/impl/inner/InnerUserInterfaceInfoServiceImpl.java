package com.dy.project.service.impl.inner;

import com.dy.common.service.InnerUserInterfaceInfoService;
import com.dy.project.service.UserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @Author: dy
 * @Date: 2024/2/19 21:53
 * @Description:
 */
@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;


    @Override
    public boolean invokeCount(Long interfaceInfoId, Long userId) {
        // TODO: 2024/2/19 是否还有调用次数?
        return userInterfaceInfoService.invokeCount(interfaceInfoId, userId);
    }
}
