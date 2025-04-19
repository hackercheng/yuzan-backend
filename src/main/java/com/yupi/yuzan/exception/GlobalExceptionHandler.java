package com.yupi.yuzan.exception;

import com.yupi.yuzan.common.BaseResponse;
import com.yupi.yuzan.common.ErrorCode;
import com.yupi.yuzan.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public BaseResponse<?> businessExceptionHandler(BusinessException e) {
        log.error("BusinessException：",e);
        return Result.error(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> businessExceptionHandler(RuntimeException e) {
        log.error("RuntimeException：",e);
        return Result.error(ErrorCode.SYSTEM_ERROR,e.getMessage());
    }
}
