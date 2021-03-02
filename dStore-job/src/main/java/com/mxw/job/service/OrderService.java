package com.mxw.job.service;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mxw.common.model.entity.GoodsDO;
import com.mxw.common.model.entity.OrderDO;
import com.mxw.job.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderService {


    @Autowired
    private OrderMapper orderMapper;

    public Integer saveOrder(String sellerId,Long shopBuyerID,String name,String goodName,String goodsId){
        OrderDO orderDO=new OrderDO();
        orderDO.setSellerId(Integer.parseInt(sellerId));
        orderDO.setShopBuyerId(Integer.parseInt(shopBuyerID+""));
        orderDO.setPostTel(name);
        orderDO.setBuyerRate("1");
        orderDO.setProductName(goodName);
        orderDO.setProductId(Integer.parseInt(goodsId));
        orderDO.setPrice(RandomUtil.randomInt(0,1000));
        orderDO.setNum(RandomUtil.randomInt(0,1000));
        orderDO.setTotalFee(RandomUtil.randomInt(0,1000));
        orderDO.setPayment(RandomUtil.randomInt(0,1000));
        orderDO.setCreateTime( DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        orderDO.setPayTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        orderDO.setPayType("1");
        orderMapper.insert(orderDO);
        OrderDO orderDO1 = orderMapper.selectOne(new QueryWrapper<OrderDO>().eq("shop_buyer_id", shopBuyerID).eq("post_tel", name));
        return orderDO1.getOrderId();
    }
}
