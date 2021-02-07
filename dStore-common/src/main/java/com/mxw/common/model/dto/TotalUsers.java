package com.mxw.common.model.dto;

import lombok.Data;

/**
 * 累计用户数
 */
@Data
public class TotalUsers {
    private String userGrowthLastDay;
    private String userGrowthLastMonth;
    private String userLastMonth;
    private String userTodayNumber;
    private String userToday;
}
