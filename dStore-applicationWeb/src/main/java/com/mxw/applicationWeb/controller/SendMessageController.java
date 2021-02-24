package com.mxw.applicationWeb.controller;


import com.alibaba.fastjson.JSONObject;
import com.mxw.common.model.entity.MessageDO;
import com.mxw.common.model.entity.ShopBuyerDO;
import com.mxw.common.model.vo.PageVO;
import com.mxw.common.utils.Result;
import com.mxw.message.api.SendMessageService;
import io.swagger.annotations.*;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "发送短信", tags = "发送短信", description = "发送短信")
@RestController
public class SendMessageController {

    @Reference
    private SendMessageService sendMessageService;

    @PostMapping("/sendMessage")
    @ApiOperation("发送短信")
    public Result sendMessage(@RequestBody @ApiParam(name = "用户对象", value = "传入json格式", required = true) String params) {
        //获取主体对象
        MessageDO messageDO = JSONObject.parseObject(params, MessageDO.class);
        Boolean res=sendMessageService.sendMessage(messageDO);
        return Result.ok("登录成功").put("data", "发送成功");
    }


    @GetMapping("/getAllMessageRecord")
    @ApiOperation("查找全部短信记录")
    public Result getAllMessageRecord() {
        String sellerId="2";
        PageVO<MessageDO> messageDOS = sendMessageService.getAllMessageRecord(sellerId);
        return Result.ok("查找短信记录成功").put("data", messageDOS.getItems()).put("count",messageDOS.getCounts());
    }

    @PostMapping("/getAllMessageRecordByParam")
    @ApiOperation("条件查找短信记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "MessageDO", value = "用户查询参数", dataType = "MessageDO", paramType = "body"),
    })
    public Result queryAllMemberByParam(@RequestBody String params) {
        MessageDO messageDO = JSONObject.parseObject(params, MessageDO.class);
        PageVO<MessageDO> messageDOS= sendMessageService.queryAllMemberByParam(messageDO);

        return Result.ok("分页条件查找短信成功").put("data", messageDOS.getItems()).put("pageNo", messageDOS.getPage())
                .put("pageSize", messageDOS.getPageSize()).put("count",messageDOS.getCounts());
    }
}
