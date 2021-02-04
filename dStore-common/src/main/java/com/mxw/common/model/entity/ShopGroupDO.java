package com.mxw.common.model.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("shop_group")
public class ShopGroupDO {
    /**
     * 分组id
     */
    @TableId
    private Integer groupId;
    /**
     * 分组名
     */
    private String groupName;
    /**
     * 标签创建时间
     */
    private Date createTime;

    /**
     * 父标签
     */
    private Integer pid;

    /**
     * 分组id
     */
    private Integer sellerId;
}
