package com.mxw.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mxw.common.model.entity.UserInfoDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfoDO> {
}
