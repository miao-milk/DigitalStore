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
import com.mxw.common.model.vo.NewOldBuyerCompareVO;
import com.mxw.common.model.vo.RadarDataVO;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

@Service
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
         Set<String> indicators=new HashSet<>();
        //销售特征值
         HashMap<String,List<Integer>> seriesDataList=new HashMap<>();
        //喜爱特征
         Set<String> favoriteFeature=new HashSet<>();
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
        //从每日销售记录表获取昨天记录
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -1);
        Date yesterday = calendar.getTime();
        String oldTime = DateUtil.format(yesterday, "yyyy-MM-dd 00:00:00");
        goodsEverydayQueryWrapper.eq("seller_id", sellerId).ge("create_time", oldTime).in("goods_name",legendTitle);
        List<GoodsEverydayDO> goodsEverydayDOS = goodsEverydayMapper.selectList(goodsEverydayQueryWrapper);
        for (GoodsEverydayDO goodsEverydayDO : goodsEverydayDOS) {
            //List<GoodsEverydayDO.GoodsFeture> goodsSalesFetures = goodsEverydayDO.getGoodsSalesFetures();
            //List<GoodsEverydayDO.GoodsFeture> goodsLikeFetures = goodsEverydayDO.getGoodsLikeFetures();
            String goodsSalesFetures = goodsEverydayDO.getGoodsSalesFetures();
            String goodsLikeFetures = goodsEverydayDO.getGoodsLikeFetures();
            List goodsSalesFeturesList = JSONObject.parseObject(goodsSalesFetures,List.class);
            List goodsLikeFeturesList = JSONObject.parseObject(goodsLikeFetures,List.class);
            //销售特征
            ArrayList<Integer> values = new ArrayList<>();
            for (Object o : goodsSalesFeturesList) {
                Map map=(Map) o;
                Set set = map.keySet();
                Collection values1 = map.values();
                indicators.addAll(set);
                values.addAll(values1);
            }
            seriesDataList.put(goodsEverydayDO.getGoodsName(),values);
            //喜爱特征
            ArrayList<Integer> likeValues = new ArrayList<>();
            for (Object o  : goodsLikeFeturesList) {
                Map map=(Map) o;
                Set set = map.keySet();
                Collection values1 = map.values();
                //GoodsEverydayDO.GoodsFeture goodsDO=JSONObject.parseObject(string, GoodsEverydayDO.GoodsFeture.class);
                favoriteFeature.addAll(set);
                likeValues.addAll(values1);
            }
            favoriteFeatureDataList.put(goodsEverydayDO.getGoodsName(),likeValues);
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
        TreeSet<String> set=new TreeSet<>();
        //先获取当前用户的商品名
        QueryWrapper<GoodsDO> goodsQueryWrapper=new QueryWrapper();
        goodsQueryWrapper.eq("seller_id",sellerId);
        List<GoodsDO> goodsDOS = goodsMapper.selectList(goodsQueryWrapper);
        //特征标题值
        List<String> goodsNames = goodsDOS.stream().map(good -> {
            return good.getGoodsName();
        }).collect(Collectors.toList());

        //查询每一个商品的销售额
        for (String goodsName : goodsNames) {
            //从每日销售记录表获取昨天记录
            QueryWrapper<GoodsEverydayDO> goodsEverydayDOQueryWrapper = new QueryWrapper<>();
            //从每日销售记录表获取昨天记录
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DATE, -7);
            Date yesterday = calendar.getTime();
            String oldTime = DateUtil.format(yesterday, "yyyy-MM-dd 00:00:00");
            goodsEverydayDOQueryWrapper.ge("create_time", oldTime).eq("seller_id",sellerId);
            ArrayList<Integer> saleVaues = new ArrayList<>();
            goodsEverydayDOQueryWrapper.eq("goods_name", goodsName);
            List<GoodsEverydayDO> goodsEverydayDOS = goodsEverydayMapper.selectList(goodsEverydayDOQueryWrapper);
            for (GoodsEverydayDO goodsEverydayDO : goodsEverydayDOS) {
                //获取一间商品近七天的销售额
                Integer goodsBuyer = goodsEverydayDO.getGoodsBuyer();
                saleVaues.add(goodsBuyer);
                set.add(DateUtil.format(goodsEverydayDO.getCreateTime(), "MM-dd"));
            }
            seriesData.put(goodsName,saleVaues);
        }
        xAxisData.addAll(set);
        chartResponseVO.setXAxisData(xAxisData);
        chartResponseVO.setSeriesData(seriesData);
        String jsonString = JSONObject.toJSONString(chartResponseVO);
        redisUtils.set("CommoditySales@"+sellerId,jsonString,60*60*24);
        return chartResponseVO;
    }
}
