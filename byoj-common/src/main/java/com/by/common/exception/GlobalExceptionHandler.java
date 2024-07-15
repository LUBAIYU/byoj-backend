package com.by.common.exception;

import com.by.common.enums.ErrorCode;
import com.by.common.utils.Result;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 全局异常处理器
 *
 * @author lzh
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServerException.class)
    public static <T> Result<T> handleServerException(ServerException se) {
        return Result.error(se.getCode(), se.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public static <T> Result<T> handleValidException(MethodArgumentNotValidException me) {
        List<ObjectError> allErrors = me.getBindingResult().getAllErrors();
        StringBuilder errorMsg = new StringBuilder();
        for (int i = 0; i < allErrors.size(); i++) {
            ObjectError error = allErrors.get(i);
            errorMsg.append(error.getDefaultMessage());
            if (i != allErrors.size() - 1) {
                errorMsg.append(",");
            }
        }
        return Result.error(ErrorCode.PARAMS_ERROR.getCode(), errorMsg.toString());
    }
}
