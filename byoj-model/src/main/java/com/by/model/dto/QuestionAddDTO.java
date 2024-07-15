package com.by.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author lzh
 */
@Data
public class QuestionAddDTO implements Serializable {
    /**
     * 标题
     */
    @NotNull(message = "标题不能为null")
    @NotBlank(message = "标题不能为空")
    private String title;

    /**
     * 题目内容
     */
    @NotNull(message = "题目内容不能为null")
    @NotBlank(message = "题目内容不能为空")
    private String content;

    /**
     * 题目答案
     */
    @NotNull(message = "题目答案不能为null")
    @NotBlank(message = "题目答案不能为空")
    private String answer;

    /**
     * 题目标签列表（JSON数组）
     */
    @NotNull(message = "题目标签不能为null")
    private List<String> tags;

    /**
     * 判题用例
     */
    private List<JudgeCaseDTO> judgeCaseDTOList;

    /**
     * 判题配置
     */
    private JudgeConfigDTO judgeConfigDTO;
}
