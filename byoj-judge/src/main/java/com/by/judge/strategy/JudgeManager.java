package com.by.judge.strategy;

import com.by.model.entity.JudgeContext;
import com.by.model.entity.JudgeInfo;
import org.springframework.stereotype.Component;

/**
 * 策略管理者
 *
 * @author lzh
 */
@Component
public class JudgeManager {

    private JudgeStrategy judgeStrategy;

    public void selectStrategy(String language) {
        switch (language) {
            case "js":
                judgeStrategy = new JsJudgeStrategy();
                break;
            case "java":
            default:
                judgeStrategy = new JavaJudgeStrategy();
        }
    }

    public JudgeInfo doJudgeByStrategy(JudgeContext judgeContext) {
        return judgeStrategy.doJudgeByStrategy(judgeContext);
    }
}
