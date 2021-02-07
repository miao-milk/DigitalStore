package com.mxw.applicationWeb.controller;

import com.mxw.common.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(value = "首页", tags = "首页", description = "首页")
@RestController("/home")
public class HomeDataController {

    /**
     * 查询累计销售额
     */
    @GetMapping("/totalSales")
    @ApiOperation("查询累计销售额")
    public Result getTotalSales() {
        return Result.ok("查询累计销售额成功");
    }
    /**
     * 查询累计订单量
     */
    @GetMapping("/totalOrders")
    @ApiOperation("查询累计订单量")
    public Result getTotalOrders() {
        return Result.ok("查询累计订单量成功");
    }
    /**
     * 查询今日新增交易用户数
     */
    @GetMapping("/todayUsers")
    @ApiOperation("查询今日新增交易用户数")
    public Result getTodayUsers() {
        return Result.ok("查询今日新增交易用户数成功");
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
        return Result.ok("查询地图数据成功");
    }
}
