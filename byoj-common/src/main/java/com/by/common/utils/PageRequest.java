package com.by.common.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 通用分页参数
 *
 * @author by
 */
@Data
public class PageRequest<T> implements Serializable {
    /**
     * 当前页码
     */
    @NotNull(message = "页号不能为空")
    private Integer current = 1;
    /**
     * 每页记录数
     */
    @NotNull(message = "每页记录数不能为空")
    private Integer pageSize = 5;

    /**
     * 获取分页对象
     */
    public Page<T> toPagination() {
        return new Page<>(current, pageSize);
    }
}
