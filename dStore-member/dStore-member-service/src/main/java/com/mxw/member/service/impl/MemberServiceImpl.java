package com.mxw.member.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mxw.common.model.dto.BuyerLabelDTO;
import com.mxw.common.model.entity.ShopBuyerDO;
import com.mxw.common.model.vo.LabelVO;
import com.mxw.common.model.vo.PageVO;
import com.mxw.member.api.MemberService;
import com.mxw.member.mapper.ShopMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    ShopMapper shopMapper;

    @Override
    public PageVO<ShopBuyerDO> queryShopBuyer() {
        List<ShopBuyerDO> shopBuyers = shopMapper.selectList(null);
        return new PageVO<ShopBuyerDO>(shopBuyers.size(), 0, 0, shopBuyers);
    }

    @Override
    public PageVO<ShopBuyerDO> queryShopBuyerByPage(ShopBuyerDO shopBuyerDTO) {
        //使用mybatis的分页插件
        QueryWrapper<ShopBuyerDO> queryWrapper = new QueryWrapper();
        if (ObjectUtil.isNotNull(shopBuyerDTO)) {
            queryWrapper.like(StringUtils.isNotBlank(shopBuyerDTO.getBuyerNick()), "buyer_nick", shopBuyerDTO.getBuyerNick())
                    .eq(StringUtils.isNotBlank(shopBuyerDTO.getReceiverMobile()), "buyer_nick", shopBuyerDTO.getReceiverMobile())
                    .like(StringUtils.isNotBlank(shopBuyerDTO.getReceiverName()), "receiver_name", shopBuyerDTO.getReceiverName());
        }
        List<ShopBuyerDO> shopBuyers = shopMapper.selectList(queryWrapper);
        return new PageVO<ShopBuyerDO>(shopBuyers.size(), 0, 0, shopBuyers);
    }


    @Override
    public ShopBuyerDO getMemberDetailByShopBuyerId(String shopBuyerId) {
        ShopBuyerDO shopBuyer = shopMapper.selectOne(new QueryWrapper<ShopBuyerDO>().eq("shop_buyer_id", shopBuyerId));
        return shopBuyer;
    }

    @Override
    public List<LabelVO> queryLabelByshopBuyerId(String shopBuyerId) {

        List<BuyerLabelDTO> buyerLabelDTOS = shopMapper.queryLabelByshopBuyerId(shopBuyerId);
        List<LabelVO> collect = buyerLabelDTOS.stream().map(dto -> {
            return new LabelVO(dto.getLabelName(), dto.getWeight());
        }).collect(Collectors.toList());
        return collect;
    }

}
