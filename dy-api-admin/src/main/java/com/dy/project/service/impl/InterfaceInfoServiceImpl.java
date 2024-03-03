package com.dy.project.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dy.client.DyApiClient;
import com.dy.dycommon.common.ErrorCode;
import com.dy.dycommon.model.entity.InterfaceInfo;
import com.dy.dycommon.model.entity.User;
import com.dy.dycommon.model.vo.InterfaceInfoVO;
import com.dy.dycommon.model.vo.RequestParamsRemarkVO;
import com.dy.dycommon.model.vo.ResponseParamsRemarkVO;
import com.dy.dycommon.model.vo.UserVO;
import com.dy.project.exception.BusinessException;
import com.dy.project.mapper.InterfaceInfoMapper;
import com.dy.project.service.InterfaceInfoService;
import com.dy.project.service.UserService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author dy
 * @description 针对表【interface_info(接口信息)】的数据库操作Service实现
 * @createDate 2023-12-20 15:21:55
 */
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
        implements InterfaceInfoService {


    @Resource
    private UserService userService;


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
        //  2024/2/2 这里的逻辑有点小问题, 创建的时候所有的参数必须都是非空的!!
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

    /**
     * 获取 请求转发的客户端
     *
     * @param request
     * @return
     */
    @Override
    public DyApiClient getDyApiClient(HttpServletRequest request) {
        User user = userService.getLoginUser(request);
        String accessKey = user.getAccessKey();
        String secretKey = user.getSecretKey();

        return new DyApiClient(accessKey, secretKey);
    }

    @Override
    public InterfaceInfoVO getInterfaceInfoVO(InterfaceInfo interfaceInfo, HttpServletRequest request) {

        InterfaceInfoVO interfaceInfoVO = InterfaceInfoVO.objToVo(interfaceInfo);
        // 1. 关联查询用户信息
        Long userId = interfaceInfo.getUserId();
        User user = null;
        if (userId != null && userId > 0) {
            user = userService.getById(userId);
        }
        UserVO userVO = userService.getUserVO(user);
        interfaceInfoVO.setUser(userVO);
        // 封装请求参数说明 和 响应参数说明
        List<RequestParamsRemarkVO> requestParamsRemarkVOList = JSONUtil.toList(JSONUtil.parseArray(interfaceInfo.getRequestParamsRemark()), RequestParamsRemarkVO.class);
        List<ResponseParamsRemarkVO> responseParamsRemarkVOList = JSONUtil.toList(JSONUtil.parseArray(interfaceInfo.getResponseParamsRemark()), ResponseParamsRemarkVO.class);
        interfaceInfoVO.setRequestParamsRemark(requestParamsRemarkVOList);
        interfaceInfoVO.setResponseParamsRemark(responseParamsRemarkVOList);
        return interfaceInfoVO;



    }


}




