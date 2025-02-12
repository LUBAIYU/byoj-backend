package com.by.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 题目提交信息表
 *
 * @author lzh
 */
@TableName(value = "question_submit")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionSubmit implements Serializable {
    /**
     * 题目提交信息ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 语言
     */
    private String language;

    /**
     * 代码
     */
    private String code;

    /**
     * 判题信息（包括执行信息、占用内存、消耗时间）
     */
    private String judgeInfo;

    /**
     * 判题状态（0-待判题，1-判题中，2-成功，3-失败）
     */
    private Integer status;

    /**
     * 题目ID
     */
    private Long questionId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否删除（0-未删除，1-删除）
     */
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}