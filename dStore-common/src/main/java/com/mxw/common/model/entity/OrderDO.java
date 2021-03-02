package com.mxw.common.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("orders")
public class OrderDO {

    @TableId
    private Integer orderId;
    private Integer sellerId;
    private Integer shopBuyerId;
    private String postTel;
    private String buyerRate;
    private String productName;
    private Integer productId;
    private Integer price;
    private Integer num;
    private Integer totalFee;
    private Integer payment;
    private String createTime;
    private String payTime;
    private String payType;
}
