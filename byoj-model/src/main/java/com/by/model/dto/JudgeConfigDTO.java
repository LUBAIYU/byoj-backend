package com.by.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 判题配置
 *
 * @author lzh
 */
@Data
public class JudgeConfigDTO implements Serializable {
    /**
     * 内存限制
     */
    @NotNull(message = "内存限制不能为null")
    @NotBlank(message = "内存限制不能为空")
    private Long memoryLimit;
    /**
     * 时间限制
     */
    @NotNull(message = "时间限制不能为null")
    @NotBlank(message = "时间限制不能为空")
    private Long timeLimit;
}
