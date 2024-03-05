package com.dy.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dy.client.DyApiClient;
import com.dy.dycommon.common.*;
import com.dy.dycommon.constant.CommonConstant;
import com.dy.dycommon.constant.UserConstant;
import com.dy.dycommon.model.dto.interfaceinfo.InterfaceInfoAddRequest;
import com.dy.dycommon.model.dto.interfaceinfo.InterfaceInfoInvokeRequest;
import com.dy.dycommon.model.dto.interfaceinfo.InterfaceInfoQueryRequest;
import com.dy.dycommon.model.dto.interfaceinfo.InterfaceInfoUpdateRequest;
import com.dy.dycommon.model.entity.InterfaceInfo;
import com.dy.dycommon.model.entity.User;
import com.dy.dycommon.model.enums.InterfaceInfoStatusEnum;
import com.dy.dycommon.model.vo.InterfaceInfoVO;
import com.dy.project.annotation.AuthCheck;


import com.dy.project.exception.BusinessException;

import com.dy.project.exception.ThrowUtils;
import com.dy.project.service.InterfaceInfoService;
import com.dy.project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 接口的接口哈哈哈
 *
 * @author dy
 */
@RestController
@RequestMapping("/interfaceInfo")
@Slf4j
public class InterfaceInfoController {

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Resource
    private UserService userService;




    // region 增删改查

    /**
     * 创建
     *
     * @param interfaceInfoAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addInterfaceInfo(@RequestBody InterfaceInfoAddRequest interfaceInfoAddRequest, HttpServletRequest request) {
        if (interfaceInfoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoAddRequest, interfaceInfo);

        // 获取当前用户 id
        Long currUserId = userService.getLoginUser(request).getId();

        interfaceInfo.setUserId(currUserId);


        // 校验
        // TODO: 2024/2/2 这里先改为 false, 等后期再优化用户 id
        interfaceInfoService.validInterfaceInfo(interfaceInfo, true);

        User loginUser = userService.getLoginUser(request);
        interfaceInfo.setUserId(loginUser.getId());
        boolean result = interfaceInfoService.save(interfaceInfo);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        long newInterfaceInfoId = interfaceInfo.getId();
        return ResultUtils.success(newInterfaceInfoId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteInterfaceInfo(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅管理员和接口创建者才可以删除接口
        if (!oldInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = interfaceInfoService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新
     *
     * @param interfaceInfoUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateInterfaceInfo(@RequestBody InterfaceInfoUpdateRequest interfaceInfoUpdateRequest,
                                                     HttpServletRequest request) {
        // TODO: 2024/3/3 这里可以使用注解来优化一下 管理员注解
        if (interfaceInfoUpdateRequest == null || interfaceInfoUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoUpdateRequest, interfaceInfo);
        // 参数校验
        interfaceInfoService.validInterfaceInfo(interfaceInfo, false);
        User user = userService.getLoginUser(request);
        long id = interfaceInfoUpdateRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可修改
        if (!oldInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<InterfaceInfoVO> getInterfaceInfoVOById(long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);

        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        //  将 interfaceInfo 转换为 interfaceInfoVO 的方法

        InterfaceInfoVO interfaceInfoVO = interfaceInfoService.getInterfaceInfoVO(interfaceInfo, request);


        return ResultUtils.success(interfaceInfoVO);
    }

    @PostMapping("/list/page/vo")
    public BaseResponse<Page<InterfaceInfoVO>> listInterfaceInfoVOByPage(@RequestBody InterfaceInfoQueryRequest interfaceInfoQueryRequest,
                                                                         HttpServletRequest request) {
        long current = interfaceInfoQueryRequest.getCurrent();
        long size = interfaceInfoQueryRequest.getPageSize();
        interfaceInfoQueryRequest.setSortField("createTime");
        // 倒序排序
        interfaceInfoQueryRequest.setSortOrder(CommonConstant.SORT_ORDER_DESC);
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<InterfaceInfo> interfaceInfoPage = interfaceInfoService.page(new Page<>(current, size),
                interfaceInfoService.getQueryWrapper(interfaceInfoQueryRequest));
        return ResultUtils.success(interfaceInfoService.getInterfaceInfoVOPage(interfaceInfoPage, request));
    }



   /* public BaseResponse<List<InterfaceInfo>> listInterfaceInfo(InterfaceInfoQueryRequest interfaceInfoQueryRequest,
                                                               HttpServletRequest request) {
        InterfaceInfo interfaceInfoQuery = new InterfaceInfo();
        if (interfaceInfoQueryRequest != null) {
            BeanUtils.copyProperties(interfaceInfoQueryRequest, interfaceInfoQuery);
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>(interfaceInfoQuery);
        List<InterfaceInfo> interfaceInfoList = interfaceInfoService.list(queryWrapper);
        return ResultUtils.success(interfaceInfoList);
    }*/

