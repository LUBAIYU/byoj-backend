package com.by.judge.feign;

import com.by.model.entity.Question;
import com.by.model.entity.QuestionSubmit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;

/**
 * @author lzh
 */
@FeignClient(name = "byoj-question", path = "/oj/question/inner")
public interface QuestionFeignClient {

    /**
     * 获取题目提交信息
     *
     * @param id 题目提交ID
     * @return 题目提交信息
     */
    @GetMapping("/submit/get")
    QuestionSubmit getQuestionSubmitById(@RequestParam("id") @NotNull Long id);

    /**
     * 修改题目提交信息
     *
     * @param questionSubmit 题目提交信息
     * @return 是否修改成功
     */
    @PostMapping("/submit/update")
    Boolean updateQuestionSubmit(@RequestBody QuestionSubmit questionSubmit);

    /**
     * 根据ID获取题目信息
     *
     * @param id 题目ID
     * @return 题目信息
     */
    @GetMapping("/get")
    Question getQuestionById(@RequestParam("id") @NotNull Long id);

    /**
     * 根据ID更新题目信息
     *
     * @param question 题目信息
     * @return 是否更新成功
     */
    @PostMapping("/update")
    Boolean updateQuestionById(@RequestBody Question question);
}
