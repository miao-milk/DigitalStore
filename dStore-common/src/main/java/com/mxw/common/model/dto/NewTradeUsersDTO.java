package com.mxw.common.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * 今日新增交易用户数
 */
@Data
public class NewTradeUsersDTO implements Serializable {

    private Integer returnRate;
    private Integer orderUser;
    private List<String>   oldTimeList;
    private List<Integer>   oldUserList;

}