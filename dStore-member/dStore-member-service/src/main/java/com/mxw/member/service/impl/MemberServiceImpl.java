package com.mxw.member.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mxw.common.model.dto.BuyerLabelDTO;
import com.mxw.common.model.entity.ShopBuyerDO;
import com.mxw.common.model.vo.LabelVO;
import com.mxw.common.model.vo.PageVO;
import com.mxw.member.api.MemberService;
import com.mxw.member.convert.ShopBuyerConvert;
import com.mxw.member.dto.ShopBuyerDTO;
import com.mxw.member.entity.ShopBuyer;
import com.mxw.member.mapper.ShopMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    ShopMapper shopMapper;

    @Override
    public List<LabelVO> queryLabelByshopBuyerId(String shopBuyerId) {

        List<BuyerLabelDTO> buyerLabelDTOS = shopMapper.queryLabelByshopBuyerId(shopBuyerId);
        List<LabelVO> collect = buyerLabelDTOS.stream().map(dto -> {
            return new LabelVO(dto.getLabelName(), dto.getWeight());
        }).collect(Collectors.toList());

        return collect;
    }

    @Override
    public PageVO<ShopBuyerDTO> queryShopBuyerByPage(ShopBuyerDTO shopBuyerDTO) {
        //使用mybatis的分页插件
        QueryWrapper<ShopBuyer> queryWrapper = new QueryWrapper();
        if (ObjectUtil.isNotNull(shopBuyerDTO)) {
            queryWrapper.like(StringUtils.isNotBlank(shopBuyerDTO.getBuyerNick()), "buyer_nick", shopBuyerDTO.getBuyerNick())
                    .eq(StringUtils.isNotBlank(shopBuyerDTO.getReceiverMobile()), "buyer_nick", shopBuyerDTO.getReceiverMobile())
                    .like(StringUtils.isNotBlank(shopBuyerDTO.getReceiverName()), "receiver_name", shopBuyerDTO.getReceiverName());
        }
        List<ShopBuyer> shopBuyers = shopMapper.selectList(queryWrapper);
        List<ShopBuyerDTO> memberVOList = ShopBuyerConvert.INSTANCE.EntityToDTO(shopBuyers);
        return new PageVO<ShopBuyerDTO>(memberVOList.size(), 0, 0, memberVOList);
    }


    @Override
    public ShopBuyerDO getMemberDetailByShopBuyerId(String shopBuyerId) {
        ShopBuyer shopBuyer = shopMapper.selectOne(new QueryWrapper<ShopBuyer>().eq("shop_buyer_id", shopBuyerId));
        ShopBuyerDO shopBuyerDO = ShopBuyerConvert.INSTANCE.EntityToDetail(shopBuyer);
        return shopBuyerDO;
    }


    @Override
    public Map<Integer, List<ShopBuyerDTO>> queryMemberLevel(int level) {
        Map<Integer, List<ShopBuyerDTO>> map = new HashMap<>();
        switch (level) {
            case 0:
                map = getStoreMemberLevel();
                break;
            case 1:
                map = getMemberSpendingLevel();
                break;
            case 2:
                map = getMemberTransactionLevel();
                break;
        }
        return map;
    }

    @Override
    public PageVO<ShopBuyerDTO> queryShopBuyer() {
        List<ShopBuyer> shopBuyers = shopMapper.selectList(null);
        List<ShopBuyerDTO> memberVOList = ShopBuyerConvert.INSTANCE.EntityToDTO(shopBuyers);
        return new PageVO<ShopBuyerDTO>(memberVOList.size(), 0, 0, memberVOList);
    }

    /**
     * 获取会员消费次数等级
     *
     * @param
     */
    private Map<Integer, List<ShopBuyerDTO>> getMemberTransactionLevel() {
        //当前店铺的所有会员
        String sellerId = "50000082";
        Map<Integer, List<ShopBuyerDTO>> map = new HashMap<>();
        List<ShopBuyerDTO> list0 = new ArrayList<>();
        List<ShopBuyerDTO> list1 = new ArrayList<>();
        List<ShopBuyerDTO> list2 = new ArrayList<>();
        List<ShopBuyerDTO> list3 = new ArrayList<>();
        List<ShopBuyerDTO> list4 = new ArrayList<>();
        List<ShopBuyer> shopBuyerList = shopMapper.selectList(new LambdaQueryWrapper<ShopBuyer>().eq(ShopBuyer::getSellerId, sellerId));
        List<ShopBuyerDTO> shopBuyerDTOList = ShopBuyerConvert.INSTANCE.EntityToDTO(shopBuyerList);
        shopBuyerDTOList.stream().forEach(
                item -> {
                    int level = item.getBuyTotalCount().intValue();
                    if (level < 5) {
                        list0.add(item);
                    } else if (level > 5 && level < 50) {
                        list1.add(item);
                    } else if (level > 50 && level < 100) {
                        list2.add(item);
                    } else if (level > 100 && level < 500) {
                        list3.add(item);
                    } else if (level > 500) {
                        list4.add(item);
                    }
                }
        );
        map.put(0, list0);
        map.put(1, list1);
        map.put(2, list2);
        map.put(3, list3);
        map.put(4, list4);
        return map;
    }

    /**
     * 获取会员消费金额等级
     *
     * @param
     */
    private Map<Integer, List<ShopBuyerDTO>> getMemberSpendingLevel() {
        //当前店铺的所有会员
        String sellerId = "50000082";
        Map<Integer, List<ShopBuyerDTO>> map = new HashMap<>();
        List<ShopBuyerDTO> list0 = new ArrayList<>();
        List<ShopBuyerDTO> list1 = new ArrayList<>();
        List<ShopBuyerDTO> list2 = new ArrayList<>();
        List<ShopBuyerDTO> list3 = new ArrayList<>();
        List<ShopBuyerDTO> list4 = new ArrayList<>();
        List<ShopBuyer> shopBuyerList = shopMapper.selectList(new LambdaQueryWrapper<ShopBuyer>().eq(ShopBuyer::getSellerId, sellerId));
        List<ShopBuyerDTO> shopBuyerDTOList = ShopBuyerConvert.INSTANCE.EntityToDTO(shopBuyerList);
        shopBuyerDTOList.stream().forEach(
                item -> {
                    int level = item.getBuyTotalMoney().intValue();
                    if (level < 100) {
                        list0.add(item);
                    } else if (level > 100 && level < 1000) {
                        list1.add(item);
                    } else if (level > 1000 && level < 5000) {
                        list2.add(item);
                    } else if (level > 5000 && level < 10000) {
                        list3.add(item);
                    } else if (level > 10000) {
                        list4.add(item);
                    }
                }
        );

        map.put(0, list0);
        map.put(1, list1);
        map.put(2, list2);
        map.put(3, list3);
        map.put(4, list4);
        return map;
    }

    /**
     * 获取店铺默认会员等级
     *
     * @param
     */
    private Map<Integer, List<ShopBuyerDTO>> getStoreMemberLevel() {
        //当前店铺的所有会员
        String sellerId = "50000082";
        List<ShopBuyer> shopBuyerList = shopMapper.selectList(new LambdaQueryWrapper<ShopBuyer>().eq(ShopBuyer::getSellerId, sellerId));
        Map<Integer, List<ShopBuyerDTO>> collect = shopBuyerList.stream()
                .map(item -> ShopBuyerConvert.INSTANCE.EntityToDTO(item))
                .collect(Collectors.groupingBy(ShopBuyerDTO::getGrade));
        return collect;
    }


