package com.mxw.applicationWeb.controller;

import com.mxw.common.exception.MyException;
import com.mxw.common.model.entity.LabelEntity;
import com.mxw.common.utils.Result;
import com.mxw.member.api.LabelService;
import io.swagger.annotations.*;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "会员标签管理", tags = "会员标签管理", description = "会员标签管理")
@RestController("/label")
public class UserLabelController {

    @Reference
    private LabelService labelService;

    /**
     * 查询标签库列表
     */
    @GetMapping("/allLabel")
    @ApiOperation("查询标签库列表")
    public Result allLabel() {
        List<LabelEntity> labelList = labelService.queryallLabel();
        return Result.ok("查询标签库成功").put("data", labelList);
    }

    /**
     * 新增标签到标签库内容
     */
    @ApiOperation("新增标签到标签库内容")
    @GetMapping("/addLabel")
    public Result addLabel(@ApiParam("标签内容") @RequestParam String labelContent) {
        labelService.addLabel(labelContent);
        return Result.ok("新增标签成功");
    }

    /**
     * 查询一个用户下的标签
     */
    @ApiOperation("查询一个用户下的标签")
    @GetMapping("/queryLabel/{sellerId}")
    public Result queryLabelBySellerId(@ApiParam("用户id") @PathVariable String sellerId) throws MyException {
        List<LabelEntity> oweLabelList = labelService.queryLabelBySellerId(sellerId);
        return Result.ok("返回用户标签成功").put("data", oweLabelList);
    }

    /**
     * 为一个用户添加标签内容
     */
    @ApiOperation("为一个用户添加标签内容")
    @GetMapping("/addSellerLabel")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sellerId", value = "用户id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "labelId", value = "标签id", dataType = "String", paramType = "query")
    })
    public Result addLabelBySellerId(@RequestParam String sellerId, @RequestParam String labelId) throws MyException {
        labelService.addLabelBySellerId(sellerId, labelId);
        return Result.ok("添加用户标签成功");
    }

    /**
     * 为一个用户去除标签内容
     */
    @ApiOperation("为一个用户去除标签内容")
    @GetMapping("/deleteSellerLabel")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sellerId", value = "用户id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "labelId", value = "标签id", dataType = "String", paramType = "query")
    })
    public Result deleteLabelBySellerId(@RequestParam String sellerId, @RequestParam String labelId) throws MyException {
        labelService.deleteLabelBySellerId(sellerId, labelId);
        return Result.ok("移除用户标签成功");
    }

}
