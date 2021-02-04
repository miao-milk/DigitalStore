package com.mxw.member.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@ApiModel(value = "ShopBuyerDTO",description = "用户信息传输模型")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopBuyerDTO implements Serializable {

    @ApiModelProperty("会员id")
    private Long shopBuyerId;

    @ApiModelProperty("会员id")
    private Long sellerId;

    @ApiModelProperty("买家昵称")
    private String buyerNick;

    @ApiModelProperty("收货人的姓名")
    private String receiverName;

    @ApiModelProperty("收货人的地址")
    private String receiverAddress;

    @ApiModelProperty("收货人的手机号码")
    private String receiverMobile;

    @ApiModelProperty("人群画像类型，1:学生 2:白领 3:公务员 4:IT 5:金融族 6:居家族 7:医护人员 8:其他")
    private Integer crowdPortraitType;

    @ApiModelProperty("交易次数")
    private Integer buyTotalCount;

    @ApiModelProperty("交易金额（购买总金额）")
    private BigDecimal buyTotalMoney;

    @ApiModelProperty("会员等级，0：店铺客户，1：普通会员，2：高级会员，3：VIP会员， 4：至尊VIP会员。如果不传入值则默认为全部等级。")
    private Integer grade;

}
