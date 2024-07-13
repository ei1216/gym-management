package com.bequre.exception;

import com.bequre.pojo.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *  定义一个全局异常处理类，可以捕获并处理所有控制器层抛出的异常，
 *  作用其实就是将500错误统一处理（不处理就是固定的500错误，信息是固定的“Internal Server Error”）
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)      //处理所有异常
    public Result handleException(Exception e){
        e.printStackTrace();
        //因为有的异常没有错误原因（为空），因此要考虑这种情况
        return Result.error(StringUtils.hasLength(e.getMessage()) ? e.getMessage() : "操作失败");
    }

}
