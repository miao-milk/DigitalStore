package com.mxw.user.api;


import com.mxw.common.exception.MyException;
import com.mxw.common.model.dto.UserDTO;

/**
 * 平台用户管理
 */
public interface UserService {

    /**
     * 注册
     */
    void addUser(UserDTO user) throws MyException;

    /**
     * 通过系统用户名查询用户
     */
    UserDTO findUserByName(String userName) throws MyException;
}
