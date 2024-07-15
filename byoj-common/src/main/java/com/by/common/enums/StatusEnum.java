package com.by.common.enums;

import lombok.Getter;

/**
 * 状态枚举
 *
 * @author lzh
 */
@Getter
public enum StatusEnum {

    /**
     * 启用
     */
    ENABLE(0, "启用"),
    /**
     * 禁用
     */
    DISABLE(1, "禁用");

    private final Integer value;

    private final String label;

    StatusEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
}
