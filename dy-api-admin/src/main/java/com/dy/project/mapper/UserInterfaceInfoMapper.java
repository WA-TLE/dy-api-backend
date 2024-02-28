package com.dy.project.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dy.dycommon.model.entity.UserInterfaceInfo;

import java.util.List;

/**
* @author 微光
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Mapper
* @createDate 2024-02-02 22:09:07
* @Entity generator.domain.UserInterfaceInfo
*/
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {
    List<UserInterfaceInfo> listTopInvokeInterfaceInfo(Integer limit);
}




