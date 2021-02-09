package com.mxw.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mxw.common.enumCode.CommonErrorCode;
import com.mxw.common.exception.MyException;
import com.mxw.common.model.dto.UserDTO;
import com.mxw.common.utils.SaltUtils;
import com.mxw.user.api.UserService;
import com.mxw.user.mapper.UserMapper;
import com.mxw.user.model.entity.User;
import org.apache.dubbo.config.annotation.Service;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public void addUser(UserDTO userDTO) throws MyException{
        //查看用户是否存在
        User one = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUserName, userDTO.getUserName()));
        if (one!=null){
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
        User user = userMapper.selectOne(wrapper.eq("username",userName));
        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
        userDTO.setSalt(user.getSalt());
        return userDTO;
    }
}
