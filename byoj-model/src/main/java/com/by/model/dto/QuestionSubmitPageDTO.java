package com.by.model.dto;

import com.by.common.utils.PageRequest;
import com.by.model.entity.QuestionSubmit;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author lzh
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionSubmitPageDTO extends PageRequest<QuestionSubmit> implements Serializable {
    /**
     * 语言
     */
    private String language;
    /**
     * 题目名称
     */
    private String questionName;
}
