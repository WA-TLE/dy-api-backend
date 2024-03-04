package com.dy.project.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dy.client.DyApiClient;
import com.dy.dycommon.model.dto.interfaceinfo.InterfaceInfoQueryRequest;
import com.dy.dycommon.model.entity.InterfaceInfo;
import com.dy.dycommon.model.vo.InterfaceInfoVO;
import org.apache.coyote.Request;

import javax.servlet.http.HttpServletRequest;


/**
* @author dy
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2023-12-20 15:21:55
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {
    void validInterfaceInfo(InterfaceInfo InterfaceInfo, boolean add);

    DyApiClient getDyApiClient(HttpServletRequest request);

    InterfaceInfoVO getInterfaceInfoVO(InterfaceInfo interfaceInfo, HttpServletRequest request);

    /**
     * 获取查询条件
     *
     * @param interfaceInfoQueryRequest
     * @return
     */
    QueryWrapper<InterfaceInfo> getQueryWrapper(InterfaceInfoQueryRequest interfaceInfoQueryRequest);

    /**
     * 分页获取接口信息封装
     *
     * @param interfaceInfoPage
     * @param request
     * @return
     */
    Page<InterfaceInfoVO>  getInterfaceInfoVOByUserIdPage(Page<InterfaceInfo> interfaceInfoPage, HttpServletRequest request);

    Page<InterfaceInfoVO> getInterfaceInfoVOPage(Page<InterfaceInfo> interfaceInfoPage, HttpServletRequest request);
}
