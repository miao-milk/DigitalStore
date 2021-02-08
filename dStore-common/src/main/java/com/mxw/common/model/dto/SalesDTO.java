package com.mxw.common.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 累计销售额
 */
@Data
public class SalesDTO  implements Serializable {
    private String salesLastDay;
    //日同比
    private String salesGrowthLastDay;
    //昨日销售额
    private String salesToday;
}
