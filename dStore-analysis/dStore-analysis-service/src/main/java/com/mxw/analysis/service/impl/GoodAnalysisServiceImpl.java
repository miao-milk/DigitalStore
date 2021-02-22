package com.mxw.analysis.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mxw.analysis.api.GoodAnalysisService;
import com.mxw.analysis.mapper.GoodsEverydayMapper;
import com.mxw.analysis.mapper.GoodsMapper;
import com.mxw.analysis.utils.RedisUtils;
import com.mxw.common.model.entity.GoodsDO;
import com.mxw.common.model.entity.GoodsEverydayDO;
import com.mxw.common.model.vo.ChartResponseVO;
import com.mxw.common.model.vo.RadarDataVO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

public class GoodAnalysisServiceImpl implements GoodAnalysisService {

    @Autowired
    GoodsEverydayMapper goodsEverydayMapper;
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    RedisUtils redisUtils;

    @Override
    public RadarDataVO getProductSalesCharacteristics(String sellerId) {
        RadarDataVO radarDataVO = new RadarDataVO();
        //销售特征
         List<String> indicators=new ArrayList<>();
        //销售特征值
         HashMap<String,List<Integer>> seriesDataList=new HashMap<>();
        //喜爱特征
         List<String> favoriteFeature=new ArrayList<>();
        //喜爱特征值
         HashMap<String,List<Integer>> favoriteFeatureDataList=new HashMap<>();
         //查询出当前用户下的全部商品标题名
        QueryWrapper<GoodsDO> goodsQueryWrapper=new QueryWrapper();
        goodsQueryWrapper.eq("seller_id",sellerId);
        List<GoodsDO> goodsDOS = goodsMapper.selectList(goodsQueryWrapper);
        //特征标题值
        List<String> legendTitle = goodsDOS.stream().map(good -> {
            return good.getGoodsName();
        }).collect(Collectors.toList());

        //当日销售特征值
        QueryWrapper<GoodsEverydayDO> goodsEverydayQueryWrapper=new QueryWrapper();
        goodsEverydayQueryWrapper.eq("seller_id", sellerId).eq("create_time", new Date()).in("goods_name",legendTitle);
        List<GoodsEverydayDO> goodsEverydayDOS = goodsEverydayMapper.selectList(goodsEverydayQueryWrapper);
        for (GoodsEverydayDO goodsEverydayDO : goodsEverydayDOS) {
            List<GoodsEverydayDO.GoodsFeture> goodsSalesFetures = goodsEverydayDO.getGoodsSalesFetures();
            List<GoodsEverydayDO.GoodsFeture> goodsLikeFetures = goodsEverydayDO.getGoodsLikeFetures();
            //销售特征
            ArrayList<Integer> values = new ArrayList<>();
            for (GoodsEverydayDO.GoodsFeture goodsDO : goodsSalesFetures) {
                indicators.add(goodsDO.getFetureName());
                values.add(goodsDO.getPeopleNum());
            }
            //喜爱特征
            ArrayList<Integer> likeValues = new ArrayList<>();
            for (GoodsEverydayDO.GoodsFeture goodsDO : goodsLikeFetures) {
                favoriteFeature.add(goodsDO.getFetureName());
                likeValues.add(goodsDO.getPeopleNum());
            }
            favoriteFeatureDataList.put(goodsEverydayDO.getGoodsName(),values);
        }

        radarDataVO.setLegendTitle(legendTitle);
        radarDataVO.setIndicators(indicators);
        radarDataVO.setSeriesDataList(seriesDataList);
        radarDataVO.setFavoriteFeature(favoriteFeature);
        radarDataVO.setFavoriteFeatureDataList(favoriteFeatureDataList);
        return radarDataVO;
    }

    @Override
    public ChartResponseVO getCommoditySales(String sellerId) {
        //获取近七天不同商品的销售额
        ChartResponseVO chartResponseVO = new ChartResponseVO();
        List<String> xAxisData=new ArrayList<>();
        HashMap<String,List<Integer>> seriesData=new HashMap<>();
        //先获取当前用户的商品名
        QueryWrapper<GoodsDO> goodsQueryWrapper=new QueryWrapper();
        goodsQueryWrapper.eq("seller_id",sellerId);
        List<GoodsDO> goodsDOS = goodsMapper.selectList(goodsQueryWrapper);
        //特征标题值
        List<String> goodsNames = goodsDOS.stream().map(good -> {
            return good.getGoodsName();
        }).collect(Collectors.toList());
        //从每日销售记录表获取昨天记录
        QueryWrapper<GoodsEverydayDO> goodsEverydayDOQueryWrapper = new QueryWrapper<>();
        //从每日销售记录表获取昨天记录
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -7);
        Date yesterday = calendar.getTime();
        String oldTime = DateUtil.format(yesterday, "yyyy-MM-dd 00:00:00");
        goodsEverydayDOQueryWrapper.ge("create_time", oldTime).eq("seller_id",sellerId);
        //查询每一个商品的销售额
        for (String goodsName : goodsNames) {
            ArrayList<Integer> saleVaues = new ArrayList<>();
            goodsEverydayDOQueryWrapper.eq("goods_name", goodsName);
            List<GoodsEverydayDO> goodsEverydayDOS = goodsEverydayMapper.selectList(goodsEverydayDOQueryWrapper);
            for (GoodsEverydayDO goodsEverydayDO : goodsEverydayDOS) {
                //获取一间商品近七天的销售额
                Integer goodsBuyer = goodsEverydayDO.getGoodsBuyer();
                saleVaues.add(goodsBuyer);
                xAxisData.add(DateUtil.format(goodsEverydayDO.getCreateTime(), "MM-dd"));
            }
            seriesData.put(goodsName,saleVaues);
        }

        chartResponseVO.setXAxisData(xAxisData);
        chartResponseVO.setSeriesData(seriesData);
        String jsonString = JSONObject.toJSONString(chartResponseVO);
        redisUtils.set("CommoditySales@"+sellerId,jsonString,60*60*24);
        return chartResponseVO;
    }
}
