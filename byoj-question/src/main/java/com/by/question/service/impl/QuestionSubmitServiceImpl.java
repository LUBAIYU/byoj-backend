package com.by.question.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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

    @Override
    public PageBean<QuestionSubmitVO> pageQuestionSubmitVos(QuestionSubmitPageDTO questionSubmitPageDTO) {
        // 获取参数
        String language = questionSubmitPageDTO.getLanguage();
        Long questionId = questionSubmitPageDTO.getQuestionId();
        Integer current = questionSubmitPageDTO.getCurrent();
        Integer pageSize = questionSubmitPageDTO.getPageSize();
        // 添加分页条件
        Page<QuestionSubmit> page = new Page<>(current, pageSize);
        // 添加查询条件
        LambdaQueryWrapper<QuestionSubmit> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(language), QuestionSubmit::getLanguage, language);
        wrapper.eq(questionId != null, QuestionSubmit::getQuestionId, questionId);
        // 查询
        this.page(page, wrapper);
        // 封装返回值
        long total = page.getTotal();
        List<QuestionSubmit> records = page.getRecords();
        List<QuestionSubmitVO> questionSubmitVOList = new ArrayList<>();
        // 如果为空直接返回
        if (records.isEmpty()) {
            return PageBean.of(total, questionSubmitVOList);
        }
        questionSubmitVOList = records.stream().map(questionSubmit -> {
            QuestionSubmitVO questionSubmitVO = new QuestionSubmitVO();
            BeanUtil.copyProperties(questionSubmit, questionSubmitVO);
            return questionSubmitVO;
        }).collect(Collectors.toList());
        return PageBean.of(total, questionSubmitVOList);
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

        // 异步发送题目提交ID到消息队列
        Long questionSubmitId = questionSubmit.getId();
        rabbitTemplate.convertAndSend(QuestionConstants.EXCHANGE_NAME, QuestionConstants.ROUTING_KEY, String.valueOf(questionSubmitId));

        return questionSubmitId;
    }
}




