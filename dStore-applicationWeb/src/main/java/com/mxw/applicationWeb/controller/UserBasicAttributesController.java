package com.mxw.applicationWeb.controller;


import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.mxw.applicationWeb.listener.ExcelListener;
import com.mxw.common.model.entity.ShopBuyerDetail;
import com.mxw.common.model.vo.PageVO;
import com.mxw.common.utils.ResponseUtils;
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
@RestController("/member")
public class UserBasicAttributesController {

    @Reference
    private MemberService memberService;

    @PostMapping("/allMember")
    @ApiOperation("分页条件查找用户")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "ShopBuyerDTO", value = "用户查询参数", dataType = "ShopBuyerDTO", paramType = "body"),
    @ApiImplicitParam(name = "pageNo", value = "页码", required =true,defaultValue ="0",dataType = "int", paramType = "query"),
    @ApiImplicitParam(name = "pageSize", value = "每页记录数", required =true,defaultValue ="10",dataType = "int", paramType = "query")
    })
    public ResponseUtils queryAllMember(@RequestBody ShopBuyerDTO shopBuyerDTO, @RequestParam Integer pageNo, @RequestParam Integer pageSize) {

        PageVO<ShopBuyerDTO> shopBuyerDTOS = memberService.queryShopBuyerByPage(shopBuyerDTO, pageNo, pageSize);

        return ResponseUtils.ok().put("data", shopBuyerDTOS.getItems()).put("pageNo", shopBuyerDTOS.getPage()).put("pageSize", shopBuyerDTOS.getPageSize());
    }

    @DeleteMapping("/addBlackList/{sellerId}")
    @ApiOperation("添加黑名单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sellerId",value = "用户id",required = false,dataType = "String",paramType ="path")
    })
    public ResponseUtils addBlackList(@PathVariable("sellerId")String sellerId){

        memberService.addBlackList(sellerId);
        return ResponseUtils.ok();
    }

    @GetMapping("/getMemberDetail/{sellerId}")
    @ApiOperation("获取用户详情信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sellerId",value = "用户id",required = false,dataType = "String",paramType ="path")
    })
    public ResponseUtils getMemberDetail(@PathVariable("sellerId")String sellerId){
        ShopBuyerDetail shopByer=memberService.getMemberDetailBySellerId(sellerId);
        return ResponseUtils.ok().put("data",shopByer);
    }


    @PutMapping("/modifyMemberDetail")
    @ApiOperation("修改用户详情信息")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "ShopBuyerDetail", value = "用户查询参数", dataType = "ShopBuyerDetail", paramType = "body")
    })
    public ResponseUtils modifyMemberDetail(@RequestBody ShopBuyerDetail shopBuyerDetail){
        memberService.modifyMemberDetail(shopBuyerDetail);
        return ResponseUtils.ok();
    }

    @PutMapping("/addMemberDetail")
    @ApiOperation("手动添加用户详情信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ShopBuyerDetail", value = "用户查询参数", dataType = "ShopBuyerDetail", paramType = "body")
    })
    public ResponseUtils addMemberDetail(@RequestBody ShopBuyerDetail shopBuyerDetail){
        memberService.addMemberDetail(shopBuyerDetail);
        return ResponseUtils.ok();
    }

    @PostMapping("/uploadMembers")
    @ApiOperation("上传会员信息")
    public ResponseUtils uploadMembers(@ApiParam(value = "用户个人信息",required = true) @RequestParam("files") MultipartFile multipartFile) throws IOException {

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
        return ResponseUtils.ok();
    }

}
