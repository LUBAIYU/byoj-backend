package com.by.question.controller;

import cn.hutool.core.bean.BeanUtil;
import com.by.model.entity.Question;
import com.by.model.entity.QuestionSubmit;
import com.by.model.vo.QuestionSubmitVO;
import com.by.question.service.QuestionService;
import com.by.question.service.QuestionSubmitService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * @author lzh
 */
@RestController
@RequestMapping("/inner")
@Validated
public class InnerQuestionController {

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @GetMapping("/submit/get")
    public QuestionSubmit getQuestionSubmitById(@RequestParam("id") @NotNull Long id) {
        QuestionSubmit questionSubmit = questionSubmitService.getById(id);
        return BeanUtil.toBean(questionSubmit, QuestionSubmit.class);
    }

    @PostMapping("/submit/update")
    public Boolean updateQuestionSubmit(@RequestBody QuestionSubmit questionSubmit) {
        return questionSubmitService.updateById(questionSubmit);
    }

    @GetMapping("/get")
    public Question getQuestionById(@RequestParam("id") @NotNull Long id) {
        return questionService.getById(id);
    }
}
