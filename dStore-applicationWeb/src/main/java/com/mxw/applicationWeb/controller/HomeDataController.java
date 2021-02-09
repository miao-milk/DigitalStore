package com.mxw.applicationWeb.controller;

import com.mxw.analysis.api.GroupService;
import com.mxw.analysis.api.MemberService;
import com.mxw.analysis.api.TradeService;
import com.mxw.common.model.dto.NewTradeUsersDTO;
import com.mxw.common.model.dto.OrderDTO;
import com.mxw.common.model.dto.SalesDTO;
import com.mxw.common.model.entity.AreaDataDO;
import com.mxw.common.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(value = "首页", tags = "首页", description = "首页")
@RestController("/home")
public class HomeDataController {

    @Reference
    private TradeService tradeService;


    /**
     * 查询累计销售额
     */
    @GetMapping("/totalSales")
    @ApiOperation("查询累计销售额")
    public Result getTotalSales() {
        String sellerId = "2";
        SalesDTO salesDTO = tradeService.getTotalSales(sellerId);
        return Result.ok("查询累计销售额成功").put("data", salesDTO);
    }

    /**
     * 查询累计订单量
     */
    @GetMapping("/totalOrders")
    @ApiOperation("查询累计订单量")
    public Result getTotalOrders() {
        String sellerId = "2";
        OrderDTO orderDTO=tradeService.getTotalOrders(sellerId);
        return Result.ok("查询累计订单量成功").put("data",orderDTO);
    }

    /**
     * 查询今日新增交易用户数
     */
    @GetMapping("/todayUsers")
    @ApiOperation("查询今日新增交易用户数")
    public Result getTodayUsers() {
        String sellerId = "2";
        NewTradeUsersDTO newTradeUsersDTO=tradeService.getTodayUsers(sellerId);
        return Result.ok("查询今日新增交易用户数成功").put("data",newTradeUsersDTO);
    }

    /**
     * 查询累计用户数
     */
    @GetMapping("/totalUsers")
    @ApiOperation("查询累计用户数")
    public Result getTotalUsers() {
        return Result.ok("查询累计用户数成功");
    }

    /**
     * 查询地图数据
     */
    @GetMapping("/mapData")
    @ApiOperation("查询地图数据")
    public Result getMapData() {
       String sellerId="2";
       List<AreaDataDO> areaDataDOS= tradeService.getMapData(sellerId);
       return Result.ok("查询地图数据成功").put("data",areaDataDOS);
    }
}
