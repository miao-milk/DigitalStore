package com.mxw.common.model.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@TableName("buyer_everyday")
public class BuyerEverydayDO implements Comparable<BuyerEverydayDO>{
    @TableId
    private Integer id;
    private Integer sellerId;
    private Integer buyerId;
    private Integer buyPrice;
    private String buyerName;
    private Date createTime;

    @Override
    public int compareTo(BuyerEverydayDO ob) {
        return this.buyPrice.compareTo(ob.getBuyPrice());
    }
}
