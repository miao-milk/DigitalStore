package com.mxw.applicationWeb.controller;


import com.alibaba.fastjson.JSONObject;
import com.mxw.common.model.entity.MessageDO;
import com.mxw.common.utils.Result;
import com.mxw.message.api.SendMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.dubbo.config.annotation.Reference;
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
}
