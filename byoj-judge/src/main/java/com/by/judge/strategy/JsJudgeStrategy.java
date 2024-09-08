package com.by.judge.strategy;

import com.by.model.entity.JudgeContext;
import com.by.model.entity.JudgeInfo;

/**
 * JavaScript判题策略
 *
 * @author lzh
 */
public class JsJudgeStrategy implements JudgeStrategy {

    @Override
    public JudgeInfo doJudgeByStrategy(JudgeContext judgeContext) {
        // 暂时使用Java判题策略
        JudgeStrategy judgeStrategy = new JavaJudgeStrategy();
        return judgeStrategy.doJudgeByStrategy(judgeContext);
    }
}
