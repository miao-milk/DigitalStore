package com.mxw.member.service.impl;

import com.mxw.common.model.param.MemberParam;
import com.mxw.common.model.vo.PageVO;
import com.mxw.member.api.MemberService;
import com.mxw.member.dto.ShopBuyerDTO;


public class MemberServiceImpl implements MemberService {

    @Override
    public PageVO<ShopBuyerDTO> queryShopBuyerByPage(ShopBuyerDTO shopBuyerDTO, Integer pageNo,Integer pageSize) {
        return null;
    }
}
