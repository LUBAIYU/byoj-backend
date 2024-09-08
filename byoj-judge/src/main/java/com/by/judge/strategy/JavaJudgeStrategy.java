package com.by.judge.strategy;

import com.by.common.constant.QuestionConstants;
import com.by.common.enums.QuestionSubmitStatusEnum;
import com.by.model.entity.JudgeConfig;
import com.by.model.entity.JudgeContext;
import com.by.model.entity.JudgeInfo;

import java.util.List;

/**
 * Java语言判题策略
 *
 * @author lzh
 */
public class JavaJudgeStrategy implements JudgeStrategy {
    @Override
    public JudgeInfo doJudgeByStrategy(JudgeContext judgeContext) {
        // 获取请求参数
        List<String> exampleInputList = judgeContext.getExampleInputList();
        List<String> exampleOutputList = judgeContext.getExampleOutputList();
        List<String> realOutputList = judgeContext.getRealOutputList();
        JudgeConfig judgeConfig = judgeContext.getJudgeConfig();
        Long memoryUsage = judgeContext.getMemoryUsage();
        Long timeUsage = judgeContext.getTimeUsage();
        if (memoryUsage == null) {
            memoryUsage = 0L;
        }
        if (timeUsage == null) {
            timeUsage = 0L;
        }

        // 创建判题信息
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        judgeInfo.setMessage(QuestionSubmitStatusEnum.RUNNING.getText());
        judgeInfo.setMemoryUsage(memoryUsage);
        judgeInfo.setTimeUsage(timeUsage);

        // 比对示例输入个数和真实输入个数是否一致
        if (exampleInputList.size() != realOutputList.size()) {
            judgeInfo.setStatus(QuestionSubmitStatusEnum.FAILED.getValue());
            judgeInfo.setMessage(QuestionConstants.QUESTION_JUDGE_NOT_PASS);
            return judgeInfo;
        }

        // 比对真实输出和示例输出是否一致
        for (int i = 0; i < exampleOutputList.size(); i++) {
            String exampleOutput = exampleOutputList.get(i);
            String realOutput = realOutputList.get(i);
            if (!exampleOutput.equals(realOutput)) {
                judgeInfo.setStatus(QuestionSubmitStatusEnum.FAILED.getValue());
                judgeInfo.setMessage(QuestionConstants.QUESTION_JUDGE_NOT_PASS);
                return judgeInfo;
            }
        }

        // 比对时间和内存是否超出限制
        if (timeUsage > judgeConfig.getTimeLimit() || memoryUsage > judgeConfig.getMemoryLimit()) {
            judgeInfo.setStatus(QuestionSubmitStatusEnum.FAILED.getValue());
            judgeInfo.setMessage(QuestionConstants.QUESTION_JUDGE_NOT_PASS);
            return judgeInfo;
        }

        // 返回判题信息
        judgeInfo.setStatus(QuestionSubmitStatusEnum.ACCEPTED.getValue());
        judgeInfo.setMessage(QuestionSubmitStatusEnum.ACCEPTED.getText());
        return judgeInfo;
    }
}
