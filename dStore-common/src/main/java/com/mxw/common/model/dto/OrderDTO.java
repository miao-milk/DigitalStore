package com.mxw.common.model.dto;



import lombok.Data;

import java.util.HashMap;
import java.util.List;

/**
 * 累计订单量
 */
@Data
public class OrderDTO {
    private String orderToday;
    private String orderLastDay;
    private List<HashMap<String, String>> dataMap;

}
