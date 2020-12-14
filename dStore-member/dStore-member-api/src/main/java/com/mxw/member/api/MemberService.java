package com.mxw.member.api;

import com.mxw.common.model.vo.PageVO;
import com.mxw.member.dto.ShopBuyerDTO;


/**
 * 用户基本属性操作
 */
public interface MemberService {

    /**
     * 分页查询用户基本属性列表
     * @return
     */
   PageVO<ShopBuyerDTO> queryShopBuyerByPage( ShopBuyerDTO shopBuyerDTO, Integer pageNo,Integer pageSize);
}
