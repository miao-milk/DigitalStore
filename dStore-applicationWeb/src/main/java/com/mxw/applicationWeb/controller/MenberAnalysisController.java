package com.mxw.applicationWeb.controller;


import com.mxw.analysis.api.MenberAnalysisService;
import com.mxw.applicationWeb.utils.ShiroUtils;
import com.mxw.common.model.dto.UserDTO;
import com.mxw.common.model.entity.ShopBuyerDO;
import com.mxw.common.model.vo.ChartAnalysisVO;
import com.mxw.common.model.vo.ChartResponseVO;
import com.mxw.common.model.vo.NewOldBuyerCompareVO;
import com.mxw.common.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "全链路用户分析", tags = "全链路用户分析", description = "全链路用户分析")
@RestController("/menberAnalysis")
public class MenberAnalysisController {

    @Reference
    private MenberAnalysisService menberAnalysisService;
    @Autowired
    ShiroUtils shiroUtils;

    @GetMapping("/getNumberOfMembers")
    @ApiOperation("获取会员数量走势数据")
    public Result getNumberOfMembers() {
        UserDTO user = shiroUtils.getUser();
        String sellerId=user.getSellerId();
        ChartResponseVO chartResponseVO=menberAnalysisService.getNumberOfMembers(sellerId);
        return Result.ok().put("data",chartResponseVO);
    }

    @GetMapping("/getLevelMembershipChanges")
    @ApiOperation("获取等级会员变化图数据")
    public Result getLevelMembershipChanges() {
        UserDTO user = shiroUtils.getUser();
        String sellerId=user.getSellerId();
        //普通会员 中级会员 高级会员
        ChartResponseVO chartResponseVO=menberAnalysisService.getLevelMembershipChanges(sellerId);
        return Result.ok().put("data",chartResponseVO);
    }

    @GetMapping("/getNewAndOldMembers")
    @ApiOperation("获取新老会员对比图数据")
    public Result getNewAndOldMembers() {
        UserDTO user = shiroUtils.getUser();
        String sellerId=user.getSellerId();
        //普通会员 中级会员 高级会员
        List<NewOldBuyerCompareVO> NewOldBuyerCompareListDate=menberAnalysisService.getNewAndOldMembers(sellerId);
        return Result.ok().put("data",NewOldBuyerCompareListDate);
    }

    @GetMapping("/getChartAnalysisResult/{analysisType}")
    @ApiOperation("获取图表解析信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "analysisType", value = "图表类型", required = false, dataType = "String", paramType = "path")
    })
    public Result getChartAnalysisResult(@PathVariable("analysisType") String analysisType) {
        UserDTO user = shiroUtils.getUser();
        String sellerId=user.getSellerId();
        ChartAnalysisVO chartAnalysisResult = menberAnalysisService.getChartAnalysisResult(analysisType,sellerId);
        return Result.ok("获取用户详情信息成功").put("data", chartAnalysisResult);
    }

}
