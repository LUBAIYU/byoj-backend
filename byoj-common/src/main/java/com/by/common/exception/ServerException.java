package com.by.common.exception;

import com.by.common.enums.ErrorCode;
import lombok.Getter;

/**
 * @author lzh
 */
@Getter
public class ServerException extends RuntimeException {

    public final Integer code;

    public ServerException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public ServerException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public ServerException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }
}
