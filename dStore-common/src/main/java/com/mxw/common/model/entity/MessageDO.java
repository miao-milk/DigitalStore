package com.mxw.common.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("send_record")
public class MessageDO implements Serializable {

    @TableId
    private Integer id;
    private String mobile;
    private Integer sellerId;
    private String content;
    private Date sendTime;
}
