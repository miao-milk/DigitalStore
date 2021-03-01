package com.mxw.applicationWeb.controller;


import com.alibaba.fastjson.JSONObject;
import com.mxw.analysis.api.MemberService;
import com.mxw.applicationWeb.utils.ShiroUtils;
import com.mxw.common.model.dto.UserDTO;
import com.mxw.common.model.entity.MessageDO;
import com.mxw.common.model.entity.ShopBuyerDO;
import com.mxw.common.model.vo.PageVO;
import com.mxw.common.utils.Result;
import com.mxw.message.api.SendMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "会员基本属性管理", tags = "会员基本属性管理", description = "会员基本属性管理")
@RestController
public class SendMessageController {
    @Reference
    private SendMessageService sendMessageService;
    @Autowired
    ShiroUtils shiroUtils;


    @GetMapping("/getAllMessage")
    @ApiOperation("查找全部信息记录")
    public Result getAllMessage() {
        UserDTO user = shiroUtils.getUser();
        String sellerId=user.getSellerId();
        PageVO<MessageDO> allMessageRecord = sendMessageService.getAllMessageRecord(sellerId);
        return Result.ok("分页条件查找信息成功").put("data", allMessageRecord.getItems()).put("count",allMessageRecord.getCounts());
    }


    @PostMapping("/allMessageByParam")
    @ApiOperation("条件查找信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "MessageDO", value = "信息查询参数", dataType = "MessageDO", paramType = "body"),
    })
    public Result allMessageByParam(@RequestBody String params) {
        MessageDO messageDO = JSONObject.parseObject(params, MessageDO.class);
        PageVO<MessageDO> messageDOS = sendMessageService.queryAllMemberByParam(messageDO);

        return Result.ok("分页条件查找信息成功").put("data", messageDOS.getItems())
                .put("pageNo", messageDOS.getPage()).put("pageSize", messageDOS.getPageSize())
                .put("count",messageDOS.getCounts());
    }


    @PostMapping("/sendMessage")
    @ApiOperation("发送信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "MessageDO", value = "信息查询参数", dataType = "MessageDO", paramType = "body"),
    })
    public Result sendMessage(@RequestBody String params) {
        MessageDO messageDO = JSONObject.parseObject(params, MessageDO.class);
        Boolean aBoolean = sendMessageService.sendMessage(messageDO);

      if (aBoolean){
          return Result.ok("发送信息成功");
      }else {
          return Result.error("发送信息失败");
      }
    }
}
