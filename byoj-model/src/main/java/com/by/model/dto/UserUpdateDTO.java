package com.by.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author lzh
 */
@Data
public class UserUpdateDTO implements Serializable {
    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为null")
    private Long id;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 用户密码
     */
    private String userPassword;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户角色（0-管理员，1-普通用户）
     */
    private Integer userRole;

    /**
     * 用户状态（0-启用，1-禁用）
     */
    private Integer status;
}
