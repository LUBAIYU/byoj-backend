package com.by.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lzh
 */
@Data
public class UserVO implements Serializable {
    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户账号
     */
    private String userAccount;

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

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
