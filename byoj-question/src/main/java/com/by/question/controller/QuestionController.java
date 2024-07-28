package com.by.question.controller;

import com.by.common.annotation.PreAuthorize;
import com.by.common.enums.ErrorCode;
import com.by.common.enums.RoleEnum;
import com.by.common.exception.ServerException;
import com.by.common.utils.PageBean;
import com.by.common.utils.Result;
import com.by.model.dto.QuestionAddDTO;
import com.by.model.dto.QuestionPageDTO;
import com.by.model.dto.QuestionSubmitPageDTO;
import com.by.model.dto.QuestionUpdateDTO;
import com.by.model.entity.Question;
import com.by.model.vo.QuestionSubmitVO;
import com.by.model.vo.QuestionVO;
import com.by.question.service.QuestionService;
import com.by.question.service.QuestionSubmitService;
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

    @Resource
    private QuestionSubmitService questionSubmitService;

    @PreAuthorize(role = RoleEnum.ADMIN)
    @PostMapping("/create")
    public Result<Long> createQuestion(@Valid @RequestBody QuestionAddDTO questionAddDTO) {
        long id = questionService.addQuestion(questionAddDTO);
        return Result.success(id);
    }

    @PreAuthorize(role = RoleEnum.ADMIN)
    @DeleteMapping("/{id}")
    public Result<Boolean> delQuestion(@NotNull @PathVariable Long id) {
        questionService.delQuestionById(id);
        return Result.success(true);
    }

    @PreAuthorize(role = RoleEnum.ADMIN)
    @PutMapping("/update")
    public Result<Boolean> updateQuestion(@Valid @RequestBody QuestionUpdateDTO questionUpdateDTO) {
        questionService.updateQuestionById(questionUpdateDTO);
        return Result.success(true);
    }

    @PreAuthorize(role = RoleEnum.ADMIN)
    @GetMapping("/{id}")
    public Result<Question> getQuestion(@NotNull @PathVariable Long id) {
        Question question = questionService.getById(id);
        if (question == null) {
            throw new ServerException(ErrorCode.NOT_FOUND_ERROR);
        }
        return Result.success(question);
    }

    @PreAuthorize(role = RoleEnum.USER)
    @GetMapping("/vo/{id}")
    public Result<QuestionVO> getQuestionVO(@NotNull @PathVariable Long id) {
        QuestionVO questionVO = questionService.getQuestionVoById(id);
        return Result.success(questionVO);
    }

    @PreAuthorize(role = RoleEnum.ADMIN)
    @PostMapping("/page")
    public Result<PageBean<Question>> listQuestionsByPage(@Valid @RequestBody QuestionPageDTO questionPageDTO) {
        PageBean<Question> pageBean = questionService.pageQuestions(questionPageDTO);
        return Result.success(pageBean);
    }

    @PreAuthorize(role = RoleEnum.USER)
    @PostMapping("/vo/page")
    public Result<PageBean<QuestionVO>> listQuestionVosByPage(@Valid @RequestBody QuestionPageDTO questionPageDTO) {
        PageBean<QuestionVO> pageBean = questionService.pageQuestionVos(questionPageDTO);
        return Result.success(pageBean);
    }

    @PreAuthorize(role = RoleEnum.USER)
    @PostMapping("/submit/page")
    public Result<PageBean<QuestionSubmitVO>> listQuestionSubmitVosByPage(@Valid @RequestBody QuestionSubmitPageDTO questionSubmitPageDTO) {
        PageBean<QuestionSubmitVO> pageBean = questionSubmitService.pageQuestionSubmitVos(questionSubmitPageDTO);
        return Result.success(pageBean);
    }
}
