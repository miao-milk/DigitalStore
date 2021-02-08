package com.mxw.applicationWeb.controller;


import com.mxw.common.model.entity.ShopBuyerDO;
import com.mxw.common.model.vo.GroupDetailVO;
import com.mxw.common.model.vo.GroupVO;
import com.mxw.common.utils.Result;
import com.mxw.analysis.api.GroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "会员分组", tags = "会员分组", description = "会员分组")
@RestController("/group")
public class MemberGroupController {

    @Reference
    private GroupService groupService;

    /**
     * 查询分组列表
     */
    @GetMapping("/groupTree")
    @ApiOperation("查询分组列表")
    public Result getGroupTree() {
        //查询该用户下的分组列表
        String sellerId="2";
        List<GroupVO> labelList = groupService.getGroupTree(sellerId);
        return Result.ok("查询标签库成功").put("data", labelList).put("count",labelList.size());
    }

    /**
     * 添加分组
     */
    @ApiOperation("添加分组内容")
    @GetMapping("/addGroup")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "content", value = "分组名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pid", value = "父id", dataType = "String", paramType = "query")
    })
    public Result addGroup(@RequestParam String content, @RequestParam String pid) {
        String sellerId="2";
        groupService.addGroup(sellerId,content,pid);
        return Result.ok("添加成功");
    }

    /**
     * 添加分组
     */
    @ApiOperation("修改分组内容")
    @GetMapping("/editGroup")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "content", value = "分组名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "id", dataType = "String", paramType = "query")
    })
    public Result editGroup(@RequestParam String content, @RequestParam String id) {
        String sellerId="2";
        groupService.editGroup(sellerId,content,id);
        return Result.ok("修改成功");
    }


    /**
     * 添加分组
     */
    @ApiOperation("删除分组内容")
    @GetMapping("/deleteGroup")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "id", value = "id", dataType = "String", paramType = "query")
    })
    public Result deleteGroup(@RequestParam String id) {
        String sellerId="2";
        groupService.deleteGroup(sellerId,id);
        return Result.ok("删除成功");
    }


    /**
     * 查询分组列表
     */
    @GetMapping("/groupMember")
    @ApiOperation("查询分组人群列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "String", paramType = "query")
    })
    public Result getGroupMember(@RequestParam String id) {
        //查询该用户下的分组列表
        String sellerId="2";
        List<ShopBuyerDO> labelList = groupService.getGroupMember(sellerId,id);
        return Result.ok("查询分组人群列表成功").put("data", labelList).put("count",labelList.size());
    }

    /**
     * 人群列表添加会员
     */
    @GetMapping("/addGroupMember")
    @ApiOperation("人群列表添加会员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupId", value = "contentId", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "shopBuyerId", value = "shopBuyerId", dataType = "String", paramType = "query")
    })
    public Result addGroupMember(@RequestParam String groupId,@RequestParam String shopBuyerId) {
        //查询该用户下的分组列表
        groupService.addGroupMember(groupId,shopBuyerId);
        return Result.ok("添加会员成功");
    }

    /**
     * 查询分组列表
     */
    @GetMapping("/getGroupDetail")
    @ApiOperation("查询分组详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "String", paramType = "query")
    })
    public Result getGroupDetail(@RequestParam String id) {
        //查询该用户下的分组列表
        String sellerId="2";
        GroupDetailVO groupDetailVO= groupService.getGroupDetail(sellerId,id);
        return Result.ok("查询标签库成功").put("data", groupDetailVO);
    }

}
