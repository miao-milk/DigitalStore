package com.mxw.analysis.utils;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mxw.analysis.mapper.TradeEverydayMapper;
import com.mxw.analysis.mapper.TradeMapper;
import com.mxw.common.model.entity.TradeDO;
import com.mxw.common.model.entity.TradeEverydayDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class TradeEveryDayUtils {

    @Autowired
    TradeMapper tradeMapper;

    @Autowired
    TradeEverydayMapper tradeEverydayMapper;

    public List<TradeDO> getTodayTrades(String sellerId) {
        /**
         * 原符号       <       <=      >       >=      <>
         * 对应函数    lt()     le()    gt()    ge()    ne()
         * Mybatis-plus写法：  queryWrapper.ge("create_time", localDateTime);
         */
        QueryWrapper<TradeDO> tradeDOQueryWrapper = new QueryWrapper<>();
        Date now = new Date();
        String startTime = DateUtil.format(now, "yyyy-MM-dd 00:00:00");
        String endTime = DateUtil.format(now, "yyyy-MM-dd 23:59:59");
        tradeDOQueryWrapper.ge("pay_time", startTime).le("pay_time", endTime).eq("seller_id", sellerId);
        List<TradeDO> tradeDOS = tradeMapper.selectList(tradeDOQueryWrapper);
        return tradeDOS;
    }

    public TradeEverydayDO getyesterdaySales(String sellerId) {
        //从每日销售记录表获取昨天记录
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -1);
        Date yesterday = calendar.getTime();
        String yesterdayTime = DateUtil.format(yesterday, "yyyy-MM-dd 00:00:00");
        String todayTime = DateUtil.format(today, "yyyy-MM-dd 00:00:00");
        QueryWrapper<TradeEverydayDO> wrapper = new QueryWrapper<>();
        wrapper.ge("create_time", yesterdayTime).le("create_time", todayTime);
        TradeEverydayDO yesterdayTradeDO = tradeEverydayMapper.selectOne(wrapper);
        return yesterdayTradeDO;
    }

    public List<TradeEverydayDO> getSevenDaySales(String sellerId) {
        //从每日销售记录表获取昨天记录
        QueryWrapper<TradeEverydayDO> tradeEverydayDOQueryWrapper = new QueryWrapper<>();
        //从每日销售记录表获取昨天记录
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -7);
        Date yesterday = calendar.getTime();
        String oldTime = DateUtil.format(yesterday, "yyyy-MM-dd 00:00:00");
        tradeEverydayDOQueryWrapper.ge("create_time", oldTime);
        List<TradeEverydayDO> tradeEverydayDOS = tradeEverydayMapper.selectList(tradeEverydayDOQueryWrapper);
        return tradeEverydayDOS;
    }
}
