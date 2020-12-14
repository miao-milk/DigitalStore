package com.mxw.member.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@ApiModel(value = "ShopBuyerDTO",description = "用户信息传输模型")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopBuyerDTO {

    @ApiModelProperty("卖家id")
    private Long sellerId;

    @ApiModelProperty("买家昵称")
    private String buyerNick;

    @ApiModelProperty("收货人的姓名")
    private String receiverName;

    @ApiModelProperty("收货人的地址")
    private String receiverAddress;

    @ApiModelProperty("收货人的手机号码")
    private String receiverMobile;

    @ApiModelProperty("交易次数")
    private Integer buyTotalCount;

    @ApiModelProperty("交易金额（购买总金额）")
    private BigDecimal buyTotalMoney;

}
