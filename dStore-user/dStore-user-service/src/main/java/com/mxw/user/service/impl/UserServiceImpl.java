package com.mxw.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mxw.common.enumCode.CommonErrorCode;
import com.mxw.common.exception.MyException;
import com.mxw.common.model.dto.UserDTO;
import com.mxw.common.model.dto.UserInfoDTO;
import com.mxw.common.model.entity.UserInfoDO;
import com.mxw.common.utils.SaltUtils;
import com.mxw.user.api.UserService;
import com.mxw.user.mapper.UserInfoMapper;
import com.mxw.user.mapper.UserMapper;
import com.mxw.user.model.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserInfoMapper userInfoMapper;

    @Override
    public void addUser(UserDTO userDTO) throws MyException {
        //查看用户是否存在
        User one = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUserName, userDTO.getUserName()));
        if (one != null) {
            throw new MyException(CommonErrorCode.ERROR_CODE_10004);
        }
        User user = BeanUtil.copyProperties(userDTO, User.class);
        //生成盐
        String salt = SaltUtils.getSalt(8);
        //将随机盐保存到数据中
        user.setSalt(salt);
        //明文加密：md5+salt+hash散列
        Md5Hash md5Hash = new Md5Hash(user.getPassword(), salt, 1024);
        user.setPassword(md5Hash.toHex());
        userMapper.insert(user);
    }

    @Override
    public UserDTO findUserByName(String userName) throws MyException {
        //查看用户是否存在
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        User user = userMapper.selectOne(wrapper.eq("username", userName));
        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
        userDTO.setSellerId(String.valueOf(user.getId()));
        userDTO.setSalt(user.getSalt());
        return userDTO;
    }

    @Override
    public void saveUserInfo(UserInfoDTO userInfoDTO, String sellerId) {
        //首先先验证用户旧密
       boolean res= checkPassword(userInfoDTO,sellerId);
       if (!res){
           throw new MyException(CommonErrorCode.ERROR_CODE_10003);
       }
        //判断是否要修改密码
        if (!StringUtils.isEmpty(userInfoDTO.getNewPassword())) {
            //查询用户
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            User user = userMapper.selectOne(wrapper.eq("id", sellerId));
            //保存原来的盐
            //明文加密：md5+salt+hash散列
            Md5Hash md5Hash = new Md5Hash(user.getPassword(), user.getSalt(), 1024);
            user.setPassword(md5Hash.toHex());
            userMapper.updateById(user);
        } else {
            UserInfoDO userInfo = BeanUtil.copyProperties(userInfoDTO, UserInfoDO.class);
            userInfo.setSellerId(Integer.parseInt(sellerId));
            userInfoMapper.updateById(userInfo);
        }
    }

    @Override
    public UserInfoDTO getUserInfo(String sellerId) {
        UserInfoDO userInfoDO = userInfoMapper.selectById(sellerId);
        UserInfoDTO userInfo = BeanUtil.copyProperties(userInfoDO, UserInfoDTO.class);
        return userInfo;
    }

    @Override
    public void updateBanlane(String sellerId, Double num) {
        UserInfoDO userInfoDO = userInfoMapper.selectById(sellerId);
        Double fee = userInfoDO.getFee();
        userInfoDO.setFee(fee+num);
        int i = userInfoMapper.updateById(userInfoDO);
        System.out.println(i);
    }

    private boolean checkPassword(UserInfoDTO userInfoDTO, String sellerId) {
        //查询用户
        //查询用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        User user = userMapper.selectOne(wrapper.eq("id", sellerId));
        String password = user.getPassword();
        //明文加密：md5+salt+hash散列
        Md5Hash md5Hash = new Md5Hash(userInfoDTO.getOldPassword(), user.getSalt(), 1024);
        String oldPassword = md5Hash.toHex();
        boolean equals = oldPassword.equals(password);
        return equals;
    }

    //验证密码
    public static void main(String[] args) {
        String salt = SaltUtils.getSalt(8);
        //将随机盐保存到数据中
        //明文加密：md5+salt+hash散列
        Md5Hash md5Hash = new Md5Hash("123456", "TQoI&61W", 1024);
        String oldPassword = md5Hash.toHex();
        System.out.println(oldPassword);
    }
}
