package com.mxw.member.api;

import com.mxw.common.model.entity.ShopBuyerDO;
import com.mxw.common.model.vo.LabelVO;
import com.mxw.common.model.vo.MemberConsumptionLevelVO;
import com.mxw.common.model.vo.PageVO;


import java.util.List;


/**
 * 用户基本属性操作
 */
public interface MemberService {

    PageVO<ShopBuyerDO> queryShopBuyer(String sellerId);

    PageVO<ShopBuyerDO> queryShopBuyerByPage(ShopBuyerDO shopBuyerDTO);

    ShopBuyerDO getMemberDetailByShopBuyerId(String shopBuyerId);

    List<LabelVO> queryLabelByshopBuyerId(String shopBuyerId);

    MemberConsumptionLevelVO queryMemberConsumptionLevel(String sellerId);

    void addLabelByshopBuyerId(String sellerId,String shopBuyerId, String labelContent);

    void deleteLabelByshopBuyerId(String sellerId, String shopBuyerId, String labelContent);
}
