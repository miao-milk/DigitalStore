package com.mxw.analysis.service.impl;

import cn.hutool.core.date.DateUtil;
import com.mxw.analysis.api.MenberAnalysisService;
import com.mxw.analysis.mapper.TradeEverydayMapper;
import com.mxw.analysis.utils.TradeEveryDayUtils;
import com.mxw.common.model.entity.TradeEverydayDO;
import com.mxw.common.model.vo.ChartResponseVO;
import com.mxw.common.model.vo.NewOldBuyerCompareVO;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class MenberAnalysisServiceImpl implements MenberAnalysisService {

    @Autowired
    TradeEverydayMapper tradeEverydayMapper;
    @Autowired
    TradeEveryDayUtils everyDayUtils;

    @Override
    public ChartResponseVO getNumberOfMembers(String sellerId) {
        //直接获取过去七日的用户数据，今日的不显示，在首页已有今日新增的会员数
        List<TradeEverydayDO> sevenDaySales = everyDayUtils.getSevenDaySales(sellerId);
        ChartResponseVO chartResponseVO = new ChartResponseVO();
        List<String> xAxisData=new ArrayList<>();
        HashMap<String,List<Integer>> seriesData=new HashMap<>();
        List<Integer> seriesData1=new ArrayList<>();
        sevenDaySales.stream().forEach(item->{
            xAxisData.add(DateUtil.format(item.getCreateTime(), "MM-dd"));
            seriesData1.add(item.getUserNum());
        });
        seriesData.put("data1",seriesData1);
        chartResponseVO.setXAxisData(xAxisData);
        chartResponseVO.setSeriesData(seriesData);
        return chartResponseVO;
    }

    @Override
    public ChartResponseVO getLevelMembershipChanges(String sellerId) {
        //直接获取过去七日的用户数据，今日的不显示，在首页已有今日新增的会员数
        List<TradeEverydayDO> sevenDaySales = everyDayUtils.getSevenDaySales(sellerId);
        ChartResponseVO chartResponseVO = new ChartResponseVO();
        List<String> xAxisData=new ArrayList<>();
        HashMap<String,List<Integer>> seriesData=new HashMap<>();
        List<Integer> seriesData1=new ArrayList<>();
        List<Integer> seriesData2=new ArrayList<>();
        List<Integer> seriesData3=new ArrayList<>();
        sevenDaySales.stream().forEach(item->{
            xAxisData.add(DateUtil.format(item.getCreateTime(), "MM-dd"));
            seriesData1.add(item.getOrdinaryMemberNum());
            seriesData2.add(item.getIntermediateMemberNum());
            seriesData3.add(item.getSeniorMemberNum());
        });
        seriesData.put("data1",seriesData1);
        seriesData.put("data2",seriesData2);
        seriesData.put("data3",seriesData3);
        chartResponseVO.setXAxisData(xAxisData);
        chartResponseVO.setSeriesData(seriesData);
        return chartResponseVO;
    }

    @Override
    public List<NewOldBuyerCompareVO> getNewAndOldMembers(String sellerId) {
        //初始化
        List<NewOldBuyerCompareVO> newOldBuyerCompareVOList=new ArrayList<>();
        String[] nameArr = {"总成交客户", "新客户", "老客户", "成交1次客户", "成交多次客户", "复购客户（交易次数=2）", "忠实客户（2＜交易次数 ≤ 5）", "粉丝客户（5＜交易次数）","潜在客户（交易次数=1）'"};
        for (String name : nameArr) {
            NewOldBuyerCompareVO newOldBuyerCompareDTO = new NewOldBuyerCompareVO();
            newOldBuyerCompareDTO.setName(name);
            newOldBuyerCompareDTO.setPercent(0.00);
            newOldBuyerCompareDTO.setBuyerCount(0);
            newOldBuyerCompareDTO.setTradeCount(0);
            newOldBuyerCompareDTO.setPayment(0.00);
            newOldBuyerCompareDTO.setBuyerAvgCost(0.00);
            newOldBuyerCompareDTO.setReturnBuyerCountSum(0);
            newOldBuyerCompareDTO.setReturnBuyerPaymentSum(0.00);
            newOldBuyerCompareDTO.setReturnBuyerPercentSum(0.00);
            newOldBuyerCompareVOList.add(newOldBuyerCompareDTO);
        }
        return newOldBuyerCompareVOList;
    }
}
