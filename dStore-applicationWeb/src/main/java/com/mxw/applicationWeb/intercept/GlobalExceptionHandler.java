package com.mxw.applicationWeb.intercept;

import com.mxw.common.enumCode.CommonErrorCode;
import com.mxw.common.exception.MyException;
import com.mxw.common.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 拦截业务类异常
     */
    @ResponseBody
    @ExceptionHandler(value = MyException.class)
    public Result handlerMyException(MyException e){
        log.error("捕获到业务类异常:{}",e.getErrorCode().getDesc());
        return Result.error(e.getErrorCode().getCode(),e.getErrorCode().getDesc());
    }

    /**
     * 拦截运行时异常
     */
    @ResponseBody
    @ExceptionHandler(value = RuntimeException.class)
    public Result handlerRuntimeException(RuntimeException e){
        log.error("捕获到行时异常:{}",e);
        return Result.error(CommonErrorCode.UNKNOWN.getCode(),CommonErrorCode.UNKNOWN.getDesc());
    }


    /**
     * 拦截系统错误异常
     */
    @ResponseBody
    @ExceptionHandler(value = Throwable.class)
    public Result handlerThrowable(Throwable e){
        log.error("捕获到Throwable错误:{}",e);
        return Result.error(CommonErrorCode.SYSTEM_ERROR.getCode(),CommonErrorCode.SYSTEM_ERROR.getDesc());
    }

//    @ExceptionHandler(value = Exception.class)
//    @ResponseBody
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public RestErrorResponseVO exceptionGet1(HttpServletRequest req, HttpServletResponse response , Exception e) {
//        if (e instanceof MyException) {
//            MyException be = (MyException) e;
//            if(CommonErrorCode.CUSTOM.equals(be.getErrorCode())){
//                return new RestErrorResponseVO(String.valueOf(be.getErrorCode().getCode()), be.getMessage());
//            }else{
//                return new RestErrorResponseVO(String.valueOf(be.getErrorCode().getCode()), be.getErrorCode().getDesc());
//            }
//
//        }
//        LOGGER.error("【系统异常】{}", e);
//        return  new RestErrorResponseVO(String.valueOf(CommonErrorCode.UNKNOWN.getCode()), CommonErrorCode.UNKNOWN.getDesc()+ ":" + e.getMessage());
//    }



}