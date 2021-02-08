package com.mxw.analysis.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mxw.common.model.dto.BuyerLabelDTO;
import com.mxw.common.model.entity.ShopBuyerDO;
import org.apache.ibatis.annotations.Insert;
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
public interface ShopMapper extends BaseMapper<ShopBuyerDO> {

    @Select("SELECT * FROM shop_buyer_label sbl LEFT JOIN shop_label sl ON sbl.label_id = sl.label_id WHERE sbl.shop_buyer_id=#{shopBuyerId}")
    List<BuyerLabelDTO> queryLabelByshopBuyerId(@Param("shopBuyerId") String shopBuyerId);

    @Select("SELECT * from shop_buyer sb  LEFT JOIN shop_buyer_group  sbg ON sb.shop_buyer_id=sbg.shop_buyer_id WHERE sb.seller_id =#{sellerId} AND sbg.group_id=#{id}")
    List<ShopBuyerDO> selectListByshopBuyerId(String id, String sellerId);

    @Insert("INSERT INTO `dStore_member`.`shop_buyer_group` (`id`, `shop_buyer_id`, `group_id`, `create_time`) VALUES (null, #{shopBuyerId}, #{groupId}, NOW())")
    void addGroupMember(String shopBuyerId, String groupId);

    @Select("SELECT count(1) FROM shop_buyer_group WHERE group_id=#{groupId} AND shop_buyer_id=#{shopBuyerId}")
    Integer selectGroup(String shopBuyerId, String groupId);
}
