package com.mxw.common.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("trade")
public class TradeDO {

    private Long tradeId;
    private Long sellerId;
    private Long shopBuyerId;
    private BigDecimal receivedPayment;
    private BigDecimal totalFee;
    private BigDecimal postAmount;
    private BigDecimal price;
    private String postReceiver;
    private String postAddr;
    private String postCode;
    private String postTel;
    private String status;
    private String bType;
    private String cBiz;
    private String tid;
    private String shippingType;
    private String logisticsId;
    private String logisticsCode;
    private String logisticsStatus;
    private String logisticsModified;
    private Integer postTelType;
    private Integer num;
    private Date payTime;
    private Date updateTime;
    private Date logisticsTime;
    private Date receiptTime;
    private Date lastShippingUpdate_time;
    private Date lastSendTime;
    private Date createTime;
    private Date orderTotalAmount;
    private Date payType;
}
