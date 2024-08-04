package com.by.judge.strategy;

import com.by.model.entity.JudgeContext;
import com.by.model.entity.JudgeInfo;

/**
 * 默认判题策略（Java语言）
 *
 * @author lzh
 */
public class DefaultJudgeStrategy implements JudgeStrategy {
    @Override
    public JudgeInfo doJudgeByStrategy(JudgeContext judgeContext) {
        return null;
    }
}
