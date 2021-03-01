package com.mxw.applicationWeb.controller;

import com.alibaba.fastjson.JSONObject;
import com.mxw.applicationWeb.utils.ShiroUtils;
import com.mxw.common.model.dto.UserDTO;
import com.mxw.common.model.entity.LabelDO;
import com.mxw.common.utils.Result;
import com.mxw.analysis.api.LabelService;
import io.swagger.annotations.*;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "会员标签管理会员等级", tags = "会员标签管理", description = "会员标签管理")
@RestController("/label")
public class MemberLabelController {

    @Reference
    private LabelService labelService;
    @Autowired
    ShiroUtils shiroUtils;

    /**
     * 查询该用户下标签库列表
     */
    @GetMapping("/allLabel")
    @ApiOperation("查询标签库列表")
    public Result allLabel() {
        UserDTO user = shiroUtils.getUser();
        String sellerId=user.getSellerId();
        List<LabelDO> labelList = labelService.queryallLabel(sellerId);
        return Result.ok("查询标签库成功").put("data", labelList).put("count",labelList.size());
    }


    @PostMapping("/allLabelByParam")
    @ApiOperation("条件查找标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "LabelDO", value = "用户查询参数", dataType = "LabelDO", paramType = "body"),
    })
    public Result queryAllMemberByParam(@RequestBody String params) {
        LabelDO labelDO = JSONObject.parseObject(params, LabelDO.class);
        List<LabelDO> labelList = labelService.queryLabelByParams(labelDO);
        return Result.ok("查询标签库成功").put("data", labelList).put("count",labelList.size());
    }


    /**
     * 批量删除标签库列表
     */
    @PostMapping("/deleteLabel")
    @ApiOperation("删除标签")
    public Result deleteLabel(@RequestBody String parms) {
        String[] ids = JSONObject.parseObject(parms, String[].class);
        labelService.deleteLabel(ids);
        return Result.ok("删除标签成功");
    }

    /**
     * 新增标签到标签库内容
     */
    @ApiOperation("新增标签到标签库内容")
    @GetMapping("/addLabel")
    public Result addLabel(@ApiParam("标签内容") @RequestParam String labelContent) {
        UserDTO user = shiroUtils.getUser();
        String sellerId=user.getSellerId();
        labelService.addLabel(sellerId,labelContent);
        return Result.ok("新增标签成功");
    }
}
