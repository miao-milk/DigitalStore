package com.mxw.applicationWeb.controller;


import com.mxw.analysis.api.GoodAnalysisService;
import com.mxw.analysis.api.MAnalysisService;
import com.mxw.common.model.vo.ChartResponseVO;
import com.mxw.common.model.vo.RadarDataVO;
import com.mxw.common.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "价值分析", tags = "价值分析", description = "价值分析")
@RestController("/Analysis")
public class MAnalysisController {

    @Reference
    private MAnalysisService mAnalysisService;

    @GetMapping("/getPurchaseAmount")
    @ApiOperation("获取购买金额")
    public Result getPurchaseAmount() {
        String sellerId="2";
        ChartResponseVO chartResponseVO=mAnalysisService.getPurchaseAmount(sellerId);
        return Result.ok().put("data",chartResponseVO);
    }

    @GetMapping("/getConsumptionRanking")
    @ApiOperation("获取消费排名")
    public Result getConsumptionRanking() {
        String sellerId="2";
        ChartResponseVO chartResponseVO=mAnalysisService.getConsumptionRanking(sellerId);
        return Result.ok().put("data",chartResponseVO);
    }

}
