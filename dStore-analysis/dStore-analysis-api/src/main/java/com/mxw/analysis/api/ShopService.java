package com.mxw.analysis.api;

import com.mxw.common.model.entity.ShopBuyerDO;

import java.util.List;

public interface ShopService {
    Integer selectCount(String wrapper);

    List<ShopBuyerDO> queryShopBuyer(String sellerId);

    List<ShopBuyerDO> queryShopBuyerByPage(ShopBuyerDO shopBuyerDTO);

    ShopBuyerDO getMemberDetailByShopBuyerId(String shopBuyerId);

    List<ShopBuyerDO> selectListBySellerId(String sellerId);
}
