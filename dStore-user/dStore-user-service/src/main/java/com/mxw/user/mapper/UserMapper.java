package com.mxw.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mxw.user.model.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
