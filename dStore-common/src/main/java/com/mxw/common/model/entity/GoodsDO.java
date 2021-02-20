package com.mxw.common.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("goods")
public class GoodsDO {

    @TableId
    private Integer id;
    private Integer sellerId;
    private String goodsName;
}
