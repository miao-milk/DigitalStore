package com.mxw.common.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 会员消费等级
 */
@Data
public class MemberConsumptionLevelVO  implements Serializable {

    private Integer peopleTotal;
    private Integer seniorNumber;
    private Integer intermediateNumber;
    private Integer ordinaryNumber;
    private BigDecimal seniorAmount;
    private BigDecimal intermediateAmount;
    private BigDecimal ordinaryAmount;
}
