package com.mxw.common.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BuyerLabelDTO  implements Serializable {

    private String id;
    private String sellerId;
    private Integer labelId;
    private Date createTime;
    private Integer weight;
    private String labelName;
}
