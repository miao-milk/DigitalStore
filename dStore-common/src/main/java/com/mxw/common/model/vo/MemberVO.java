package com.mxw.common.model.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "MemberVO",description = "返回会员用户结果")
@Data
public class MemberVO {

    @ApiModelProperty("买家ID")
    private Long shopBuyerId;

    @ApiModelProperty("买家昵称(旺旺名称)")
    private String buyerNick;

    @ApiModelProperty("收货人的姓名")
    private String receiverName;

    @ApiModelProperty("收货人的地址")
    private String receiverAddress;
}
