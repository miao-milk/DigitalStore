package com.mxw.common.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class GroupDetailVO implements Serializable {

    private String labelName;
    private String fatherName;
    private String createTime;
    private String updateTime;
}
