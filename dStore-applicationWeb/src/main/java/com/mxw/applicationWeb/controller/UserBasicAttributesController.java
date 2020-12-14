package com.mxw.applicationWeb.controller;


import com.mxw.common.model.vo.PageVO;
import com.mxw.common.utils.ResponseUtils;
import com.mxw.member.api.MemberService;
import com.mxw.member.dto.ShopBuyerDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "会员基本属性管理", tags = "会员基本属性管理", description = "会员基本属性管理")
@RestController("/member")
public class UserBasicAttributesController {

    @DubboReference
    private MemberService memberService;

    @PostMapping("/allMember")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "shopBuyerDTO", value = "用户查询参数", dataType = "shopBuyerDTO", paramType = "body"),
    @ApiImplicitParam(name = "pageNo", value = "页码", required =true,defaultValue ="0",dataType = "int", paramType = "query"),
    @ApiImplicitParam(name = "pageSize", value = "每页记录数", required =true,defaultValue ="10",dataType = "int", paramType = "query")
    })
    public ResponseUtils queryAllMember(@RequestBody ShopBuyerDTO shopBuyerDTO, @RequestParam Integer pageNo, @RequestParam Integer pageSize) {

        PageVO<ShopBuyerDTO> shopBuyerDTOS = memberService.queryShopBuyerByPage(shopBuyerDTO, pageNo, pageSize);

        return ResponseUtils.ok().put("data", shopBuyerDTOS.getItems()).put("pageNo", shopBuyerDTOS.getPage()).put("pageSize", shopBuyerDTOS.getPageSize());
    }
}
