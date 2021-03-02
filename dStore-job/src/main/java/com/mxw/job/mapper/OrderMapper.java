package com.mxw.job.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mxw.common.model.entity.OrderDO;
import com.mxw.common.model.entity.ShopBuyerDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<OrderDO> {
}
