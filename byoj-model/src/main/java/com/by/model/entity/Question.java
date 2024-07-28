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

/**
 * 题目表
 *
 * @author lzh
 */
@TableName(value = "question")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Question implements Serializable {
    /**
     * 题目ID
     */
    @TableId(type = IdType.ASSIGN_ID)
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
    private String tags;

    /**
     * 判题用例
     */
    private String judgeCase;

    /**
     * 判题配置
     */
    private String judgeConfig;

    /**
     * 总提交数
     */
    private Integer submitNum;

    /**
     * 总通过数
     */
    private Integer acceptNum;

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