package com.by.model.entity;

import lombok.Data;

import java.util.List;

/**
 * 代码沙箱响应体
 *
 * @author lzh
 */
@Data
public class ExecuteCodeResponse {
    /**
     * 执行状态
     */
    private Integer status;
    /**
     * 执行信息
     */
    private String message;
    /**
     * 输出
     */
    private List<String> outputList;
}
