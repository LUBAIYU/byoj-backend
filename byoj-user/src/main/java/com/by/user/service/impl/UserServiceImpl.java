package com.by.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.by.common.constant.UserConstants;
import com.by.common.enums.ErrorCode;
import com.by.common.enums.RoleEnum;
import com.by.common.enums.StatusEnum;
import com.by.common.exception.ServerException;
import com.by.common.utils.JwtUtil;
import com.by.common.utils.PageBean;
import com.by.model.dto.UserLoginDTO;
import com.by.model.dto.UserPageDTO;
import com.by.model.dto.UserRegisterDTO;
import com.by.model.dto.UserUpdateDTO;
import com.by.model.entity.User;
import com.by.model.vo.UserVO;
import com.by.user.mapper.UserMapper;
import com.by.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lzh
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Value("${jwt.encrypt.secretKey}")
    private String secretKey;

    @Override
    public long register(UserRegisterDTO userRegisterDTO) {
        // 获取参数
        String userAccount = userRegisterDTO.getUserAccount();
        String userPassword = userRegisterDTO.getUserPassword();
        String checkPassword = userRegisterDTO.getCheckPassword();

        // 校验密码和确认密码是否一致
        if (!userPassword.equals(checkPassword)) {
            throw new ServerException(ErrorCode.PARAMS_ERROR, UserConstants.PASSWORD_NOT_SAME);
        }

        // 判断账号是否存在
        User dbUser = this.lambdaQuery()
                .eq(User::getUserAccount, userAccount)
                .one();
        if (dbUser != null) {
            throw new ServerException(ErrorCode.PARAMS_ERROR, UserConstants.USER_ACCOUNT_EXISTS);
        }

        // 用户密码加密
        String salt = RandomUtil.randomString(4);
        String encryptPassword = DigestUtil.md5Hex(userPassword + salt);

        // 保存用户数据
        User user = User.builder()
                .userAccount(userAccount)
                .userPassword(encryptPassword)
                .userRole(RoleEnum.USER.getValue())
                .status(StatusEnum.ENABLE.getValue())
                .salt(salt)
                .build();
        boolean save = this.save(user);
        if (!save) {
            throw new ServerException(ErrorCode.SYSTEM_ERROR);
        }
        return user.getId();
    }

    @Override
    public String login(UserLoginDTO userLoginDTO) {
        // 获取参数
        String userAccount = userLoginDTO.getUserAccount();
        String userPassword = userLoginDTO.getUserPassword();

        // 判断用户是否存在
        User dbUser = this.lambdaQuery()
                .eq(User::getUserAccount, userAccount)
                .one();
        if (dbUser == null) {
            throw new ServerException(ErrorCode.PARAMS_ERROR, UserConstants.ACCOUNT_PASSWORD_ERROR);
        }

        // 判断密码是否正确
        String salt = dbUser.getSalt();
        String md5Password = DigestUtil.md5Hex(userPassword + salt);
        if (!dbUser.getUserPassword().equals(md5Password)) {
            throw new ServerException(ErrorCode.PARAMS_ERROR, UserConstants.ACCOUNT_PASSWORD_ERROR);
        }

        // 判断用户是否被禁用
        if (StatusEnum.DISABLE.getValue().equals(dbUser.getStatus())) {
            throw new ServerException(ErrorCode.PARAMS_ERROR, UserConstants.ACCOUNT_FORBIDDEN);
        }

        // 生成JWT令牌
        Map<String, Object> payload = new HashMap<>(5);
        // 存放用户ID
        payload.put("userId", dbUser.getId());
        // 存放用户角色
        payload.put("role", dbUser.getUserRole());
        return JwtUtil.createToken(payload, secretKey);
    }

    @Override
    public void removeUserById(Long id) {
        // 判断用户是否存在
        User user = this.getById(id);
        if (user == null) {
            throw new ServerException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 删除用户
        boolean res = this.removeById(id);
        if (!res) {
            throw new ServerException(ErrorCode.SYSTEM_ERROR);
        }
    }

    @Override
    public void updateUserById(UserUpdateDTO userUpdateDTO) {
        User user = new User();
        BeanUtil.copyProperties(userUpdateDTO, user);
        // 对输入密码进行加密
        String userPassword = userUpdateDTO.getUserPassword();
        if (StrUtil.isNotBlank(userPassword)) {
            Long id = userUpdateDTO.getId();
            User dbUser = this.getById(id);
            String salt = dbUser.getSalt();
            String encryptPassword = DigestUtil.md5Hex(userPassword + salt);
            user.setUserPassword(encryptPassword);
        }
        boolean res = this.updateById(user);
        if (!res) {
            throw new ServerException(ErrorCode.SYSTEM_ERROR);
        }
    }

    @Override
    public UserVO getUserById(Long id) {
        // 判断数据是否存在
        User dbUser = this.getById(id);
        if (dbUser == null) {
            throw new ServerException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 返回脱敏后的数据
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(dbUser, userVO);
        return userVO;
    }

    @Override
    public PageBean<UserVO> listUserVoByPage(UserPageDTO userPageDTO) {
        // 查询数据
        IPage<UserVO> pageRes = userMapper.pageUserVo(userPageDTO.toPagination(), userPageDTO);
        // 封装数据
        return PageBean.of(pageRes.getTotal(), pageRes.getRecords());
    }

    @Override
    public PageBean<User> listUserByPage(UserPageDTO userPageDTO) {
        // 获取查询参数
        Long id = userPageDTO.getId();
        String userName = userPageDTO.getUserName();
        Integer status = userPageDTO.getStatus();

        // 添加分页参数
        Page<User> page = new Page<>(userPageDTO.getCurrent(), userPageDTO.getPageSize());

        // 获取枚举值
        StatusEnum statusEnum = StatusEnum.getEnumByValue(status);

        // 添加查询参数
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(id != null, User::getId, id);
        queryWrapper.like(StrUtil.isNotBlank(userName), User::getUserName, userName);
        queryWrapper.eq(statusEnum != null, User::getStatus, status);

        // 添加排序条件
        page.addOrder(new OrderItem("createTime", false));

        // 查询
        this.page(page, queryWrapper);
        // 封装数据返回
        return PageBean.of(page.getTotal(), page.getRecords());
    }
}