//    @Override
//    public void modifyMemberDetail(ShopBuyerDetail shopBuyerDetail) {
//        ShopBuyer shopBuyer = ShopBuyerConvert.INSTANCE.DetailToEntity(shopBuyerDetail);
//        shopMapper.updateById(shopBuyer);
//    }
//
//    @Override
//    public void addBlackList(String sellerId) {
//        UpdateWrapper<ShopBuyer> updateWrapper = new UpdateWrapper<>();
//        updateWrapper.eq("seller_id", sellerId);
//        updateWrapper.set("is_blacklist", "1");
//        shopMapper.update(null, updateWrapper);
//    }
//
//    @Override
//    public void addMemberDetail(ShopBuyerDetail shopBuyerDetail) {
//
//        ShopBuyer shopBuyer = ShopBuyerConvert.INSTANCE.DetailToEntity(shopBuyerDetail);
//        shopMapper.insert(shopBuyer);
//    }
//
//    @Override
//    public void uploadMembers(List<Object> list) {
//        for (Object o : list) {
//            ShopBuyerDetail shopBuyerDetail = (ShopBuyerDetail) o;
//            ShopBuyer shopBuyer = ShopBuyerConvert.INSTANCE.DetailToEntity(shopBuyerDetail);
//            shopMapper.insert(shopBuyer);
//        }
//    }
}
