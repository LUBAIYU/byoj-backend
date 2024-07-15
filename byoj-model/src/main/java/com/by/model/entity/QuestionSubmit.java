package com.by.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 题目提交信息表
 *
 * @author lzh
 */
@TableName(value = "question_submit")
@Data
public class QuestionSubmit implements Serializable {
    /**
     * 题目提交信息ID
     */
    @TableId(type = IdType.AUTO)
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
     * 判题状态
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
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除（0-未删除，1-删除）
     */
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}