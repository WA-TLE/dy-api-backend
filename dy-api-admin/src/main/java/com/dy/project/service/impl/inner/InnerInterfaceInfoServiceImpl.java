package com.dy.project.service.impl.inner;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.dy.common.model.entity.InterfaceInfo;
import com.dy.common.service.InnerInterfaceInfoService;
import com.dy.project.common.ErrorCode;
import com.dy.project.exception.BusinessException;
import com.dy.project.mapper.InterfaceInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;


import javax.annotation.Resource;

/**
 * @Author: dy
 * @Date: 2024/2/19 21:53
 * @Description:
 */
@Slf4j
@DubboService
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {

    @Resource
    InterfaceInfoMapper interfaceInfoMapper;


    @Override
    public InterfaceInfo getInterfaceInfo(String url, String method) {

        //  1. 判断参数是否合法
        if (StrUtil.hasBlank(url, method)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }


        LambdaQueryWrapper<InterfaceInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .eq(InterfaceInfo::getUrl, url)
                .eq(InterfaceInfo::getMethod, method);

        InterfaceInfo interfaceInfo = interfaceInfoMapper.selectOne(lambdaQueryWrapper);

        log.info("interfaceInfo -> {}", interfaceInfo);

        return interfaceInfo;
    }
}
