package com.mxw.member.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mxw.member.model.entity.ShopBuyer;
import org.apache.ibatis.annotations.Mapper;

/**
 * 卖家会员表
 * 
 * @author miaoXiaoWen
 * @email miaoXiaoWen@gmail.com
 * @date 2020-12-10 15:00:54
 */
@Mapper
public interface ShopMapper extends BaseMapper<ShopBuyer> {
	
}
