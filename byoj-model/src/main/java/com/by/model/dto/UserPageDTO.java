package com.by.model.dto;

import com.by.common.utils.PageRequest;
import com.by.model.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author lzh
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserPageDTO extends PageRequest<User> implements Serializable {
    /**
     * 用户ID
     */
    private Long id;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户状态（0-启用，1-禁用）
     */
    private Integer status;
}
