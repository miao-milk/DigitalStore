package com.mxw.common.model.vo;


import lombok.Data;

import java.util.HashMap;
import java.util.List;

//雷达图数据
@Data
public class RadarDataVO {
    //属性名
    private HashMap<String,List<String>> indicators;
    //属性值
    private HashMap<String,List<Integer>> seriesDataList;

}
