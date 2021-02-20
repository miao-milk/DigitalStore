package com.mxw.analysis.api;

import com.mxw.common.model.vo.ChartResponseVO;
import com.mxw.common.model.vo.RadarDataVO;

/**
 * 商品分析
 */
public interface GoodAnalysisService {

    RadarDataVO getProductSalesCharacteristics(String sellerId);

    ChartResponseVO getCommoditySales(String sellerId);
}
