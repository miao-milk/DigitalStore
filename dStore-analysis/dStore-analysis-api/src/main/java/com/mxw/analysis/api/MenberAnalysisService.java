package com.mxw.analysis.api;


import com.mxw.common.model.vo.ChartAnalysisVO;
import com.mxw.common.model.vo.ChartResponseVO;
import com.mxw.common.model.vo.NewOldBuyerCompareVO;

import java.util.List;

/**
 * 全链路用户分析
 */
public interface MenberAnalysisService {
    ChartResponseVO getNumberOfMembers(String sellerId);

    ChartResponseVO getLevelMembershipChanges(String sellerId);

    List<NewOldBuyerCompareVO> getNewAndOldMembers(String sellerId);

    ChartAnalysisVO getChartAnalysisResult(String analysisType, String sellerId);
}