    /*
    @GetMapping("/list/page")
    public BaseResponse<Page<InterfaceInfo>> listInterfaceInfoByPage(InterfaceInfoQueryRequest interfaceInfoQueryRequest, HttpServletRequest request) {
        if (interfaceInfoQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        InterfaceInfo interfaceInfoQuery = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoQueryRequest, interfaceInfoQuery);

        long current = interfaceInfoQueryRequest.getCurrent();
        long size = interfaceInfoQueryRequest.getPageSize();

        String sortField = interfaceInfoQueryRequest.getSortField();
        String sortOrder = interfaceInfoQueryRequest.getSortOrder();
        String description = interfaceInfoQuery.getDescription();

        // description 需支持模糊搜索
        interfaceInfoQuery.setDescription(null);
        // 限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>(interfaceInfoQuery);
        queryWrapper.like(StringUtils.isNotBlank(description), "description", description);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        Page<InterfaceInfo> interfaceInfoPage = interfaceInfoService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(interfaceInfoPage);
    }*/

    /**
     * 根据 当前用户ID 分页获取列表（封装类）
     *
     * @param interfaceInfoQueryRequest 查询条件
     * @param request                   请求
     * @return 分页列表
     */
    @PostMapping("/my/list/page/vo")
    public BaseResponse<Page<InterfaceInfoVO>> listInterfaceInfoVOByUserIdPage(@RequestBody InterfaceInfoQueryRequest interfaceInfoQueryRequest,
                                                                               HttpServletRequest request) {
        long current = interfaceInfoQueryRequest.getCurrent();
        long size = interfaceInfoQueryRequest.getPageSize();
        interfaceInfoQueryRequest.setSortField("createTime");
        // 倒序排序
        interfaceInfoQueryRequest.setSortOrder(CommonConstant.SORT_ORDER_DESC);
        // 限制爬虫
        ThrowUtils.throwIf(size > 30, ErrorCode.PARAMS_ERROR);

//        User loginUser = userService.getLoginUser(request);
//        interfaceInfoQueryRequest.setUserId(loginUser.getId());

        Page<InterfaceInfo> interfaceInfoPage = interfaceInfoService.page(new Page<>(current, size),
                interfaceInfoService.getQueryWrapper(interfaceInfoQueryRequest));

        return ResultUtils.success(interfaceInfoService.getInterfaceInfoVOByUserIdPage(interfaceInfoPage, request));
    }

    // endregion

    // region 发布下线接口调用

    // TODO: 2024/2/4 关于代码重复使用问题的解决
    /**
     * 发布接口
     *
     * @param idRequest
     * @param request
     * @return
     */
    @PostMapping("/online")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<Boolean> onlineInterfaceInfo(@RequestBody IdRequest idRequest,
                                                     HttpServletRequest request) {

        //  如果 idRequest 为 null 或者 id < 0, 抛出异常
        if (idRequest == null || idRequest.getId() < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        long id = idRequest.getId();

        // 判断接口是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        //   判断接口是否可以调用
        //   在接口上线前做一下测试是没有什么毛病的
        com.dy.model.User user = new com.dy.model.User();

        // TODO: 2024/3/1 接口上线前做测试


//        String flag = dyApiClient.invokeInterface(user);
//
//        if (StrUtil.hasBlank(flag)) {
//            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "接口验证失败");
//        }

        InterfaceInfo interfaceInfo = new InterfaceInfo();
        interfaceInfo.setStatus(InterfaceInfoStatusEnum.FEMALE.getValue());

        //  这里忘记给接口 set id 了...
        interfaceInfo.setId(id);


        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);

    }


    /**
     * 下线接口
     *
     * @param idRequest
     * @param request
     * @return
     */
    @PostMapping("/offline")
//    @AuthCheck(mustRole = "admin")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> offlineInterfaceInfo(@RequestBody IdRequest idRequest,
                                                      HttpServletRequest request) {


        //  todo 上线, 下线接口需要完善
        if (idRequest == null || idRequest.getId() < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        long id = idRequest.getId();

        // 判断接口是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }


        InterfaceInfo interfaceInfo = new InterfaceInfo();
        interfaceInfo.setStatus(InterfaceInfoStatusEnum.MALE.getValue());
        //  这里忘记给接口 set id 了...
        interfaceInfo.setId(id);

        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);
    }

    /**
     * 测试用户调用接口
     *
     * @param interfaceInfoInvokeRequest
     * @param request
     * @return
     */
    @PostMapping("/invoke")
    public BaseResponse<Object> testInvokeInterface(@RequestBody InterfaceInfoInvokeRequest interfaceInfoInvokeRequest,
                                                      HttpServletRequest request) {


        //  1. 校验请求参数
        if (interfaceInfoInvokeRequest == null || interfaceInfoInvokeRequest.getId() < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        //  通过接口 id 获取要调用的接口
        long id = interfaceInfoInvokeRequest.getId();


        // 判断接口是否存在
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        //  判断接口是否可用
        if (interfaceInfo.getStatus() != InterfaceInfoStatusEnum.FEMALE.getValue()) {
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR, "接口已关闭");
        }

        //  获取接口请求地址
        String host = interfaceInfo.getHost();
        String url = interfaceInfo.getUrl();

//        String requestParams = interfaceInfo.getRequestParams();
        String requestParams = interfaceInfoInvokeRequest.getRequestParams();
        String method = interfaceInfo.getMethod();

        //  获取 SDK客户端并调用
        // TODO: 2024/3/1 防止异常
        DyApiClient dyApiClient = interfaceInfoService.getDyApiClient(request);

        String result = dyApiClient.invokeInterface(requestParams, url, method);


        return ResultUtils.success(result);
    }

    // endregion




}
