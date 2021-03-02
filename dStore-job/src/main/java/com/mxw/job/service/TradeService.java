package com.mxw.job.service;


import cn.hutool.core.util.RandomUtil;
import com.mxw.common.model.entity.TradeDO;
import com.mxw.job.mapper.TradeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TradeService {
    @Autowired
    private TradeMapper tradeMapper;

    public void saveTrade(String sellerId,Long shopBuyerID,String name,String addres,String mobiles){
        TradeDO tradeDO = new TradeDO();
        tradeDO.setSellerId(Long.parseLong(sellerId));
        tradeDO.setShopBuyerId(shopBuyerID);
        tradeDO.setReceivedPayment(RandomUtil.randomBigDecimal());
        tradeDO.setPostAmount(RandomUtil.randomBigDecimal());
        tradeDO.setPostReceiver(name);
        tradeDO.setPostAddr(addres);
        tradeDO.setPostCode("");
        tradeDO.setPostTel(mobiles);
        tradeDO.setTid(String.valueOf(RandomUtil.randomInt(0,10000)));
        tradeDO.setNum(RandomUtil.randomInt(0,1000));
        tradeDO.setPrice(RandomUtil.randomBigDecimal());
        tradeDO.setTotalFee(RandomUtil.randomBigDecimal());
        tradeDO.setPayTime(new Date());
        tradeDO.setUpdateTime(new Date());
        tradeDO.setLogisticsTime(new Date());
        tradeDO.setCreateTime(new Date());
        tradeDO.setOrderTotalAmount(RandomUtil.randomBigDecimal());
        tradeMapper.insert(tradeDO);
    }
}
