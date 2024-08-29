package com.by.question.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.by.common.constant.QuestionConstants;
import com.by.common.enums.ErrorCode;
import com.by.common.enums.LanguageEnum;
import com.by.common.enums.QuestionSubmitStatusEnum;
import com.by.common.exception.ServerException;
import com.by.common.utils.PageBean;
import com.by.model.dto.QuestionSubmitDTO;
import com.by.model.dto.QuestionSubmitPageDTO;
import com.by.model.entity.Question;
import com.by.model.entity.QuestionSubmit;
import com.by.model.entity.User;
import com.by.model.vo.QuestionSubmitVO;
import com.by.question.feign.UserFeignClient;
import com.by.question.service.QuestionService;
import com.by.question.service.QuestionSubmitService;
import com.by.question.mapper.QuestionSubmitMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lzh
 */
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
        implements QuestionSubmitService {

    @Resource
    private QuestionService questionService;

    @Resource
    private UserFeignClient userFeignClient;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private QuestionSubmitMapper questionSubmitMapper;

    @Override
    public PageBean<QuestionSubmitVO> pageQuestionSubmitVos(QuestionSubmitPageDTO questionSubmitPageDTO) {
        IPage<QuestionSubmit> pagination = new Page<>(questionSubmitPageDTO.getCurrent(), questionSubmitPageDTO.getPageSize());
        IPage<QuestionSubmitVO> pageResult = questionSubmitMapper.pageQuestionSubmitVos(pagination, questionSubmitPageDTO);
        return PageBean.of(pageResult.getTotal(), pageResult.getRecords());
    }

    @Override
    public Long doQuestionSubmit(QuestionSubmitDTO questionSubmitDTO) {
        // 获取请求参数
        String code = questionSubmitDTO.getCode();
        String language = questionSubmitDTO.getLanguage();
        Long questionId = questionSubmitDTO.getQuestionId();
        Long userId = questionSubmitDTO.getUserId();
        // 判断编程语言是否符合要求
        LanguageEnum languageEnum = LanguageEnum.getEnumByValue(language);
        if (languageEnum == null) {
            throw new ServerException(ErrorCode.PARAMS_ERROR, QuestionConstants.LANGUAGE_NOT_SUPPORT);
        }
        // 判断题目是否存在
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new ServerException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 判断用户是否存在
        User user = userFeignClient.getById(userId);
        if (user == null) {
            throw new ServerException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 插入数据
        QuestionSubmit questionSubmit = QuestionSubmit
                .builder()
                .code(code)
                .language(language)
                .questionId(questionId)
                .userId(userId)
                .status(QuestionSubmitStatusEnum.WAITING.getValue())
                .build();
        boolean save = this.save(questionSubmit);
        if (!save) {
            throw new ServerException(ErrorCode.PARAMS_ERROR, QuestionConstants.QUESTION_SUBMIT_ADD_ERROR);
        }

        // 更新题目的提交数
        question.setSubmitNum(question.getSubmitNum() + 1);
        questionService.updateById(question);

        // 异步发送题目提交ID到消息队列
        Long questionSubmitId = questionSubmit.getId();
        rabbitTemplate.convertAndSend(QuestionConstants.EXCHANGE_NAME, QuestionConstants.ROUTING_KEY, String.valueOf(questionSubmitId));

        return questionSubmitId;
    }
}




