package com.dy.project.model.dto.interfaceinfo;

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
     * 用户请求请求参数
     */
    private String userRequestParams;



    private static final long serialVersionUID = 1L;
}