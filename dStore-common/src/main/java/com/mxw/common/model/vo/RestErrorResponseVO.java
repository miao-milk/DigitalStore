package com.mxw.common.model.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author Administrator
 * @version 1.0
 **/
@ApiModel(value = "RestErrorResponse", description = "错误响应参数包装VO")
@Data
public class RestErrorResponseVO {

    private String errCode;

    private String errMessage;

    public RestErrorResponseVO(String errCode,String errMessage){
        this.errCode = errCode;
        this.errMessage= errMessage;
    }


}
