package com.mxw.common.enumCode;

import com.mxw.common.exception.ErrorCode;

/**
 * 异常编码
 */
public enum CommonErrorCode implements ErrorCode {

    /*****************公用异常编码***********************/
    ERROR_CODE_10001(10001,"查询参数错误"),

    /*****************特殊异常异常编码***********************/

    CUSTOM(99998,"自定义异常"),
    /*****************未知错误***********************/

    UNKNOWN(99999,"未知错误");

    private int code;
    private String desc;

   private CommonErrorCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    /**
     * 设置对应的code，返回对应的描述信息
     * @param code
     * @return
     */
    public static CommonErrorCode setErrorCode(int code){
        for (CommonErrorCode errorCode : CommonErrorCode.values()) {
            if (errorCode.getCode()==code){
                return errorCode;
            }
        }
        return null;
    }

}
