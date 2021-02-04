package com.mxw.applicationWeb.controller;

import com.mxw.common.utils.Result;
import com.mxw.member.api.MemberService;
import com.mxw.member.dto.ShopBuyerDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Api(value = "会员等级", tags = "会员等级", description = "会员等级")
@RestController
public class MemberLevelController {

    @Reference
    private MemberService memberService;

    @GetMapping("/queryMemberLevel")
    @ApiOperation("会员等级")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "level", value = "等级类型", dataType = "Integer", paramType = "query"),
           })
    public Result queryMemberLevel(@RequestParam(value = "level",defaultValue = "0") int level){
        Map<Integer, List<ShopBuyerDTO>> stringListMap = memberService.queryMemberLevel(level);
        return Result.ok().put("data",stringListMap);
    }
}
