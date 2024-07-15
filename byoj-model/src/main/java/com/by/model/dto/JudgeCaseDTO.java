package com.by.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 判题用例
 *
 * @author lzh
 */
@Data
public class JudgeCaseDTO implements Serializable {
    /**
     * 输入
     */
    @NotNull(message = "输入不能为null")
    @NotBlank(message = "输入不能为空")
    private String input;
    /**
     * 输出
     */
    @NotNull(message = "输出不能为null")
    @NotBlank(message = "输出不能为空")
    private String output;
}
