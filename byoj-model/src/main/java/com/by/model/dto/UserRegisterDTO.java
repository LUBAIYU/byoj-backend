package com.by.model.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author lzh
 */
@Data
public class UserRegisterDTO implements Serializable {
    /**
     * 用户账号
     */
    @NotNull(message = "用户账号不能为null")
    @NotBlank(message = "用户账号不能为空")
    @Length(min = 1, message = "账号长度不能小于1位")
    private String userAccount;
    /**
     * 密码
     */
    @NotNull(message = "用户密码不能为null")
    @NotBlank(message = "用户密码不能为空")
    @Length(min = 8, message = "密码长度不能小于8位")
    private String userPassword;
    /**
     * 确认密码
     */
    @NotNull(message = "确认密码不能为null")
    @NotBlank(message = "确认密码不能为空")
    @Length(min = 8, message = "确认密码长度不能小于8位")
    private String checkPassword;
}
