package com.by.question.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.by.common.utils.PageBean;
import com.by.model.dto.QuestionSubmitPageDTO;
import com.by.model.entity.QuestionSubmit;
import com.by.model.vo.QuestionSubmitVO;
import com.by.question.service.QuestionSubmitService;
import com.by.question.mapper.QuestionSubmitMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lzh
 */
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
        implements QuestionSubmitService {

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
}




