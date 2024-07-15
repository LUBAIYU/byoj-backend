package com.by.question.controller;

import com.by.common.utils.PageBean;
import com.by.common.utils.Result;
import com.by.model.dto.QuestionAddDTO;
import com.by.model.dto.QuestionPageDTO;
import com.by.model.dto.QuestionUpdateDTO;
import com.by.model.vo.QuestionVO;
import com.by.question.service.QuestionService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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

    @DeleteMapping("/{id}")
    public Result<Boolean> delQuestion(@NotNull @PathVariable Long id) {
        questionService.delQuestionById(id);
        return Result.success(true);
    }

    @PutMapping("/update")
    public Result<Boolean> updateQuestion(@Valid @RequestBody QuestionUpdateDTO questionUpdateDTO) {
        questionService.updateQuestionById(questionUpdateDTO);
        return Result.success(true);
    }

    @GetMapping("/{id}")
    public Result<QuestionVO> getQuestionVO(@NotNull @PathVariable Long id) {
        QuestionVO questionVO = questionService.getQuestionVoById(id);
        return Result.success(questionVO);
    }

    @PostMapping("/page")
    public Result<PageBean<QuestionVO>> listQuestionVosByPage(@Valid @RequestBody QuestionPageDTO questionPageDTO) {
        PageBean<QuestionVO> pageBean = questionService.pageQuestionVos(questionPageDTO);
        return Result.success(pageBean);
    }
}
