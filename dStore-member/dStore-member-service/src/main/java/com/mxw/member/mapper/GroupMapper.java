package com.mxw.member.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mxw.common.model.entity.ShopGroupDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GroupMapper extends BaseMapper<ShopGroupDO> {
}
