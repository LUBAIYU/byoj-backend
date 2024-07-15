package com.by.model.vo;

import com.by.model.entity.JudgeCase;
import com.by.model.entity.JudgeConfig;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lzh
 */
@Data
public class QuestionVO implements Serializable {
    /**
     * 题目ID
     */
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
     * 题目标签
     */
    private List<String> tagList;

    /**
     * 判题配置
     */
    private JudgeConfig judgeConfigVO;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
