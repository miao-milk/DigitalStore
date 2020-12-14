package com.mxw.applicationWeb.intercept;

import com.mxw.common.enumCode.CommonErrorCode;
import com.mxw.common.exception.MyException;
import com.mxw.common.model.vo.RestErrorResponseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponseVO exceptionGet1(HttpServletRequest req, HttpServletResponse response , Exception e) {
        if (e instanceof MyException) {
            MyException be = (MyException) e;
            if(CommonErrorCode.CUSTOM.equals(be.getErrorCode())){
                return new RestErrorResponseVO(String.valueOf(be.getErrorCode().getCode()), be.getMessage());
            }else{
                return new RestErrorResponseVO(String.valueOf(be.getErrorCode().getCode()), be.getErrorCode().getDesc());
            }

        }
        LOGGER.error("【系统异常】{}", e);
        return  new RestErrorResponseVO(String.valueOf(CommonErrorCode.UNKNOWN.getCode()), CommonErrorCode.UNKNOWN.getDesc()+ ":" + e.getMessage());
    }


}