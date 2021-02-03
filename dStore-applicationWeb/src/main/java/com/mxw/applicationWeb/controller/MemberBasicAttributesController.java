package com.mxw.applicationWeb.controller;


import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSONObject;
import com.mxw.applicationWeb.listener.ExcelListener;
import com.mxw.common.model.entity.ShopBuyerDetail;
import com.mxw.common.model.vo.PageVO;
import com.mxw.common.utils.Result;
import com.mxw.member.api.MemberService;
import com.mxw.member.dto.ShopBuyerDTO;
import io.swagger.annotations.*;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Api(value = "会员基本属性管理", tags = "会员基本属性管理", description = "会员基本属性管理")
@RestController
public class MemberBasicAttributesController {

    @Reference
    private MemberService memberService;

    @GetMapping("/allMember")
    @ApiOperation("查找全部用户")
    public Result queryAllMember() {
        PageVO<ShopBuyerDTO> shopBuyerDTOS = memberService.queryShopBuyer();

        return Result.ok("分页条件查找用户成功").put("data", shopBuyerDTOS.getItems()).put("count",shopBuyerDTOS.getCounts());
    }

    @PostMapping("/allMemberByParam")
    @ApiOperation("分页条件查找用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ShopBuyerDTO", value = "用户查询参数", dataType = "ShopBuyerDTO", paramType = "body"),
            })
    public Result queryAllMemberByParam(@RequestBody String params) {
        ShopBuyerDTO shopBuyerDTO = JSONObject.parseObject(params, ShopBuyerDTO.class);
        PageVO<ShopBuyerDTO> shopBuyerDTOS = memberService.queryShopBuyerByPage(shopBuyerDTO);

        return Result.ok("分页条件查找用户成功").put("data", shopBuyerDTOS.getItems()).put("pageNo", shopBuyerDTOS.getPage()).put("pageSize", shopBuyerDTOS.getPageSize()).put("count",shopBuyerDTOS.getCounts());
    }

    @DeleteMapping("/addBlackList/{sellerId}")
    @ApiOperation("添加黑名单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sellerId", value = "用户id", required = false, dataType = "String", paramType = "path")
    })
    public Result addBlackList(@PathVariable("sellerId") String sellerId) {

        memberService.addBlackList(sellerId);
        return Result.ok("添加黑名单成功");
    }

    @GetMapping("/getMemberDetail/{shopBuyerId}")
    @ApiOperation("获取用户详情信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shopBuyerId", value = "用户id", required = false, dataType = "String", paramType = "path")
    })
    public Result getMemberDetail(@PathVariable("shopBuyerId") String shopBuyerId) {
        ShopBuyerDetail shopByer = memberService.getMemberDetailByShopBuyerId(shopBuyerId);
        return Result.ok("获取用户详情信息成功").put("data", shopByer);
    }


    @PutMapping("/modifyMemberDetail")
    @ApiOperation("修改用户详情信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ShopBuyerDetail", value = "用户查询参数", dataType = "ShopBuyerDetail", paramType = "body")
    })
    public Result modifyMemberDetail(@RequestBody ShopBuyerDetail shopBuyerDetail) {
        memberService.modifyMemberDetail(shopBuyerDetail);
        return Result.ok("修改用户详情信息成功");
    }

    @PutMapping("/addMemberDetail")
    @ApiOperation("手动添加用户详情信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ShopBuyerDetail", value = "用户查询参数", dataType = "ShopBuyerDetail", paramType = "body")
    })
    public Result addMemberDetail(@RequestBody ShopBuyerDetail shopBuyerDetail) {
        memberService.addMemberDetail(shopBuyerDetail);
        return Result.ok("手动添加用户详情信息成功");
    }

    @PostMapping("/uploadMembers")
    @ApiOperation("上传会员信息")
    public Result uploadMembers(@ApiParam(value = "用户个人信息", required = true) @RequestParam("files") MultipartFile multipartFile) throws IOException {

        //生成文件名称，保证文件的唯一性
        String originalFilename = multipartFile.getOriginalFilename();
        //扩展名
        String substring = originalFilename.substring(originalFilename.lastIndexOf('.') - 1);
        //文件名称
        String fileName = UUID.randomUUID() + substring;

        InputStream inputStream = multipartFile.getInputStream();
        //实例化实现了AnalysisEventListener接口的类
        ExcelListener listener = new ExcelListener();
        //传入参数
        ExcelReader excelReader = new ExcelReader(inputStream, ExcelTypeEnum.XLS, null, listener);
        //读取信息
        excelReader.read(new Sheet(1, 1, ShopBuyerDetail.class));
        //获取数据
        List<Object> list = listener.getDatas();
        memberService.uploadMembers(list);
        return Result.ok();
    }

}
