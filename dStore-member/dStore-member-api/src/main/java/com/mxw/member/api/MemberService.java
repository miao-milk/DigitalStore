package com.mxw.member.api;

import com.mxw.common.model.entity.ShopBuyerDetail;
import com.mxw.common.model.vo.PageVO;
import com.mxw.member.dto.ShopBuyerDTO;

import java.util.List;
import java.util.Map;


/**
 * 用户基本属性操作
 */
public interface MemberService {

    /**
     * 分页查询用户基本属性列表
     * @return
     */
   PageVO<ShopBuyerDTO> queryShopBuyerByPage( ShopBuyerDTO shopBuyerDTO);

    /**
     * 将该用户添加到黑名单中
     * @param sellerId
     */
    void addBlackList(String sellerId);

    ShopBuyerDetail getMemberDetailByShopBuyerId(String shopBuyerId);

    void modifyMemberDetail(ShopBuyerDetail shopBuyerDetail);

    void addMemberDetail(ShopBuyerDetail shopBuyerDetail);

    void uploadMembers(List<Object> list);

    Map<Integer,List<ShopBuyerDTO>> queryMemberLevel(int level);

    PageVO<ShopBuyerDTO> queryShopBuyer();
}
