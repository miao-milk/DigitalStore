package com.mxw.common.model.dto;


import lombok.Data;

import java.io.Serializable;

@Data
public class PayDTO implements Serializable {

    private Integer total;
    private Double fee;
}
