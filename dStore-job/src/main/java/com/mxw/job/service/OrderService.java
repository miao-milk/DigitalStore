package com.mxw.job.service;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mxw.common.model.entity.GoodsDO;
import com.mxw.common.model.entity.GoodsEverydayDO;
import com.mxw.common.model.entity.OrderDO;
import com.mxw.common.model.entity.ShopBuyerDO;
import com.mxw.job.mapper.GoodsEverydayMapper;
import com.mxw.job.mapper.GoodsMapper;
import com.mxw.job.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {


    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsEverydayMapper goodsEverydayMapper;

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

    public void userDataStatistics() {

        System.out.println("GoodsEverydayDO任务开启|启动时间："+new Date());
        //查询昨日的全部用户
        //从每日销售记录表获取昨天记录
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -1);
        Date yesterday = calendar.getTime();
        String yesterdayTime = DateUtil.format(yesterday, "yyyy-MM-dd 00:00:00");
        String todayTime = DateUtil.format(new Date(), "yyyy-MM-dd 00:00:00");
        QueryWrapper<OrderDO> wrapper = new QueryWrapper<>();
        List<GoodsDO> goodsDOS = goodsMapper.selectList(null);

        for (GoodsDO goodsDO : goodsDOS) {
            String goodsName = goodsDO.getGoodsName();
            Integer sellerId = goodsDO.getSellerId();
            //大于昨天小于今天
            wrapper.ge("create_time", yesterdayTime).le("create_time",todayTime).eq("product_name",goodsName);
            List<OrderDO> orderDOS = orderMapper.selectList(wrapper);
            GoodsEverydayDO goodsEverydayDO = new GoodsEverydayDO();
            Integer id = goodsDO.getSellerId();
            goodsEverydayDO.setSellerId(id);
            Integer id1 = goodsDO.getId();
            goodsEverydayDO.setGoodsId(id1);
            String goodsName1 = goodsDO.getGoodsName();
            goodsEverydayDO.setGoodsName(goodsName1);
            String[] goodsNames={"外观","包装","价格","颜色","大众","好评","实用","售后","质量","安全"};
            ArrayList<HashMap<String, Integer>> goodsFetures = new ArrayList<>();
            HashMap<String, Integer> hashMap = new HashMap<>();
            for (int i = 0; i <5; i++) {
                hashMap.put(RandomUtil.randomEle(goodsNames),RandomUtil.randomInt(1,20));
            }
            Set<String> strings = hashMap.keySet();
            for (String string : strings) {
                HashMap<String, Integer> hashMap1 = new HashMap<>();
                hashMap1.put(string,hashMap.get(string));
                goodsFetures.add(hashMap1);
            }
            String s = JSONObject.toJSONString(goodsFetures);
            goodsEverydayDO.setGoodsLikeFetures(s);

            String[] SalesNames={"品牌","内容","可用性","功能","国际","耐用","手感","证书","礼品","安全"};
            ArrayList<HashMap<String, Integer>> SalesFetures = new ArrayList<>();
            HashMap<String, Integer> hashMap2 = new HashMap<>();
            for (int i = 0; i <5; i++) {
                hashMap2.put(RandomUtil.randomEle(SalesNames),RandomUtil.randomInt(1,20));
            }
            Set<String> strings2 = hashMap2.keySet();
            for (String string : strings2) {
                HashMap<String, Integer> hashMap1 = new HashMap<>();
                hashMap1.put(string,hashMap2.get(string));
                SalesFetures.add(hashMap1);
            }
            String sales= JSONObject.toJSONString(SalesFetures);
            goodsEverydayDO.setGoodsSalesFetures(sales);
            goodsEverydayDO.setGoodsBuyer(orderDOS.size());
            //大于昨天小于今天
            int sunPrise=0;
            for (OrderDO orderDO : orderDOS) {
                sunPrise=sunPrise+orderDO.getPayment();
            }
            goodsEverydayDO.setGoodsPrice(sunPrise);
            goodsEverydayDO.setCreateTime(new Date());
            goodsEverydayMapper.insert(goodsEverydayDO);
        }

    }
}
