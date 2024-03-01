package com.dy.dycommon.model.dto.interfaceinfo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * 创建请求
 *
 * @TableName product
 */
@Data
public class InterfaceInfoInvokeRequest implements Serializable {


    /**
     * 主键
     */
    private Long id;

    /**
     * 请求类型
     */
    @TableField(value = "method")
    private String method;


    /**
     * 主机名
     */
    @TableField(value = "host")
    private String host;


    /**
     * 用户请求请求参数
     */
    private String userRequestParams;





    private static final long serialVersionUID = 1L;
}