package com.dy.project.service.impl.inner;

import com.dy.dycommon.model.entity.UserInterfaceInfo;
import com.dy.dycommon.service.InnerUserInterfaceInfoService;
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
        return userInterfaceInfoService.invokeCount(interfaceInfoId, userId);
    }

    /**
     * 查看用户对该接口是否还有调用次数
     *
     * @param interfaceId
     * @param userId
     * @return
     */
    @Override
    public UserInterfaceInfo hasLeftNum(Long interfaceId, Long userId) {

        return userInterfaceInfoService.lambdaQuery()
                .eq(UserInterfaceInfo::getInterfaceInfoId, interfaceId)
                .eq(UserInterfaceInfo::getUserId, userId)
                .one();
    }
}
