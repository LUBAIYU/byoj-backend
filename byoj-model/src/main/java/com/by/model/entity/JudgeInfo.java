package com.by.model.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 判题信息
 *
 * @author lzh
 */
@Data
public class JudgeInfo implements Serializable {

    /**
     * 判题状态
     */
    private Integer status;

    /**
     * 判题信息
     */
    private String message;

    /**
     * 内存占用
     */
    private Long memoryUsage;

    /**
     * 时间占用
     */
    private Long timeUsage;

    private static final long serialVersionUID = 1L;
}
