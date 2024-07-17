package com.by.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.by.common.utils.PageBean;
import com.by.model.dto.UserLoginDTO;
import com.by.model.dto.UserPageDTO;
import com.by.model.dto.UserRegisterDTO;
import com.by.model.dto.UserUpdateDTO;
import com.by.model.entity.User;
import com.by.model.vo.UserVO;

/**
 * @author lzh
 */
public interface UserService extends IService<User> {
    /**
     * 用户注册
     *
     * @param userRegisterDTO 请求体
     * @return 用户ID
     */
    long register(UserRegisterDTO userRegisterDTO);

    /**
     * 用户登录
     *
     * @param userLoginDTO 登录请求体
     * @return token
     */
    String login(UserLoginDTO userLoginDTO);

    /**
     * 根据ID删除用户
     *
     * @param id 用户ID
     * @return 是否删除成功
     */
    void removeUserById(Long id);

    /**
     * 根据ID修改用户信息
     *
     * @param userUpdateDTO 用户修改信息请求体
     */
    void updateUserById(UserUpdateDTO userUpdateDTO);

    /**
     * 根据ID获取用户信息
     *
     * @param id 用户ID
     * @return 用户脱敏后的数据
     */
    UserVO getUserById(Long id);

    /**
     * 分页获取用户信息（脱敏）
     *
     * @param userPageDTO 分页请求体
     * @return 用户信息
     */
    PageBean<UserVO> listUserVoByPage(UserPageDTO userPageDTO);

    /**
     * 分页获取用户信息
     *
     * @param userPageDTO 分页请求体
     * @return 用户信息
     */
    PageBean<User> listUserByPage(UserPageDTO userPageDTO);
}
