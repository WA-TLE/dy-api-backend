package com.dy.project.service.impl.inner;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dy.common.model.entity.User;
import com.dy.common.service.InnerUserService;
import com.dy.project.common.ErrorCode;
import com.dy.project.exception.BusinessException;
import com.dy.project.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;


/**
 * @Author: dy
 * @Date: 2024/2/19 21:56
 * @Description:
 */

@Slf4j
@DubboService

public class InnerUserServiceImpl implements InnerUserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User getUserByAccessKey(String accessKey) {

        if (StrUtil.hasBlank(accessKey)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>()
                .eq(User::getAccessKey, accessKey);

        User user = userMapper.selectOne(wrapper);

        log.info("user -> {}", user);

        return user;
    }
}

