package com.mxw.common.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("trade")
public class TradeDO {

    @TableId
    private Long tradeId;
    private Long sellerId;
    private Long shopBuyerId;
    private BigDecimal receivedPayment;
    private BigDecimal postAmount;
    private String postReceiver;
    private String postAddr;
    private String postCode;
    private String postTel;
    private String tid;
    private Integer num;
    private BigDecimal price;
    private BigDecimal totalFee;
    private Date payTime;
    private Date updateTime;
    private Date logisticsTime;
    private Date receiptTime;
    private String logisticsId;
    private String logisticsCode;
    private String logisticsStatus;
    private Date createTime;
    private BigDecimal orderTotalAmount;
    private Date payType;
}
