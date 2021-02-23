package com.mxw.common.model.vo;


import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

//雷达图数据
@Data
public class RadarDataVO implements Serializable {
    //特征标题值
    private List<String> legendTitle;
    //销售特征
    private Set<String> indicators;
    //销售特征值
    private HashMap<String,List<Integer>> seriesDataList;
    //喜爱特征
    private Set<String> favoriteFeature;
    //喜爱特征值
    private HashMap<String,List<Integer>> favoriteFeatureDataList;

}
