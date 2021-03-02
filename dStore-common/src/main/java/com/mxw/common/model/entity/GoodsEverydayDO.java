package com.mxw.common.model.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@TableName("goods_everyday")
public class GoodsEverydayDO {

    @TableId
    private Integer id;
    private Integer sellerId;
    private Integer goodsId;
    private String goodsName;
    private String goodsSalesFetures;
    private String goodsLikeFetures;
    private Integer goodsBuyer;
    private Integer goodsPrice;
    private Date createTime;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public  static class GoodsFeture{
        private String fetureName;
        private Integer peopleNum;
    }
}
