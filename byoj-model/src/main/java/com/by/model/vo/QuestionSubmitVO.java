package com.by.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lzh
 */
@Data
public class QuestionSubmitVO implements Serializable {
    /**
     * 题目提交信息ID
     */
    private Long id;

    /**
     * 语言
     */
    private String language;


    /**
     * 判题信息（包括执行信息、占用内存、消耗时间）
     */
    private String judgeInfo;

    /**
     * 判题状态（0-待判题，1-判题中，2-成功，3-失败）
     */
    private Integer status;

    /**
     * 题目
     */
    private String questionName;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
