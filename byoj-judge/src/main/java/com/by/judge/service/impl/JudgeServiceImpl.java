package com.by.judge.service.impl;

import com.by.common.constant.QuestionConstants;
import com.by.common.enums.ErrorCode;
import com.by.common.enums.QuestionSubmitStatusEnum;
import com.by.common.exception.ServerException;
import com.by.judge.codesandbox.CodeSandBoxFactory;
import com.by.judge.feign.QuestionFeignClient;
import com.by.judge.codesandbox.CodeSandBox;
import com.by.judge.service.JudgeService;
import com.by.judge.strategy.JudgeManager;
import com.by.model.entity.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lzh
 */
public class JudgeServiceImpl implements JudgeService {

    @Resource
    private QuestionFeignClient questionFeignClient;

    @Resource
    private JudgeManager judgeManager;

    @Value("${codesandbox.type}")
    private String type;

    @Override
    public void doJudge(long questionSubmitId) {
        // 查看题目提交信息
        if (questionSubmitId <= 0) {
            throw new ServerException(ErrorCode.PARAMS_ERROR, QuestionConstants.QUESTION_SUBMIT_ID_NOT_EXIST);
        }
        QuestionSubmit questionSubmit = questionFeignClient.getQuestionSubmitById(questionSubmitId);
        if (questionSubmit == null) {
            throw new ServerException(ErrorCode.PARAMS_ERROR, QuestionConstants.QUESTION_SUBMIT_NOT_EXIST);
        }

        // 判断题目是否为等待状态
        Integer status = questionSubmit.getStatus();
        QuestionSubmitStatusEnum statusEnum = QuestionSubmitStatusEnum.getEnumByValue(status);
        if (!QuestionSubmitStatusEnum.WAITING.getValue().equals(statusEnum.getValue())) {
            throw new ServerException(ErrorCode.PARAMS_ERROR, QuestionConstants.QUESTION_SUBMIT_ERROR);
        }

        // 修改题目状态为判题中
        questionSubmit.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        questionFeignClient.updateQuestionSubmit(questionSubmit);

        //获取题目的示例输入
        Question question = questionFeignClient.getQuestionById(questionSubmit.getQuestionId());
        String judgeCase = question.getJudgeCase();
        Gson gson = new Gson();
        List<JudgeCase> judgeCaseList = gson.fromJson(judgeCase, new TypeToken<List<JudgeCase>>() {
        });
        List<String> exampleInputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        String code = questionSubmit.getCode();
        String language = questionSubmit.getLanguage();

        // 调用代码沙箱获取执行结果
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(exampleInputList)
                .build();
        CodeSandBox codeSandBox = CodeSandBoxFactory.newInstance(type);
        ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(executeCodeRequest);

        // 执行判题
        List<String> exampleOutputList = judgeCaseList.stream().map(JudgeCase::getOutput).collect(Collectors.toList());
        String judgeConfigStr = question.getJudgeConfig();
        JudgeConfig judgeConfig = gson.fromJson(judgeConfigStr, new TypeToken<JudgeConfig>() {
        });
        JudgeContext judgeContext = JudgeContext.builder()
                .exampleInputList(exampleOutputList)
                .exampleOutputList(exampleOutputList)
                .realOutputList(executeCodeResponse.getOutputList())
                .judgeConfig(judgeConfig)
                .memoryUsage(executeCodeResponse.getMemoryUsage())
                .timeUsage(executeCodeResponse.getTimeUsage())
                .build();

        // 通过编程语言选择不同的判题策略
        judgeManager.selectStrategy(language);
        JudgeInfo judgeInfo = judgeManager.doJudgeByStrategy(judgeContext);

        // 修改题目提交信息
        String judgeInfoStr = gson.toJson(judgeInfo);
        questionSubmit.setJudgeInfo(judgeInfoStr);
        questionSubmit.setStatus(judgeInfo.getStatus());
        Boolean res = questionFeignClient.updateQuestionSubmit(questionSubmit);
        if (!res) {
            throw new ServerException(ErrorCode.SYSTEM_ERROR, QuestionConstants.QUESTION_JUDGE_ERROR);
        }
    }
}
