package com.mxw.analysis.api;

import com.mxw.common.model.vo.ChartResponseVO;

import java.util.Map;

public interface MAnalysisService {

    ChartResponseVO getPurchaseAmount(String sellerId);

    Map<String, Integer> getConsumptionRanking(String sellerId);
}
