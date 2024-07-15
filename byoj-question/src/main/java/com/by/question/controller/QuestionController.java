package com.by.question.controller;

import com.by.common.utils.Result;
import com.by.model.dto.QuestionAddDTO;
import com.by.question.service.QuestionService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author lzh
 */
@RestController
@RequestMapping("/")
@Validated
public class QuestionController {

    @Resource
    private QuestionService questionService;

    @PostMapping("/create")
    public Result<Long> createQuestion(@Valid @RequestBody QuestionAddDTO questionAddDTO) {
        long id = questionService.addQuestion(questionAddDTO);
        return Result.success(id);
    }
}
