package com.mxw.applicationWeb.controller;

import com.mxw.analysis.api.MAnalysisService;
import com.mxw.applicationWeb.utils.ShiroUtils;
import com.mxw.common.model.dto.UserDTO;
import com.mxw.common.model.vo.ChartResponseVO;
import com.mxw.common.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Api(value = "价值分析", tags = "价值分析", description = "价值分析")
@RestController("/Analysis")
public class MAnalysisController {

    @Reference
    private MAnalysisService mAnalysisService;
    @Autowired
    ShiroUtils shiroUtils;

    @GetMapping("/getPurchaseAmount")
    @ApiOperation("获取购买金额")
    public Result getPurchaseAmount() {
        UserDTO user = shiroUtils.getUser();
        String sellerId=user.getSellerId();
        ChartResponseVO chartResponseVO=mAnalysisService.getPurchaseAmount(sellerId);
        return Result.ok().put("data",chartResponseVO);
    }

    @GetMapping("/getConsumptionRanking")
    @ApiOperation("获取消费排名")
    public Result getConsumptionRanking() {
        UserDTO user = shiroUtils.getUser();
        String sellerId=user.getSellerId();
        Map<String, Integer> hashMap=mAnalysisService.getConsumptionRanking(sellerId);
        return Result.ok().put("data",hashMap);
    }

}
