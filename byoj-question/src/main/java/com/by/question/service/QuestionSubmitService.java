package com.by.question.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.by.common.utils.PageBean;
import com.by.model.dto.QuestionSubmitPageDTO;
import com.by.model.entity.QuestionSubmit;
import com.by.model.vo.QuestionSubmitVO;

/**
 * @author lzh
 */
public interface QuestionSubmitService extends IService<QuestionSubmit> {

    /**
     * 分页查询提交记录
     *
     * @param questionSubmitPageDTO 请求体
     * @return 记录列表
     */
    PageBean<QuestionSubmitVO> pageQuestionSubmitVos(QuestionSubmitPageDTO questionSubmitPageDTO);
}
