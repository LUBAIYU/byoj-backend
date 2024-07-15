package com.by.question.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.by.model.dto.QuestionAddDTO;
import com.by.model.entity.Question;

/**
 * @author lzh
 */
public interface QuestionService extends IService<Question> {

    /**
     * 添加题目
     *
     * @param questionAddDTO 请求参数
     * @return 题目ID
     */
    long addQuestion(QuestionAddDTO questionAddDTO);
}
