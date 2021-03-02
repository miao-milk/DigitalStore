package com.mxw.analysis.service.impl;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mxw.analysis.api.ShopService;
import com.mxw.analysis.mapper.ShopMapper;
import com.mxw.common.model.entity.ShopBuyerDO;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    ShopMapper shopMapper;
    @Override
    public Integer selectCount(String sellerId) {
        QueryWrapper<ShopBuyerDO> wrapper = new QueryWrapper<>();
        String today = DateUtil.format(new Date(), "yyyy-MM-dd 00:00:00");
        wrapper.eq("seller_id", sellerId).ge("create_time",today);
        Integer integer = shopMapper.selectCount(wrapper);
        return integer;
    }

    @Override
    public List<ShopBuyerDO> queryShopBuyer(String sellerId) {
        QueryWrapper<ShopBuyerDO> wrapper = new QueryWrapper<ShopBuyerDO>().eq("seller_id", sellerId);
        List<ShopBuyerDO> shopBuyerDOS = shopMapper.selectList(wrapper);
        return shopBuyerDOS;
    }

    @Override
    public List<ShopBuyerDO> queryShopBuyerByPage(ShopBuyerDO shopBuyerDTO) {
        //使用mybatis的分页插件
        QueryWrapper<ShopBuyerDO> queryWrapper = new QueryWrapper();
        if (ObjectUtil.isNotNull(shopBuyerDTO)) {
            queryWrapper.like(StringUtils.isNotBlank(shopBuyerDTO.getBuyerNick()), "buyer_nick", shopBuyerDTO.getBuyerNick())
                    .eq(StringUtils.isNotBlank(shopBuyerDTO.getReceiverMobile()), "buyer_nick", shopBuyerDTO.getReceiverMobile())
                    .like(StringUtils.isNotBlank(shopBuyerDTO.getReceiverName()), "receiver_name", shopBuyerDTO.getReceiverName());
        }
        List<ShopBuyerDO> shopBuyerDOS = shopMapper.selectList(queryWrapper);
        return shopBuyerDOS;
    }

    @Override
    public ShopBuyerDO getMemberDetailByShopBuyerId(String shopBuyerId) {
        QueryWrapper<ShopBuyerDO> wrapper = new QueryWrapper<ShopBuyerDO>().eq("shop_buyer_id", shopBuyerId);
        ShopBuyerDO shopBuyerDO = shopMapper.selectOne(wrapper);
        return shopBuyerDO;
    }

    @Override
    public List<ShopBuyerDO> selectListBySellerId(String sellerId) {
        QueryWrapper<ShopBuyerDO> wrapper = new QueryWrapper<ShopBuyerDO>().eq("seller_id", sellerId);
        List<ShopBuyerDO> shopBuyerDOS = shopMapper.selectList(wrapper);
        return shopBuyerDOS;
    }
}
