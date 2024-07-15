package com.by.question.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.by.model.dto.QuestionPageDTO;
import com.by.model.entity.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author lzh
 */
@Mapper
public interface QuestionMapper extends BaseMapper<Question> {

    /**
     * 分页查询
     *
     * @param pagination      分页参数
     * @param questionPageDTO 请求体
     * @return 题目类别
     */
    IPage<Question> pageQuestionVos(Page<Question> pagination, @Param("dto") QuestionPageDTO questionPageDTO);
}




