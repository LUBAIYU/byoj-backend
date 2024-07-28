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
     * 正常信息
     */
    private String message;

    /**
     * 错误信息
     */
    private String errorMessage;

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
