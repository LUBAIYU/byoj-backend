package com.by.common.utils;

import lombok.Data;

/**
 * 通用返回结果工具类
 *
 * @param <T>
 * @author lzh
 */
@Data
public class Result<T> {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应成功，不带数据
     */
    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setData(null);
        result.setMessage("请求成功");
        return result;
    }

    /**
     * 响应成功，带返回数据
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setData(data);
        result.setMessage("请求成功");
        return result;
    }

    /**
     * 响应失败
     */
    public static <T> Result<T> error(Integer code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setData(null);
        result.setMessage(message);
        return result;
    }
}
