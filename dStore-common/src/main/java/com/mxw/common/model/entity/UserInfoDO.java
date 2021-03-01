package com.mxw.common.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("seller_info")
public class UserInfoDO {

    @TableId
    private Integer sellerId;
    private String sellerNick;
    private Integer sex;
    private Double fee;
    private String mobile;
    private String address;
    private Date birthday;
    private String introduction;
    private String imageUrl;

}
