package com.dy.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dy.common.model.entity.InterfaceInfo;


/**
* @author dy
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2023-12-20 15:21:55
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {
    void validInterfaceInfo(InterfaceInfo InterfaceInfo, boolean add);
}
