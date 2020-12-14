package com.mxw.member.api;

import com.mxw.common.model.param.MemberParam;
import com.mxw.common.model.vo.PageVO;
import com.mxw.member.dto.ShopBuyerDTO;


/**
 * 用户基本属性操作
 */
public interface MemberService {

   PageVO<ShopBuyerDTO> queryShopBuyerByPage(MemberParam memberParam);
}
