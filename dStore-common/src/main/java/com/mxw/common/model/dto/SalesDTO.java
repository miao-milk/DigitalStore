package com.mxw.common.model.dto;

import lombok.Data;

/**
 * 累计销售额
 */
@Data
public class SalesDTO {
    private String salesLastDay;
    private String salesGrowthLastDay;
    private String salesGrowthLastMonth;
    private String salesToday;
}
