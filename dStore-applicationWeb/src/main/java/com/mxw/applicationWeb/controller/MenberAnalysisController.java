package com.mxw.applicationWeb.controller;


import com.mxw.analysis.api.MenberAnalysisService;
import com.mxw.common.model.vo.ChartResponseVO;
import com.mxw.common.model.vo.NewOldBuyerCompareVO;
import com.mxw.common.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "全链路用户分析", tags = "全链路用户分析", description = "全链路用户分析")
@RestController("/menberAnalysis")
public class MenberAnalysisController {

    @Reference
    private MenberAnalysisService menberAnalysisService;

    @GetMapping("/getNumberOfMembers")
    @ApiOperation("获取会员数量走势数据")
    public Result getNumberOfMembers() {
        String sellerId="2";
        ChartResponseVO chartResponseVO=menberAnalysisService.getNumberOfMembers(sellerId);
        return Result.ok().put("data",chartResponseVO);
    }

    @GetMapping("/getLevelMembershipChanges")
    @ApiOperation("获取等级会员变化图数据")
    public Result getLevelMembershipChanges() {
        String sellerId="2";
        //普通会员 中级会员 高级会员
        ChartResponseVO chartResponseVO=menberAnalysisService.getLevelMembershipChanges(sellerId);
        return Result.ok().put("data",chartResponseVO);
    }

    @GetMapping("/getNewAndOldMembers")
    @ApiOperation("获取新老会员对比图数据")
    public Result getNewAndOldMembers() {
        String sellerId="2";
        //普通会员 中级会员 高级会员
        List<NewOldBuyerCompareVO> NewOldBuyerCompareListDate=menberAnalysisService.getNewAndOldMembers(sellerId);
        return Result.ok().put("data",NewOldBuyerCompareListDate);
    }

}
