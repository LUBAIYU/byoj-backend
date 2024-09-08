package com.by.common.enums;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

/**
 * @author lzh
 */
@Getter
public enum LanguageEnum {

    /**
     * 语言类型枚举
     */
    JAVA("Java", "java"),
    JS("JavaScript", "javascript"),
    GOLANG("Go", "go");

    private final String label;

    private final String value;

    LanguageEnum(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public static LanguageEnum getEnumByValue(String value) {
        if (StrUtil.isBlank(value)) {
            return null;
        }
        for (LanguageEnum languageEnum : LanguageEnum.values()) {
            if (languageEnum.value.equals(value)) {
                return languageEnum;
            }
        }
        return null;
    }
}
