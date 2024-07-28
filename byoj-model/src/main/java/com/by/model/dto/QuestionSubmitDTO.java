package com.by.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 题目提交请求参数
 *
 * @author lzh
 */
@Data
public class QuestionSubmitDTO implements Serializable {
    /**
     * 代码
     */
    @NotNull(message = "代码不能为null")
    @NotBlank(message = "代码不能为空")
    private String code;
    /**
     * 编程语言
     */
    @NotNull(message = "编程语言不能为null")
    @NotBlank(message = "编程语言不能为空")
    private String language;
    /**
     * 题目ID
     */
    @NotNull(message = "题目ID不能为null")
    private Long questionId;
    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为null")
    private Long userId;
}
