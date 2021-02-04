package com.mxw.common.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户画像词频类
 */
@Data
@AllArgsConstructor
public class LabelVO implements Serializable {
    private  String name;
    private  Integer value;
}
