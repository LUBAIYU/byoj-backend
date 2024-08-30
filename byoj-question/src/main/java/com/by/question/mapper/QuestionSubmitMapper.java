package com.by.question.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.by.model.dto.QuestionSubmitPageDTO;
import com.by.model.entity.QuestionSubmit;
import com.by.model.vo.QuestionSubmitVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author lzh
 */
@Mapper
public interface QuestionSubmitMapper extends BaseMapper<QuestionSubmit> {

    /**
     * 分页查询题目提交记录
     *
     * @param pagination            分页条件
     * @param questionSubmitPageDTO 查询条件
     * @param userRole              用户角色
     * @param userId                用户ID
     * @return 查询结果
     */
    IPage<QuestionSubmitVO> pageQuestionSubmitVos(@Param("pagination") IPage<QuestionSubmit> pagination, @Param("dto") QuestionSubmitPageDTO questionSubmitPageDTO, @Param("userRole") Integer userRole, @Param("userId") Long userId);
}




