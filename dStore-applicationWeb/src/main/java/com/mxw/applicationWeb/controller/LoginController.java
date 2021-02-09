package com.mxw.applicationWeb.controller;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.mxw.common.enumCode.CommonErrorCode;
import com.mxw.common.exception.MyException;
import com.mxw.common.model.dto.UserDTO;
import com.mxw.common.model.entity.ShopBuyerDO;
import com.mxw.common.model.vo.UserVO;
import com.mxw.common.utils.Result;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.Serializable;

@Api(value = "登录退出", tags = "登录退出", description = "登录退出")
@RestController
public class LoginController {


    @PostMapping("/login")
    @ApiOperation("登录接口")
    public Result login(@RequestBody @ApiParam(name="用户对象",value="传入json格式",required=true) String params){
        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        UserDTO user = JSONObject.parseObject(params, UserDTO.class);
        String userName = user.getUserName();
        String password = user.getPassword();
        //拿到token
        UsernamePasswordToken token=new UsernamePasswordToken(userName,password);
        //将token进行验证
            try {
                subject.login(token);
                UserVO userVO = new UserVO();
                userVO.setUserName(userName);
                userVO.setToken(String.valueOf(subject.getSession().getId()));
                return Result.ok("登录成功").put("data", userVO);
            } catch (UnknownAccountException e) {
                throw new MyException(CommonErrorCode.ERROR_CODE_10002);
            } catch (IncorrectCredentialsException e) {
                throw new MyException(CommonErrorCode.ERROR_CODE_10003);
            }catch (Exception ex){
                throw new MyException(CommonErrorCode.ERROR_CODE_10010);
            }
    }

    @GetMapping("/logout")
    @ApiOperation("退出用户")
    public Result logout(){
        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return Result.ok("退出成功");
    }

}
