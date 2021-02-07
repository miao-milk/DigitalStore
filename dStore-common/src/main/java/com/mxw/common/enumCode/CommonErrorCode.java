package com.mxw.common.enumCode;

import com.mxw.common.exception.ErrorCode;

/**
 * 异常编码
 */
public enum CommonErrorCode implements ErrorCode {

    /*****************公用异常编码***********************/
    ERROR_CODE_10001(10001,"查询参数错误"),
    ERROR_CODE_10002(10002,"用户不存在"),
    ERROR_CODE_10003(10003,"密码错误"),
    ERROR_CODE_10004(10004,"用户名已存在"),
    ERROR_CODE_10005(10005,"删除标签失败"),
    ERROR_CODE_10006(10006,"获取树形结构失败"),
    ERROR_CODE_10007(10007,"该分组名称已存在"),
    ERROR_CODE_10008(10008,"标签名不能为空"),
    ERROR_CODE_10009(10009,"分组已存在该用户"),

    /*****************特殊异常异常编码***********************/

    SYSTEM_ERROR(99998,"系统错误"),
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
