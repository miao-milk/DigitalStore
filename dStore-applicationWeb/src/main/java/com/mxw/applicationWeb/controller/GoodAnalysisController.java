package com.mxw.applicationWeb.controller;

import com.mxw.analysis.api.GoodAnalysisService;

import com.mxw.common.model.vo.ChartResponseVO;
import com.mxw.common.model.vo.RadarDataVO;
import com.mxw.common.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "商品分析", tags = "商品分析", description = "商品分析")
@RestController("/goodsAnalysis")
public class GoodAnalysisController {

    @Reference
    private GoodAnalysisService goodAnalysisService;

    @GetMapping("/getProductSalesCharacteristics")
    @ApiOperation("获取商品销售特征雷达数据喜爱度")
    public Result getProductSalesCharacteristics() {
        String sellerId="2";
        RadarDataVO radarDataVO=goodAnalysisService.getProductSalesCharacteristics(sellerId);
        return Result.ok().put("data",radarDataVO);
    }

    @GetMapping("/getCommoditySales")
    @ApiOperation("获取商品销售额数据")
    public Result getCommoditySales() {
        String sellerId="2";
        ChartResponseVO chartResponseVO=goodAnalysisService.getCommoditySales(sellerId);
        return Result.ok().put("data",chartResponseVO);
    }

}
