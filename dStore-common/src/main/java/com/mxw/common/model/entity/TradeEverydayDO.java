package com.mxw.common.model.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("seller_everyday")
public class TradeEverydayDO {

    @TableId
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
