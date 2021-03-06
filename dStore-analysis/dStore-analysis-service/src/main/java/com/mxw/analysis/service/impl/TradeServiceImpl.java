package com.mxw.analysis.service.impl;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mxw.analysis.api.MemberService;
import com.mxw.analysis.api.TradeService;
import com.mxw.analysis.mapper.AreaMapper;
import com.mxw.analysis.mapper.TradeEverydayMapper;
import com.mxw.analysis.mapper.TradeMapper;
import com.mxw.analysis.utils.TradeEveryDayUtils;
import com.mxw.common.model.dto.NewTradeUsersDTO;
import com.mxw.common.model.dto.OrderDTO;
import com.mxw.common.model.dto.SalesDTO;
import com.mxw.common.model.entity.AreaDataDO;
import com.mxw.common.model.entity.TradeDO;
import com.mxw.common.model.entity.TradeEverydayDO;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TradeServiceImpl implements TradeService {


    @Autowired
    TradeMapper tradeMapper;
    @Autowired
    TradeEverydayMapper tradeEverydayMapper;
    @Reference
    private MemberService memberService;
    @Autowired
    AreaMapper areaMapper;
    @Autowired
    TradeEveryDayUtils everyDayUtils;

    @Override
    public SalesDTO getTotalSales(String sellerId) {
        //获取今日销售额 成交额
        List<TradeDO> todayTrades = everyDayUtils.getTodayTrades(sellerId);
        BigDecimal salesLastDay = new BigDecimal("0");
        for (TradeDO tradeDO : todayTrades) {
            salesLastDay = salesLastDay.add(tradeDO.getOrderTotalAmount());
        }
        //获取昨日的销售额
        TradeEverydayDO yesterdayTradeDO = everyDayUtils.getyesterdaySales(sellerId);
        BigDecimal yesterdaySales = yesterdayTradeDO.getSales();
        //算出增长量
        BigDecimal subtract = salesLastDay.subtract(yesterdaySales);
        BigDecimal salesGrowthLastDay = subtract.divide(yesterdaySales, 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));

        //封装数据
        SalesDTO salesDTO = new SalesDTO();
        salesDTO.setSalesToday(String.valueOf(salesLastDay));
        salesDTO.setSalesLastDay(String.valueOf(yesterdaySales));
        salesDTO.setSalesGrowthLastDay(String.valueOf(salesGrowthLastDay));
        return salesDTO;
    }

    @Override
    public OrderDTO getTotalOrders(String sellerId) {

        //获取今日的订单数
        List<TradeDO> todayTrades = everyDayUtils.getTodayTrades(sellerId);
        //遍历今日订单数，封装一日的销售数据
        //获取昨日的订单数
        TradeEverydayDO tradeEverydayDO = everyDayUtils.getyesterdaySales(sellerId);
        Integer orders = tradeEverydayDO.getOrders()==null?0:tradeEverydayDO.getOrders();


        //封装数据
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderLastDay(String.valueOf(orders));
        orderDTO.setOrderToday(String.valueOf(todayTrades.size()));
        return orderDTO;
    }

    @Override
    public NewTradeUsersDTO getTodayUsers(String sellerId) {
        ArrayList<String> oldTimeList = new ArrayList<>();
        ArrayList<Integer> oldUserList = new ArrayList<>();
        //查询7天的数据
        QueryWrapper<TradeEverydayDO> tradeEverydayDOQueryWrapper = new QueryWrapper<>();
        //从每日销售记录表获取昨天记录
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -6);
        Date yesterday = calendar.getTime();
        String oldTime = DateUtil.format(yesterday, "yyyy-MM-dd 00:00:00");
        String nowTime = DateUtil.format(new Date(), "yyyy-MM-dd 00:00:00");
        tradeEverydayDOQueryWrapper.ge("create_time", oldTime).le("create_time", nowTime);
        List<TradeEverydayDO> tradeEverydayDOS = tradeEverydayMapper.selectList(tradeEverydayDOQueryWrapper);
        for (TradeEverydayDO tradeEverydayDO : tradeEverydayDOS) {
            oldTimeList.add(DateUtil.format(tradeEverydayDO.getCreateTime(), "MM-dd"));
            oldUserList.add(tradeEverydayDO.getNewuser());
        }
        //获取昨日会员数
        TradeEverydayDO yesterdayDO = everyDayUtils.getyesterdaySales(sellerId);
        NewTradeUsersDTO newTradeUsersDTO = new NewTradeUsersDTO();
        newTradeUsersDTO.setOrderUser(oldUserList.size()==0?0:oldUserList.get(oldUserList.size()-1));
        newTradeUsersDTO.setReturnRate(yesterdayDO.getNewuser()==null?0:yesterdayDO.getNewuser());
        newTradeUsersDTO.setOldTimeList(oldTimeList);
        newTradeUsersDTO.setOldUserList(oldUserList);
        return newTradeUsersDTO;
    }

    @Override
    public List<AreaDataDO> getMapData(String sellerId) {
        List<AreaDataDO> areaDataDOS = areaMapper.selectList(null);
        List<AreaDataDO> collect = areaDataDOS.stream().map(item -> {
            int randomInt = RandomUtil.randomInt(0, 500);
            item.setValue(randomInt);
            return item;
        }).collect(Collectors.toList());
        return collect;
    }

}
