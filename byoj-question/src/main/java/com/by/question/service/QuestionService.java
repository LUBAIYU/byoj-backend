package com.by.question.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.by.common.utils.PageBean;
import com.by.model.dto.QuestionAddDTO;
import com.by.model.dto.QuestionPageDTO;
import com.by.model.dto.QuestionUpdateDTO;
import com.by.model.entity.Question;
import com.by.model.vo.QuestionVO;

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

    /**
     * 根据ID删除题目
     *
     * @param id 题目ID
     */
    void delQuestionById(Long id);

    /**
     * 根据ID更新题目信息
     *
     * @param questionUpdateDTO 请求体
     */
    void updateQuestionById(QuestionUpdateDTO questionUpdateDTO);

    /**
     * 根据ID获取题目信息
     *
     * @param id 题目ID
     * @return 题目信息
     */
    QuestionVO getQuestionVoById(Long id);

    /**
     * 分页获取题目信息（脱敏）
     *
     * @param questionPageDTO 分页请求体
     * @return 题目列表
     */
    PageBean<QuestionVO> pageQuestionVos(QuestionPageDTO questionPageDTO);

    /**
     * 分页获取题目信息（完整）
     *
     * @param questionPageDTO 分页请求体
     * @return 题目列表
     */
    PageBean<Question> pageQuestions(QuestionPageDTO questionPageDTO);
}
