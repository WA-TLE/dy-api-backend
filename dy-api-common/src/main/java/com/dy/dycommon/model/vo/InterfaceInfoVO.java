package com.dy.dycommon.model.vo;

import com.dy.dycommon.model.entity.InterfaceInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 接口调用分析视图
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class InterfaceInfoVO extends InterfaceInfo implements Serializable {
    /**
     * id
     */
    private Long id;

    private Integer totalNum;

    private static final long serialVersionUID = 1L;
}