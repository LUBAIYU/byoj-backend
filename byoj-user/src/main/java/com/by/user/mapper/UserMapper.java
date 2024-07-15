package com.by.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.by.model.dto.UserPageDTO;
import com.by.model.entity.User;
import com.by.model.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author lzh
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 分页查询用户
     *
     * @param pagination  分页参数
     * @param userPageDTO 查询条件
     * @return 查询结果
     */
    IPage<UserVO> pageUserVo(Page<User> pagination, @Param("dto") UserPageDTO userPageDTO);
}
