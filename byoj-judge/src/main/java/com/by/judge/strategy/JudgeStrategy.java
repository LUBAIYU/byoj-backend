package com.by.judge.strategy;

import com.by.model.entity.JudgeContext;
import com.by.model.entity.JudgeInfo;

/**
 * 判题策略
 *
 * @author lzh
 */
public interface JudgeStrategy {

    /**
     * 使用策略进行判题
     *
     * @param judgeContext 判题参数
     * @return 判题结果
     */
    JudgeInfo doJudgeByStrategy(JudgeContext judgeContext);
}
