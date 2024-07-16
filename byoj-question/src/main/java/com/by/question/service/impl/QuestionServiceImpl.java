package com.by.question.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.by.common.enums.ErrorCode;
import com.by.common.exception.ServerException;
import com.by.common.utils.PageBean;
import com.by.model.dto.QuestionPageDTO;
import com.by.model.entity.JudgeCase;
import com.by.model.entity.JudgeConfig;
import com.by.model.dto.QuestionAddDTO;
import com.by.model.dto.QuestionUpdateDTO;
import com.by.model.entity.Question;
import com.by.model.vo.QuestionVO;
import com.by.question.service.QuestionService;
import com.by.question.mapper.QuestionMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lzh
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
        implements QuestionService {

    @Resource
    private QuestionMapper questionMapper;

    @Override
    public long addQuestion(QuestionAddDTO questionAddDTO) {
        // 获取参数
        String title = questionAddDTO.getTitle();
        String content = questionAddDTO.getContent();
        String answer = questionAddDTO.getAnswer();
        List<String> tagList = questionAddDTO.getTagList();
        List<JudgeCase> judgeCaseDTOList = questionAddDTO.getJudgeCaseDTOList();
        JudgeConfig judgeConfigDTO = questionAddDTO.getJudgeConfigDTO();
        // 将标签列表转换成JSON字符串
        Gson gson = new Gson();
        String tags = gson.toJson(tagList);
        // 将判题用例和判题配置转换成JSON字符串
        String judgeCase = gson.toJson(judgeCaseDTOList);
        String judgeConfig = gson.toJson(judgeConfigDTO);
        // 创建题目
        Question question = Question.builder()
                .title(title)
                .content(content)
                .answer(answer)
                .tags(tags)
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

    @Override
    public void delQuestionById(Long id) {
        // 判断数据是否存在
        Question question = this.getById(id);
        if (question == null) {
            throw new ServerException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 删除数据
        boolean res = this.removeById(id);
        if (!res) {
            throw new ServerException(ErrorCode.SYSTEM_ERROR);
        }
    }

    @Override
    public void updateQuestionById(QuestionUpdateDTO questionUpdateDTO) {
        Question question = new Question();
        BeanUtil.copyProperties(questionUpdateDTO, question);
        List<String> tagList = questionUpdateDTO.getTagList();
        List<JudgeCase> judgeCaseDTOList = questionUpdateDTO.getJudgeCaseDTOList();
        JudgeConfig judgeConfigDTO = questionUpdateDTO.getJudgeConfigDTO();
        // 转为JSON
        Gson gson = new Gson();
        if (!CollectionUtils.isEmpty(tagList)) {
            String tags = gson.toJson(tagList);
            question.setTags(tags);
        }
        if (!CollectionUtils.isEmpty(judgeCaseDTOList)) {
            String judgeCase = gson.toJson(judgeCaseDTOList);
            question.setJudgeCase(judgeCase);
        }
        if (judgeConfigDTO != null) {
            String judgeConfig = gson.toJson(judgeConfigDTO);
            question.setJudgeConfig(judgeConfig);
        }
        // 更新数据
        boolean update = this.updateById(question);
        if (!update) {
            throw new ServerException(ErrorCode.SYSTEM_ERROR);
        }
    }

    @Override
    public QuestionVO getQuestionVoById(Long id) {
        // 判断数据是否存在
        Question question = this.getById(id);
        if (question == null) {
            throw new ServerException(ErrorCode.NOT_FOUND_ERROR);
        }
        QuestionVO questionVO = new QuestionVO();
        BeanUtil.copyProperties(question, questionVO);
        String tags = question.getTags();
        String judgeConfig = question.getJudgeConfig();
        // 将JSON字符串转为对象
        Gson gson = new Gson();
        List<String> tagList = gson.fromJson(tags, new TypeToken<List<String>>() {
        });
        JudgeConfig judgeConfigVO = gson.fromJson(judgeConfig, JudgeConfig.class);
        questionVO.setTagList(tagList);
        questionVO.setJudgeConfigVO(judgeConfigVO);
        return questionVO;
    }

    @Override
    public PageBean<QuestionVO> pageQuestionVos(QuestionPageDTO questionPageDTO) {
        // 获取题目列表
        IPage<Question> questionIpage = questionMapper.pageQuestionVos(questionPageDTO.toPagination(), questionPageDTO);
        // 获取题目数据
        long total = questionIpage.getTotal();
        List<Question> records = questionIpage.getRecords();
        List<QuestionVO> questionVOList = new ArrayList<>();
        // 如果为空直接返回
        if (CollectionUtils.isEmpty(records)) {
            return PageBean.of(total, questionVOList);
        }
        // 封装返回数据
        Gson gson = new Gson();
        questionVOList = records.stream().map(question -> {
            QuestionVO questionVO = new QuestionVO();
            BeanUtil.copyProperties(question, questionVO);
            String tags = question.getTags();
            String judgeConfig = question.getJudgeConfig();
            if (StrUtil.isNotBlank(tags)) {
                questionVO.setTagList(gson.fromJson(tags, new TypeToken<List<String>>() {
                }));
            }
            if (StrUtil.isNotBlank(judgeConfig)) {
                questionVO.setJudgeConfigVO(gson.fromJson(judgeConfig, JudgeConfig.class));
            }
            return questionVO;
        }).collect(Collectors.toList());
        return PageBean.of(total, questionVOList);
    }

    @Override
    public PageBean<Question> pageQuestions(QuestionPageDTO questionPageDTO) {
        IPage<Question> questionIpage = questionMapper.pageQuestionVos(questionPageDTO.toPagination(), questionPageDTO);
        long total = questionIpage.getTotal();
        List<Question> records = questionIpage.getRecords();
        return PageBean.of(total, records);
    }
}




