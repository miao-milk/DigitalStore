package com.mxw.applicationWeb.controller;

import com.alibaba.fastjson.JSONObject;
import com.mxw.common.exception.MyException;
import com.mxw.common.model.entity.ShopBuyerDO;
import com.mxw.common.model.vo.LabelVO;
import com.mxw.common.model.vo.MemberConsumptionLevelVO;
import com.mxw.common.model.vo.PageVO;
import com.mxw.common.utils.Result;
import com.mxw.analysis.api.MemberService;;
import io.swagger.annotations.*;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "会员基本属性管理", tags = "会员基本属性管理", description = "会员基本属性管理")
@RestController
public class MemberMessageController {

    @Reference
    private MemberService memberService;

    @GetMapping("/allMember")
    @ApiOperation("查找全部用户")
    public Result queryAllMember() {
        String sellerId="2";
        PageVO<ShopBuyerDO> shopBuyerDTOS = memberService.queryShopBuyer(sellerId);
        return Result.ok("分页条件查找用户成功").put("data", shopBuyerDTOS.getItems()).put("count",shopBuyerDTOS.getCounts());
    }

    @PostMapping("/allMemberByParam")
    @ApiOperation("条件查找用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ShopBuyerDTO", value = "用户查询参数", dataType = "ShopBuyerDTO", paramType = "body"),
            })
    public Result queryAllMemberByParam(@RequestBody String params) {
        ShopBuyerDO shopBuyerDTO = JSONObject.parseObject(params, ShopBuyerDO.class);
        PageVO<ShopBuyerDO> shopBuyerDTOS = memberService.queryShopBuyerByPage(shopBuyerDTO);

        return Result.ok("分页条件查找用户成功").put("data", shopBuyerDTOS.getItems()).put("pageNo", shopBuyerDTOS.getPage()).put("pageSize", shopBuyerDTOS.getPageSize()).put("count",shopBuyerDTOS.getCounts());
    }


    @GetMapping("/getMemberDetail/{shopBuyerId}")
    @ApiOperation("获取会员详情信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shopBuyerId", value = "用户id", required = false, dataType = "String", paramType = "path")
    })
    public Result getMemberDetail(@PathVariable("shopBuyerId") String shopBuyerId) {
        ShopBuyerDO shopByer = memberService.getMemberDetailByShopBuyerId(shopBuyerId);
        return Result.ok("获取用户详情信息成功").put("data", shopByer);
    }

    /**
     * 查询一个用户下的标签
     */
    @ApiOperation("查询会员下的标签集")
    @GetMapping("/queryLabel/{shopBuyerId}")
    public Result queryLabelByshopBuyerId(@ApiParam("用户id") @PathVariable String shopBuyerId) throws MyException {
        List<LabelVO> labelVOS = memberService.queryLabelByshopBuyerId(shopBuyerId);
        return Result.ok("返回用户标签成功").put("data", labelVOS);
    }

    /**
     * 添加一个用户下的标签
     */
    @ApiOperation("添加一个用户下的标签")
    @GetMapping("/addLabel/{shopBuyerId}")
    public Result addLabelByshopBuyerId(@ApiParam("用户id") @PathVariable String shopBuyerId,@ApiParam("标签内容") @RequestParam String labelContent) throws MyException {
        String sellerId="2";
        memberService.addLabelByshopBuyerId(sellerId,shopBuyerId,labelContent);
        return Result.ok("返回用户标签成功");
    }

    /**
     * 删除一个用户下的标签
     */
    @ApiOperation("删除一个用户下的标签")
    @GetMapping("/deleteLabel/{shopBuyerId}")
    public Result deleteLabelByshopBuyerId(@ApiParam("用户id") @PathVariable String shopBuyerId,@ApiParam("标签内容") @RequestParam String labelContent) throws MyException {
        String sellerId="2";
        memberService.deleteLabelByshopBuyerId(sellerId,shopBuyerId,labelContent);
        return Result.ok("删除用户标签成功");
    }

    @GetMapping("/MemberConsumptionLevel")
    @ApiOperation("查找用户消费等级")
    public Result queryMemberConsumptionLevel() {
        String sellerId="2";
        MemberConsumptionLevelVO memberConsumptionLevelVO=memberService.queryMemberConsumptionLevel(sellerId);
        return Result.ok("查找用户消费等级成功").put("data",memberConsumptionLevelVO);
    }

}
