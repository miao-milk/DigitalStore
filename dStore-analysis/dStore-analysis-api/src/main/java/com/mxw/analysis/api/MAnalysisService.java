package com.mxw.analysis.api;

import com.mxw.common.model.vo.ChartResponseVO;

public interface MAnalysisService {

    ChartResponseVO getPurchaseAmount(String sellerId);

    ChartResponseVO getConsumptionRanking(String sellerId);
}
