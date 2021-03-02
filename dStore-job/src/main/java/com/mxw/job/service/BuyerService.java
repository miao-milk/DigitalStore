package com.mxw.job.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mxw.common.model.entity.ShopBuyerDO;
import com.mxw.job.mapper.BuyerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BuyerService {

    @Autowired
    private BuyerMapper buyerMapper;

    public Long saveBuyer(String sellerId,String name,String addres,String mobiles,Integer sexInt){
        ShopBuyerDO buyer = new ShopBuyerDO();
        buyer.setSellerId(Long.parseLong(sellerId));
        buyer.setBuyerNick(name);
        buyer.setReceiverName(name);
        buyer.setReceiverAddress(addres);
        buyer.setReceiverZip(" ");
        buyer.setReceiverMobile(mobiles);
        buyer.setReceiverMobileType(null);
        buyer.setBuyTotalCount(RandomUtil.randomInt(0,1000));
        buyer.setBuyTotalMoney(RandomUtil.randomBigDecimal());
        buyer.setPaymentNum(RandomUtil.randomInt(0,1000));
        buyer.setAvgNum(RandomUtil.randomInt(0,1000));
        buyer.setGuestPrice(RandomUtil.randomBigDecimal());
        buyer.setRefundNum(RandomUtil.randomInt(0,1000));
        buyer.setRefundMoney(RandomUtil.randomBigDecimal());
        buyer.setWeChat(mobiles);
        buyer.setQq(mobiles);
        buyer.setEmail(mobiles+"@qq.com");
        buyer.setBuyerAlipayNo(mobiles);
        buyer.setGrade(RandomUtil.randomInt(0,10));
        buyer.setSex(sexInt);
        buyer.setBornYear(DateUtil.format(new Date(), "yyyy"));
        buyer.setBuyerLastTime(new Date());
        buyer.setBuyerLastPayTime(new Date());
        buyer.setBuyerFristTime(new Date());
        buyer.setBuyerFristPayTime(new Date());
        buyer.setBuyerFristSucceedTime(new Date());
        buyer.setBuyerLastSucceedTime(new Date());
        int insert = buyerMapper.insert(buyer);
        ShopBuyerDO shopBuyerDO = buyerMapper.selectOne(new QueryWrapper<ShopBuyerDO>().eq("buyer_nick", name));
        return shopBuyerDO.getShopBuyerId();
    }
}
