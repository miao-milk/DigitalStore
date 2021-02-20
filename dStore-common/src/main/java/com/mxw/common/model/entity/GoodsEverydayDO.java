package com.mxw.common.model.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

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
    private List<GoodsFeture> goodsSalesFetures;
    private List<GoodsFeture> goodsLikeFetures;
    private Integer goodsBuyer;
    private Integer goodsPrice;
    private Date createTime;


    @Data
    public  class GoodsFeture{
        private String fetureName;
        private Integer peopleNum;
    }
}
