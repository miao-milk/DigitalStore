package com.mxw.common.model.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("goods_everyday")
public class GoodsEverydayDO {

    @TableId
    private Integer id;
    private Integer goodsId;
    private String goodsFeture;
    private Integer goodsBuyer;
    private Integer goodsViews;
    private Integer goodsPrice;
    private Date createTime;


    @Data
    class GoodsFeture{
        private String feture;
        private Integer peopleNum;
    }
}
