package com.dy.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dy.common.model.entity.InterfaceInfo;
import com.dy.project.common.ErrorCode;
import com.dy.project.exception.BusinessException;
import com.dy.project.mapper.InterfaceInfoMapper;
import com.dy.project.service.InterfaceInfoService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author dy
 * @description 针对表【interface_info(接口信息)】的数据库操作Service实现
 * @createDate 2023-12-20 15:21:55
 */
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
        implements InterfaceInfoService {

    /**
     * 判断接口信息是否有效
     *
     * @param interfaceInfo
     * @param add
     */
    @Override
    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add) {


        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String name = interfaceInfo.getName();
        String description = interfaceInfo.getDescription();
        String url = interfaceInfo.getUrl();
        String requestHeader = interfaceInfo.getRequestHeader();
        String responseHeader = interfaceInfo.getResponseHeader();
        String method = interfaceInfo.getMethod();
        Long userId = interfaceInfo.getUserId();
        String requestParams = interfaceInfo.getRequestParams();


        //  创建时，所有参数必须非空
        //  这里我最初写的是 &&, 但是我的本意是如果任何一个参数为 null 或者 " ", 我就抛出异常
        //  也就是说, 我在创建接口的时候, 这些参数都不准为 null 所以这里应该用 "||"
        // TODO: 2024/2/2 这里的逻辑有点小问题, 创建的时候所有的参数必须都是非空的!!
        //  归根结底在于用户id 并没有得到!!!!
        if (add) {
            if (StringUtils.isAnyBlank(name, description, url, requestHeader, responseHeader, requestParams, method) ||
            ObjectUtils.anyNull(userId)) {

                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
        if (StringUtils.isNotBlank(description) && description.length() > 512) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "描述过长");
        }


    }


}




