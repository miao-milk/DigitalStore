package com.mxw.analysis.api;

import com.mxw.common.model.dto.NewTradeUsersDTO;
import com.mxw.common.model.dto.OrderDTO;
import com.mxw.common.model.dto.SalesDTO;
import com.mxw.common.model.entity.AreaDataDO;
import com.mxw.common.model.entity.ShopBuyerDO;
import com.mxw.common.model.vo.GroupDetailVO;
import com.mxw.common.model.vo.GroupVO;

import java.util.List;

/**
 * 用户分组
 */
public interface TradeService {


    SalesDTO getTotalSales(String sellerId);

    OrderDTO getTotalOrders(String sellerId);

    NewTradeUsersDTO getTodayUsers(String sellerId);

    List<AreaDataDO> getMapData(String sellerId);
}
