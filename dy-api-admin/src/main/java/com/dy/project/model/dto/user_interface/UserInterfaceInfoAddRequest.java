package com.dy.project.model.dto.user_interface;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * 创建请求
 *
 * @TableName product
 */
@Data
public class UserInterfaceInfoAddRequest implements Serializable {


    /**
     * 用户 id
     */
    @TableField(value = "userId")
    private Long userId;

    /**
     * 接口 id
     */
    @TableField(value = "interfaceInfoId")
    private Long interfaceInfoId;

    /**
     * 总调用次数
     */
    @TableField(value = "totalNum")
    private Integer totalNum;

    /**
     * 剩余调用次数
     */
    @TableField(value = "leftNum")
    private Integer leftNum;



    private static final long serialVersionUID = 1L;
}