package com.mxw.applicationWeb.utils;

import com.mxw.common.model.dto.UserDTO;
import com.mxw.user.api.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

@Component
public class ShiroUtils {

    @Reference
    UserService userService;

    public UserDTO getUser(){
        Subject subject = SecurityUtils.getSubject();
        String principal =  (String)subject.getPrincipal();
        UserDTO user = userService.findUserByName(principal);

        return user;
    }
}
