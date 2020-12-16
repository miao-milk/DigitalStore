package com.mxw.member.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mxw.common.model.entity.LabelEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LabelMapper extends BaseMapper<LabelEntity> {

//    @Select(" SELECT label_id,label_name,create_time" +
//            " FROM shop_label" +
//            " WHERE label_id in (SELECT label_id from shop_buyer_label WHERE seller_id=#{sellerId})")
    List<LabelEntity> selectListBySellerId(@Param("sellerId") String sellerId);
}
