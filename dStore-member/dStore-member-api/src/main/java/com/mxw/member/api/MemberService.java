package com.mxw.member.api;

import com.mxw.common.model.entity.ShopBuyerDO;
import com.mxw.common.model.vo.LabelVO;
import com.mxw.common.model.vo.PageVO;


import java.util.List;


/**
 * 用户基本属性操作
 */
public interface MemberService {

    PageVO<ShopBuyerDO> queryShopBuyer();

    PageVO<ShopBuyerDO> queryShopBuyerByPage(ShopBuyerDO shopBuyerDTO);

    ShopBuyerDO getMemberDetailByShopBuyerId(String shopBuyerId);

    List<LabelVO> queryLabelByshopBuyerId(String shopBuyerId);


}
