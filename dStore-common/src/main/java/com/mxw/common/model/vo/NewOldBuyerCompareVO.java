package com.mxw.common.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 新老会员对比分析
 */
@SuppressWarnings("serial")
@ApiModel(value = "新老会员对比分析")
@Data
public class NewOldBuyerCompareVO implements Serializable {

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("百分比")
    private Double percent;

    @ApiModelProperty("客户总数")
    private Integer buyerCount;

    @ApiModelProperty("订单数")
    private Integer tradeCount;

    @ApiModelProperty("成交金额")
    private Double payment;

    @ApiModelProperty("客单价")
    private Double buyerAvgCost;

    @ApiModelProperty("复购客户数")
    private Integer returnBuyerCountSum;

    @ApiModelProperty("复购成交额(元)")
    private Double returnBuyerPaymentSum;

    @ApiModelProperty("复购率")
    private Double returnBuyerPercentSum;
}
