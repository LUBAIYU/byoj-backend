package com.by.common.enums;

import lombok.Getter;

/**
 * 题目提交状态枚举
 *
 * @author lzh
 */
@Getter
public enum QuestionSubmitStatusEnum {

    /**
     * 题目状态枚举
     */
    WAITING(0, "等待中"),
    RUNNING(1, "判题中"),
    ACCEPTED(2, "通过"),
    FAILED(3, "失败");

    private final Integer value;

    private final String text;

    QuestionSubmitStatusEnum(Integer value, String text) {
        this.value = value;
        this.text = text;
    }

    public static QuestionSubmitStatusEnum getEnumByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (QuestionSubmitStatusEnum questionSubmitStatusEnum : QuestionSubmitStatusEnum.values()) {
            if (questionSubmitStatusEnum.value.equals(value)) {
                return questionSubmitStatusEnum;
            }
        }
        return null;
    }
}
