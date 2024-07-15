package com.by.common.enums;

import lombok.Getter;

/**
 * 角色枚举
 *
 * @author lzh
 */
@Getter
public enum RoleEnum {

    /**
     * 管理员
     */
    ADMIN(0, "管理员"),
    /**
     * 普通用户
     */
    USER(1, "普通用户");

    private final Integer value;

    private final String label;

    RoleEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
}
