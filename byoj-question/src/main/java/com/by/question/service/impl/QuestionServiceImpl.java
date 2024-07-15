package com.by.question.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.by.model.entity.Question;
import com.by.question.service.QuestionService;
import com.by.question.mapper.QuestionMapper;
import org.springframework.stereotype.Service;

/**
* @author lzh
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService {

}




