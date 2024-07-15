package com.by.model.dto;

import com.by.common.utils.PageRequest;
import com.by.model.entity.Question;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author lzh
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionPageDTO extends PageRequest<Question> implements Serializable {
    /**
     * 题目ID
     */
    private Long id;
    /**
     * 标题
     */
    private String title;
    /**
     * 题目标签
     */
    private String tags;
}
