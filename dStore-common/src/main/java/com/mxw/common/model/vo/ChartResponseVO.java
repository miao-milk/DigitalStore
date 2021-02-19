package com.mxw.common.model.vo;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class ChartResponseVO {

    private List<String> xAxisData;
    private HashMap<String,List<Integer>> seriesData;
}
