package com.mxw.applicationWeb.controller;

import com.mxw.common.enumCode.CommonErrorCode;
import com.mxw.common.exception.MyException;
import com.mxw.common.utils.Result;
import com.mxw.user.api.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@Api(value = "登录退出", tags = "登录退出", description = "登录退出")
@RestController
public class LoginController {


    @PostMapping("/login")
    @ApiOperation("登录接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名", required = true,  dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", required = true,dataType = "String", paramType = "query")
      })
    public Result login(String userName, String password, HttpSession session){
        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        //拿到token
        UsernamePasswordToken token=new UsernamePasswordToken(userName,password);
        //将token进行验证
        try {
            subject.login(token);
            return Result.ok("登录成功");
        }catch (UnknownAccountException e){
            throw new MyException(CommonErrorCode.ERROR_CODE_10002);
        }catch (IncorrectCredentialsException e){
            throw new MyException(CommonErrorCode.ERROR_CODE_10003);
        }
    }

    @GetMapping("/logout")
    @ApiOperation("注册用户")
    public Result logout(){
        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return Result.ok("退出成功");
    }

}
