package com.mxw.job.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mxw.common.model.entity.BuyerEverydayDO;
import com.mxw.common.model.entity.ShopBuyerDO;
import com.mxw.common.model.entity.TradeEverydayDO;
import com.mxw.job.mapper.BuyerEverydayMapper;
import com.mxw.job.mapper.BuyerMapper;
import com.mxw.job.mapper.TradeEverydayMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BuyerService {

    @Autowired
    private BuyerMapper buyerMapper;
    @Autowired
    private BuyerEverydayMapper buyerEverydayMapper;
    @Autowired
    private TradeEverydayMapper tradeEverydayMapper;

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
        buyer.setBuyTotalMoney(RandomUtil.randomBigDecimal(BigDecimal.valueOf(100),BigDecimal.valueOf(10000)));
        buyer.setPaymentNum(RandomUtil.randomInt(0,1000));
        buyer.setAvgNum(RandomUtil.randomInt(0,1000));
        buyer.setGuestPrice(RandomUtil.randomBigDecimal(BigDecimal.valueOf(100),BigDecimal.valueOf(10000)));
        buyer.setRefundNum(RandomUtil.randomInt(0,1000));
        buyer.setRefundMoney(RandomUtil.randomBigDecimal(BigDecimal.valueOf(100),BigDecimal.valueOf(10000)));
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
        buyer.setCreateTime(new Date());
        int insert = buyerMapper.insert(buyer);
        ShopBuyerDO shopBuyerDO = buyerMapper.selectOne(new QueryWrapper<ShopBuyerDO>().eq("buyer_nick", name));
        return shopBuyerDO.getShopBuyerId();
    }

    public void userDataStatistics() {
        System.out.println("BuyerEverydayDO任务开启|启动时间："+new Date());
        //查询昨日的全部用户
        //从每日销售记录表获取昨天记录
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -1);
        Date yesterday = calendar.getTime();
        String yesterdayTime = DateUtil.format(yesterday, "yyyy-MM-dd 00:00:00");
        String todayTime = DateUtil.format(new Date(), "yyyy-MM-dd 00:00:00");
        QueryWrapper<ShopBuyerDO> wrapper = new QueryWrapper<>();
        //大于昨天小于今天
        wrapper.ge("create_time", yesterdayTime).le("create_time",todayTime);
        List<ShopBuyerDO> shopBuyerDOS = buyerMapper.selectList(wrapper);
        //封装数据
        List<BuyerEverydayDO> collect = shopBuyerDOS.stream().map(shopBuyerDO -> {
            BuyerEverydayDO buyerEverydayDO = new BuyerEverydayDO();
            Long shopBuyerId = shopBuyerDO.getShopBuyerId();
            buyerEverydayDO.setBuyerId(shopBuyerId.intValue());
            String receiverName = shopBuyerDO.getReceiverName();
            buyerEverydayDO.setBuyerName(receiverName);
            System.out.println("BuyerEverydayDO任务开启|正在统计receiverName："+receiverName);
            BigDecimal buyTotalMoney = shopBuyerDO.getBuyTotalMoney();
            buyerEverydayDO.setBuyPrice(buyTotalMoney.intValue());
            buyerEverydayDO.setCreateTime(new Date());
            Long sellerId = shopBuyerDO.getSellerId();
            buyerEverydayDO.setSellerId(sellerId.intValue());
            return buyerEverydayDO;
        }).collect(Collectors.toList());
        System.out.println("BuyerEverydayDO任务开启|插入数据开始");
        for (BuyerEverydayDO buyerEverydayDO : collect) {
            buyerEverydayMapper.insert(buyerEverydayDO);
        }
        System.out.println("BuyerEverydayDO任务开启|插入数据结束");
    }

    //统计seller_everyday
    public void userSellerStatistics() {

        //查询昨日的全部用户
        //从每日销售记录表获取昨天记录
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -1);
        Date yesterday = calendar.getTime();
        String yesterdayTime = DateUtil.format(yesterday, "yyyy-MM-dd 00:00:00");
        String todayTime = DateUtil.format(new Date(), "yyyy-MM-dd 00:00:00");
        QueryWrapper<ShopBuyerDO> wrapper = new QueryWrapper<>();
        //大于昨天小于今天
        String[] sellerID={"1","2"};
        for (String s : sellerID) {
            wrapper.ge("create_time", yesterdayTime).le("create_time",todayTime).eq("seller_id",s);
            List<ShopBuyerDO> shopBuyerDOS = buyerMapper.selectList(wrapper);
            TradeEverydayDO tradeEverydayDO = new TradeEverydayDO();
            tradeEverydayDO.setSellerId(Integer.parseInt(s));
            BigDecimal sales=new BigDecimal(0);
            for (ShopBuyerDO shopBuyerDO : shopBuyerDOS) {
                BigDecimal buyTotalMoney = shopBuyerDO.getBuyTotalMoney();
                sales=sales.add(buyTotalMoney);
            }
            tradeEverydayDO.setSales(sales);
            tradeEverydayDO.setOrders(shopBuyerDOS.size());
            tradeEverydayDO.setNewuser(shopBuyerDOS.size());
            tradeEverydayDO.setDeal(shopBuyerDOS.size());
            tradeEverydayDO.setCreateTime(new Date());
            int num=0;
            if(shopBuyerDOS.size()!=0){
                num=RandomUtil.randomInt(1,shopBuyerDOS.size()-1);
            }
            tradeEverydayDO.setUserNum(shopBuyerDOS.size());
            tradeEverydayDO.setOrdinaryMemberNum(num);
            tradeEverydayDO.setIntermediateMemberNum(num);
            tradeEverydayDO.setSeniorMemberNum(num);

            tradeEverydayMapper.insert(tradeEverydayDO);
        }


    }
}
