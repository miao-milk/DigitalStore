package com.mxw.applicationWeb.controller;

import com.mxw.common.model.dto.UserDTO;
import com.mxw.common.utils.Result;
import com.mxw.user.api.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Api(value = "系统用户管理", tags = "系统用户管理", description = "系统用户管理")
@RestController
public class SysUserController {

    @Reference
    UserService userService;

    @PostMapping("/register")
    @ApiOperation("注册用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "UserDTO", value = "用户信息", dataType = "UserDTO", paramType = "body"),
    })
    public Result register(@RequestBody UserDTO userDTO){
        userService.addUser(userDTO);
        return Result.ok("注册成功");
    }

}
