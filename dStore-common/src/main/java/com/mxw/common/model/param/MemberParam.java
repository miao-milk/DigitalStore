package com.mxw.common.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "MemberParam",description = "会员用户查询参数")
@Data
public class MemberParam {

    @ApiModelProperty("买家昵称(旺旺名称)")
    private String buyerNick;

    @ApiModelProperty("收货人的姓名")
    private String receiverName;

    @ApiModelProperty("收货人的地址")
    private String receiverAddress;

    @ApiModelProperty("收货人的邮编码,保存到市（海外：000000）")
    private String receiverZip;

    @ApiModelProperty("收货人的手机号码")
    private String receiverMobile;

    @ApiModelProperty("当前页面")
    private Long page;

    @ApiModelProperty("页面大小")
    private Long pageSize;
}
