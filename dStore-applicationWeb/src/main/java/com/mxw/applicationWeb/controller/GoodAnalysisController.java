package com.mxw.applicationWeb.controller;

import com.mxw.analysis.api.GoodAnalysisService;

import com.mxw.applicationWeb.filter.ShiroLoginFilter;
import com.mxw.applicationWeb.utils.ShiroUtils;
import com.mxw.common.model.dto.UserDTO;
import com.mxw.common.model.vo.ChartResponseVO;
import com.mxw.common.model.vo.RadarDataVO;
import com.mxw.common.utils.Result;
import com.mxw.user.api.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "商品分析", tags = "商品分析", description = "商品分析")
@RestController("/goodsAnalysis")
public class GoodAnalysisController {

    @Reference
    private GoodAnalysisService goodAnalysisService;
    @Reference
    UserService userService;

    @Autowired
    ShiroUtils shiroUtils;

    @GetMapping("/getProductSalesCharacteristics")
    @ApiOperation("获取商品销售特征雷达数据")
    public Result getProductSalesCharacteristics() {
        UserDTO user = shiroUtils.getUser();
        String sellerId=user.getSellerId();
        RadarDataVO radarDataVO=goodAnalysisService.getProductSalesCharacteristics(sellerId);
        return Result.ok().put("data",radarDataVO);
    }

    @GetMapping("/getCommoditySales")
    @ApiOperation("获取商品销售额数据")
    public Result getCommoditySales() {
        UserDTO user = shiroUtils.getUser();
        String sellerId=user.getSellerId();
        ChartResponseVO chartResponseVO=goodAnalysisService.getCommoditySales(sellerId);
        return Result.ok().put("data",chartResponseVO);
    }

}
