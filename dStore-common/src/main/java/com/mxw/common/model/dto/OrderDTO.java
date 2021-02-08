package com.mxw.common.model.dto;



import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * 累计订单量
 */
@Data
public class OrderDTO implements Serializable {
    private String orderToday;
    private String orderLastDay;
}
