package com.by.model.dto;

import com.by.model.entity.JudgeCase;
import com.by.model.entity.JudgeConfig;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author lzh
 */
@Data
public class QuestionUpdateDTO implements Serializable {
    /**
     * 题目ID
     */
    @NotNull(message = "id不能为null")
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 题目内容
     */
    private String content;

    /**
     * 题目答案
     */
    private String answer;

    /**
     * 题目标签
     */
    private List<String> tagList;

    /**
     * 判题用例
     */
    private List<JudgeCase> judgeCaseDTOList;

    /**
     * 判题配置
     */
    private JudgeConfig judgeConfigDTO;
}
