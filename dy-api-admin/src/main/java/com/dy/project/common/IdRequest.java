package com.dy.project.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 接口 id
 *
 * @author dy
 */
@Data
public class IdRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}