package com.mxw.common.model.vo;


import lombok.Data;

import java.util.HashMap;
import java.util.List;

//雷达图数据
@Data
public class RadarDataVO {
    //特征标题值
    private List<String> legendTitle;
    //销售特征
    private List<String> indicators;
    //销售特征值
    private HashMap<String,List<Integer>> seriesDataList;
    //喜爱特征
    private List<String> favoriteFeature;
    //喜爱特征值
    private HashMap<String,List<Integer>> favoriteFeatureDataList;

}
