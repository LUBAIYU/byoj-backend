package com.by.model.entity;

import lombok.Data;

import java.util.List;

/**
 * 代码沙箱请求体
 *
 * @author lzh
 */
@Data
public class ExecuteCodeRequest {
    /**
     * 代码
     */
    private String code;
    /**
     * 语言
     */
    private String language;
    /**
     * 输入
     */
    private List<String> inputList;
}
