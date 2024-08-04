package com.by.model.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 判题策略参数（上下文）
 *
 * @author lzh
 */
@Data
@Builder
public class JudgeContext {
    /**
     * 示例输入
     */
    private List<String> exampleInputList;
    /**
     * 示例输出
     */
    private List<String> exampleOutputList;
    /**
     * 真实输出
     */
    private List<String> realOutputList;
    /**
     * 判题限制
     */
    private JudgeConfig judgeConfig;
    /**
     * 内存占用
     */
    private Long memoryUsage;
    /**
     * 时间占用
     */
    private Long timeUsage;
}
