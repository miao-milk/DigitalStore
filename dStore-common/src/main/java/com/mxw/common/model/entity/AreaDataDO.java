package com.mxw.common.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("area_data")
public class AreaDataDO implements Serializable {

    @TableId
    private Long id;
    private String areaName;
    private Integer value;
}
