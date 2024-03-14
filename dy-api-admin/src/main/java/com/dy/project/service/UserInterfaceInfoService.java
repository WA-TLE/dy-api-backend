package com.dy.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dy.dycommon.model.entity.UserInterfaceInfo;

import java.util.List;


/**
* @author 微光
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service
* @createDate 2024-02-02 22:09:07
*/
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {
    /**
     * 判断接口信息是否有效
     *
     * @param userInterfaceInfo
     * @param add
     */
    void validInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add);

    boolean invokeCount(Long interfaceInfoId, Long userId);

    List<UserInterfaceInfo> listTopInvokeInterfaceInfo(Integer limit);
}
