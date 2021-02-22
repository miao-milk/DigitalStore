package com.mxw.analysis.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.mxw.analysis.api.MenberAnalysisService;
import com.mxw.analysis.mapper.TradeEverydayMapper;
import com.mxw.analysis.utils.RedisUtils;
import com.mxw.analysis.utils.TradeEveryDayUtils;
import com.mxw.common.model.entity.TradeEverydayDO;
import com.mxw.common.model.vo.ChartAnalysisVO;
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
    @Autowired
    RedisUtils redisUtils;

    @Override
    public ChartResponseVO getNumberOfMembers(String sellerId) {
        //直接获取过去七日的用户数据，今日的不显示，在首页已有今日新增的会员数 图表解析
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
        String jsonString = JSONObject.toJSONString(chartResponseVO);
        redisUtils.set("NumberOfMembers@"+sellerId,jsonString,60*60*24);
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
        seriesData.put("ordinaryMember",seriesData1);
        seriesData.put("intermediateMember",seriesData2);
        seriesData.put("seniorMember",seriesData3);
        chartResponseVO.setXAxisData(xAxisData);
        chartResponseVO.setSeriesData(seriesData);
        String jsonString = JSONObject.toJSONString(chartResponseVO);
        redisUtils.set("LevelMembershipChanges@"+sellerId,jsonString,60*60*24);
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
        String jsonString = JSONObject.toJSONString(newOldBuyerCompareVOList);
        redisUtils.set("newOldBuyerCompare@"+sellerId,jsonString,60*60*24);
        return newOldBuyerCompareVOList;
    }

    @Override
    public ChartAnalysisVO getChartAnalysisResult(String analysisType, String sellerId) {
        ChartAnalysisVO chartAnalysisResult = new ChartAnalysisVO();
        String result="";
        switch (analysisType){
            case "1":
                //会员数量走势图
                result=analysisOfTheNumberOfMembers(sellerId);
            case "2":
                //等级会员变化图
                result=levelMembershipChanges(sellerId);
            case "3":
                //新老会员对比图
                result=newAndOldmembers(sellerId);
            case "4":
                chartAnalysisResult.setResult("4");
            case "5":
                chartAnalysisResult.setResult("5");
            case "6":
                chartAnalysisResult.setResult("6");
            case "7":
                chartAnalysisResult.setResult("7");
        }
        chartAnalysisResult.setResult(result);
        return chartAnalysisResult;
    }

    private String newAndOldmembers(String sellerId) {
        StringBuilder result = new StringBuilder();
        //从缓存中获取对应的值，减少mysql的io查询
        Object o = redisUtils.get("newOldBuyerCompare@" + sellerId);
        List<NewOldBuyerCompareVO> newOldBuyerCompareVO = (List<NewOldBuyerCompareVO>)JSONObject.parse(o.toString());
        String[] nameArr = {"总成交客户", "新客户", "老客户", "成交1次客户", "成交多次客户", "复购客户（交易次数=2）", "忠实客户（2＜交易次数 ≤ 5）", "粉丝客户（5＜交易次数）","潜在客户（交易次数=1）'"};
        for (NewOldBuyerCompareVO oldBuyerCompareVO : newOldBuyerCompareVO) {
            if ("总成交客户".equals(oldBuyerCompareVO.getName())){
                result.append("总成交客户有"+oldBuyerCompareVO.getBuyerCount()+"人,总订单数为"+oldBuyerCompareVO.getTradeCount());
            }if ("新客户".equals(oldBuyerCompareVO.getName())){
                result.append("其中，新用户占比为"+oldBuyerCompareVO.getPercent()+"订单数为"+oldBuyerCompareVO.getTradeCount()+"单，可见新用户购买力低下，需要大力推广拉新活动");
            }if ("老客户".equals(oldBuyerCompareVO.getName())){
                result.append("其中，老用户占比为"+oldBuyerCompareVO.getPercent()+"复购数为"+oldBuyerCompareVO.getReturnBuyerCountSum()+"单，可见老用户复购率低，需要开展老用户促销活动");
            }
        }
        return result.toString();
    }

    private String levelMembershipChanges(String sellerId) {
        StringBuilder result = new StringBuilder();
        //从缓存中获取对应的值，减少mysql的io查询
        Object o = redisUtils.get("LevelMembershipChanges@" + sellerId);
        ChartResponseVO chartResponseVO = (ChartResponseVO)JSONObject.parse(o.toString());
        //波动要求出其标准差
        HashMap<String, List<Integer>> seriesData = chartResponseVO.getSeriesData();
        //获取日期数据
        List<String> xAxisData = chartResponseVO.getXAxisData();
        //获取普通会员
        List<Integer> ordinaryMemberData = seriesData.get("ordinaryMember");
        //获取最大下降数对应的日期
        Integer ordinaryMemberDataMin = CollectionUtil.min(ordinaryMemberData);
        int ordinaryMemberDataIndex = ordinaryMemberData.indexOf(ordinaryMemberDataMin);
        String ordinaryMemberDataString = xAxisData.get(ordinaryMemberDataIndex);
        result.append("在七天时间间期内，在"+ordinaryMemberDataString+"这日有"+ordinaryMemberDataMin+"名普通会员退会，请查清当日是否对应的活动清除会员，请及时搞促销活动，拉回会员");
        //获取中级会员
        List<Integer> intermediateMemberData = seriesData.get("intermediateMember");
        //获取最大下降数对应的日期
        Integer intermediateMemberDataMin = CollectionUtil.min(intermediateMemberData);
        int intermediateMemberDataIndex = ordinaryMemberData.indexOf(intermediateMemberDataMin);
        String intermediateMemberDataDataString = xAxisData.get(intermediateMemberDataIndex);
        result.append("在七天时间间期内，在"+intermediateMemberDataDataString+"这日有"+intermediateMemberDataMin+"名中级会员退会，请查清当日是否对应的活动清除会员，请及时搞促销活动，拉回会员");
        //获取高级会员
        List<Integer> seniorMemberData = seriesData.get("seniorMember");
        Integer seniorMemberDataMin = CollectionUtil.min(seniorMemberData);
        int seniorMemberDataIndex = ordinaryMemberData.indexOf(seniorMemberDataMin);
        String seniorMemberDataString = xAxisData.get(seniorMemberDataIndex);
        result.append("在七天时间间期内，在"+seniorMemberDataString+"这日有"+seniorMemberDataMin+"名高级会员退会，请查清当日是否对应的活动清除会员，请及时搞促销活动，拉回会员");

        return result.toString();
    }

    private String analysisOfTheNumberOfMembers(String sellerId) {
        StringBuilder result=new StringBuilder();
        //从缓存中获取对应的值，减少mysql的io查询
        Object o = redisUtils.get("NumberOfMembers@" + sellerId);
        ChartResponseVO chartResponseVO = (ChartResponseVO)JSONObject.parse(o.toString());
        //波动要求出其标准差
        HashMap<String, List<Integer>> seriesData = chartResponseVO.getSeriesData();
        List<Integer> data1 = seriesData.get("data1");
        //平均值
        int sum=0;
        for (Integer integer : data1) {
            sum=sum+integer;
        }
        int svg=sum/(data1.size());
        //标准差分子
        double molecular=1.0;
        for (Integer integer : data1) {
            molecular=molecular+Math.pow(integer-svg,2);
        }
        //标准差分母
        double Denominator=molecular/data1.size();
        double standardDeviation = Math.sqrt(molecular / Denominator);
        if (standardDeviation<0.4){
            result.append("用户数量变化稳定");
        }else {
            result.append("用户数量变化波动");
        }
        //上升还是下降 直接第一天和最后一天做差比较
        Integer start = data1.get(0);
        Integer end = data1.get(data1.size()-1);
        Integer re=start-end;
        if (re>0){
            result.append("用户数量增加，继续提供新用户优惠活动");
        }else {
            result.append("用户数量减少，请拉回老用户，使用营销活动");
        }
        return result.toString();
    }
}
