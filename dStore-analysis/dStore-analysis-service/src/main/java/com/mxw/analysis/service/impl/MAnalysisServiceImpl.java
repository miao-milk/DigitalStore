package com.mxw.analysis.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mxw.analysis.api.MAnalysisService;
import com.mxw.analysis.mapper.BuyerEverydayMapper;
import com.mxw.analysis.mapper.TradeEverydayMapper;;
import com.mxw.common.model.entity.BuyerEverydayDO;
import com.mxw.common.model.entity.TradeEverydayDO;
import com.mxw.common.model.vo.ChartResponseVO;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MAnalysisServiceImpl implements MAnalysisService {

    @Autowired
    BuyerEverydayMapper buyerEverydayMapper;
    @Autowired
    TradeEverydayMapper tradeEverydayMapper;

    @Override
    public ChartResponseVO getPurchaseAmount(String sellerId) {
        //获取近七天的用户购买金额
        //从每日销售记录表获取昨天记录
        ChartResponseVO chartResponseVO = new ChartResponseVO();
        List<String> xAxisData = new ArrayList<>();
        List<Integer> values = new ArrayList<>();
        HashMap<String, List<Integer>> seriesData = new HashMap<>();
        QueryWrapper<TradeEverydayDO> queryWrapper = new QueryWrapper<>();
        //从每日销售记录表获取昨天记录
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -7);
        Date yesterday = calendar.getTime();
        String oldTime = DateUtil.format(yesterday, "yyyy-MM-dd 00:00:00");
        queryWrapper.ge("create_time", oldTime).eq("seller_id", sellerId);
        List<TradeEverydayDO> tradeEverydayDO = tradeEverydayMapper.selectList(queryWrapper);

        tradeEverydayDO.stream().forEach(item -> {
            xAxisData.add(DateUtil.format(item.getCreateTime(), "MM-dd"));
            values.add(item.getDeal());
        });
        seriesData.put("data", values);
        chartResponseVO.setXAxisData(xAxisData);
        chartResponseVO.setSeriesData(seriesData);
        return chartResponseVO;
    }

    @Override
    public Map<String, Integer> getConsumptionRanking(String sellerId) {
        //获取当日消费最高的前五名
        HashMap<String, Integer> valuse = new HashMap<>();
        QueryWrapper<BuyerEverydayDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("seller_id", sellerId).eq("create_time", new Date());
        List<BuyerEverydayDO> buyerEverydayDOS = buyerEverydayMapper.selectList(queryWrapper);
        int max = 0;
        Map<String, Integer> collect = buyerEverydayDOS.stream().sorted(Comparator.comparing(BuyerEverydayDO::getBuyerPrice)).limit(5).collect(Collectors.toMap(BuyerEverydayDO::getBuyerName, BuyerEverydayDO -> {
            return BuyerEverydayDO.getBuyerPrice();
        }));

        return collect;
    }
}
