package com.by.question.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.by.common.enums.ErrorCode;
import com.by.common.exception.ServerException;
import com.by.model.dto.JudgeCaseDTO;
import com.by.model.dto.JudgeConfigDTO;
import com.by.model.dto.QuestionAddDTO;
import com.by.model.entity.Question;
import com.by.question.service.QuestionService;
import com.by.question.mapper.QuestionMapper;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lzh
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
        implements QuestionService {

    @Override
    public long addQuestion(QuestionAddDTO questionAddDTO) {
        // 获取参数
        String title = questionAddDTO.getTitle();
        String content = questionAddDTO.getContent();
        String answer = questionAddDTO.getAnswer();
        List<String> tags = questionAddDTO.getTags();
        List<JudgeCaseDTO> judgeCaseDTOList = questionAddDTO.getJudgeCaseDTOList();
        JudgeConfigDTO judgeConfigDTO = questionAddDTO.getJudgeConfigDTO();
        // 将标签列表转换成JSON字符串
        Gson gson = new Gson();
        String tagsJson = gson.toJson(tags);
        // 将判题用例和判题配置转换成JSON字符串
        String judgeCase = gson.toJson(judgeCaseDTOList);
        String judgeConfig = gson.toJson(judgeConfigDTO);
        // 创建题目
        Question question = Question.builder()
                .title(title)
                .content(content)
                .answer(answer)
                .tags(tagsJson)
                .judgeCase(judgeCase)
                .judgeConfig(judgeConfig)
                .submitNum(0)
                .acceptNum(0)
                .build();
        // 插入数据
        boolean save = this.save(question);
        if (!save) {
            throw new ServerException(ErrorCode.SYSTEM_ERROR);
        }
        return question.getId();
    }
}




