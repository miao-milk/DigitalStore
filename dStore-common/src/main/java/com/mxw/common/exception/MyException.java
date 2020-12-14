package com.mxw.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义的异常类型
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyException extends RuntimeException{

    //枚举各个异常代码和信息
    private ErrorCode errorCode;

}
