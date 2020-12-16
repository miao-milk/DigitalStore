package com.mxw.member.controller;

import com.mxw.common.model.param.MemberParam;
import com.mxw.common.model.vo.PageVO;
import com.mxw.common.utils.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;

/**
 * 卖家会员表
 *
 * @author miaoXiaoWen
 * @email miaoXiaoWen@gmail.com
 * @date 2020-12-10 15:00:54
 */
@Api(value = "会员用户-管理服务",tags = "会员用户-管理服务",description = "会员用户-管理服务")
@RestController
@RequestMapping("dStore/shop")
public class ShopController {

    @Autowired
    ShopService shopService;

    /**
     * 列表
     */
    @PostMapping("/list")
    @ApiImplicitParams({@ApiImplicitParam(name = "memberParam",value = "查询用户参数",required = true,dataType = "MemberParam",paramType = "body")})
    public ResponseUtils list(@RequestBody MemberParam memberParam){

        PageVO page = shopService.queryPage(memberParam);
        return ResponseUtils.ok().put("page", page);
    }

}
