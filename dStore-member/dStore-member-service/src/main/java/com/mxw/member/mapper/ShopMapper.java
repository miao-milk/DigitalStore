package com.mxw.member.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mxw.common.model.dto.BuyerLabelDTO;
import com.mxw.member.entity.ShopBuyer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 卖家会员表
 * 
 * @author miaoXiaoWen
 * @email miaoXiaoWen@gmail.com
 * @date 2020-12-10 15:00:54
 */
@Mapper
public interface ShopMapper extends BaseMapper<ShopBuyer> {

    @Select("SELECT * FROM shop_buyer_label sbl LEFT JOIN shop_label sl ON sbl.label_id = sl.label_id WHERE sbl.shop_buyer_id=#{shopBuyerId}")
    List<BuyerLabelDTO> queryLabelByshopBuyerId(@Param("shopBuyerId") String shopBuyerId);
}
