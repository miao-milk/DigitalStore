package com.mxw.common.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@Data
public class ChartResponseVO implements Serializable {

    private List<String> xAxisData;
    private HashMap<String,List<Integer>> seriesData;
}
