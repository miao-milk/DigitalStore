package com.mxw.member.api;

import com.mxw.common.model.entity.ShopBuyerDO;
import com.mxw.common.model.vo.LabelVO;
import com.mxw.common.model.vo.PageVO;
import com.mxw.member.dto.ShopBuyerDTO;

import java.util.List;
import java.util.Map;


/**
 * 用户基本属性操作
 */
public interface MemberService {

    List<LabelVO> queryLabelByshopBuyerId(String shopBuyerId);

    /**
     * 分页查询用户基本属性列表
     *
     * @return
     */
    PageVO<ShopBuyerDTO> queryShopBuyerByPage(ShopBuyerDTO shopBuyerDTO);

    ShopBuyerDO getMemberDetailByShopBuyerId(String shopBuyerId);

    Map<Integer, List<ShopBuyerDTO>> queryMemberLevel(int level);

    PageVO<ShopBuyerDTO> queryShopBuyer();
}
