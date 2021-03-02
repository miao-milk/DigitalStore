package com.mxw.job.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mxw.common.model.entity.GoodsDO;
import com.mxw.job.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    public List<GoodsDO> getGoodList(String sellerId){
        List<GoodsDO> goodsDOList = goodsMapper.selectList(new QueryWrapper<GoodsDO>().eq("seller_id", sellerId));

        return goodsDOList;
    }
}
