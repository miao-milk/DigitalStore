package com.mxw.user.api;


import com.mxw.common.exception.MyException;
import com.mxw.common.model.dto.UserDTO;
import com.mxw.common.model.dto.UserInfoDTO;
import com.mxw.common.model.entity.UserInfoDO;

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

    void saveUserInfo(UserInfoDTO userInfoDTO,String sellerId);

    UserInfoDTO getUserInfo(String sellerId);

    void updateBanlane(String sellerId,Double num);
}
