package com.mxw.common.model.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("trade_everyday")
public class TradeEverydayDO {

    private Integer id;
    private Integer sellerId;
    private BigDecimal sales;
    private Integer orders;
    private Integer newuser;
    private Integer deal;
    private Date createTime;
    private Integer userNum;
    private Integer ordinaryMemberNum;
    private Integer intermediateMemberNum;
    private Integer seniorMemberNum;
}
