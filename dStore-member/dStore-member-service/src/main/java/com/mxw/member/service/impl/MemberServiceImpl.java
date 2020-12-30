package com.mxw.member.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mxw.common.model.entity.ShopBuyerDetail;
import com.mxw.common.model.vo.PageVO;
import com.mxw.member.api.MemberService;
import com.mxw.member.convert.ShopBuyerConvert;
import com.mxw.member.dto.ShopBuyerDTO;
import com.mxw.member.entity.ShopBuyer;
import com.mxw.member.mapper.ShopMapper;
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
    public PageVO<ShopBuyerDTO> queryShopBuyerByPage(ShopBuyerDTO shopBuyerDTO, Integer pageNo,Integer pageSize) {
        //使用mybatis的分页插件
        IPage<ShopBuyer> page = new Page();
        page.setPages(pageNo);
        page.setSize(pageSize);
        LambdaQueryWrapper<ShopBuyer> lambdaQueryWrapper = new LambdaQueryWrapper();
//        if (ObjectUtil.isNotNull(shopBuyerDTO)){
//            lambdaQueryWrapper.like(StringUtils.isNotBlank(shopBuyerDTO.getBuyerNick()),ShopBuyer::getBuyerNick,shopBuyerDTO.getBuyerNick())
//                               .eq(StringUtils.isNotBlank(shopBuyerDTO.getReceiverMobile()),ShopBuyer::getReceiverMobile,shopBuyerDTO.getReceiverMobile());
//        }
        IPage<ShopBuyer> buyerIPage = shopMapper.selectPage(page, null);
        long total = buyerIPage.getTotal();
        long current = buyerIPage.getCurrent();
        long size = buyerIPage.getSize();
        if (buyerIPage.getTotal()>0){
            List<ShopBuyerDTO> memberVOList = ShopBuyerConvert.INSTANCE.EntityToDTO(buyerIPage.getRecords());
            return new PageVO<ShopBuyerDTO>(total,current,size,memberVOList);
        }
        return new PageVO<ShopBuyerDTO>(0,current,size,new ArrayList<>());
    }

    @Override
    public void addBlackList(String sellerId) {
        UpdateWrapper<ShopBuyer> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("seller_id",sellerId);
        updateWrapper.set("is_blacklist","1");
        shopMapper.update(null, updateWrapper);
    }

    @Override
    public ShopBuyerDetail getMemberDetailBySellerId(String sellerId) {
        ShopBuyer shopBuyer = shopMapper.selectOne(new LambdaQueryWrapper<ShopBuyer>().eq(ShopBuyer::getSellerId, sellerId));
        ShopBuyerDetail shopBuyerDetail = ShopBuyerConvert.INSTANCE.EntityToDetail(shopBuyer);
        return shopBuyerDetail;
    }

    @Override
    public void modifyMemberDetail(ShopBuyerDetail shopBuyerDetail) {
        ShopBuyer shopBuyer = ShopBuyerConvert.INSTANCE.DetailToEntity(shopBuyerDetail);
        shopMapper.updateById(shopBuyer);
    }

    @Override
    public void addMemberDetail(ShopBuyerDetail shopBuyerDetail) {

        ShopBuyer shopBuyer = ShopBuyerConvert.INSTANCE.DetailToEntity(shopBuyerDetail);
        shopMapper.insert(shopBuyer);
    }

    @Override
    public void uploadMembers(List<Object> list) {
        for (Object o : list) {
            ShopBuyerDetail shopBuyerDetail=(ShopBuyerDetail)o;
            ShopBuyer shopBuyer = ShopBuyerConvert.INSTANCE.DetailToEntity(shopBuyerDetail);
            shopMapper.insert(shopBuyer);
        }
    }

    @Override
    public Map<Integer,List<ShopBuyerDTO>> queryMemberLevel(int level) {
        Map<Integer, List<ShopBuyerDTO>> map=new HashMap<>();
        switch (level){
            case 0:
                map = getStoreMemberLevel();
                break;
            case 1:
                map =getMemberSpendingLevel();
                break;
            case 2:
                map =getMemberTransactionLevel();
                break;
        }
        return map;
    }

    /**
     * 获取会员消费次数等级
     * @param
     */
    private Map<Integer, List<ShopBuyerDTO>> getMemberTransactionLevel() {
        //当前店铺的所有会员
        String sellerId="50000082";
        Map<Integer, List<ShopBuyerDTO>> map=new HashMap<>();
        List<ShopBuyerDTO> list0=new ArrayList<>();
        List<ShopBuyerDTO> list1=new ArrayList<>();
        List<ShopBuyerDTO> list2=new ArrayList<>();
        List<ShopBuyerDTO> list3=new ArrayList<>();
        List<ShopBuyerDTO> list4=new ArrayList<>();
        List<ShopBuyer> shopBuyerList = shopMapper.selectList(new LambdaQueryWrapper<ShopBuyer>().eq(ShopBuyer::getSellerId, sellerId));
        List<ShopBuyerDTO> shopBuyerDTOList = ShopBuyerConvert.INSTANCE.EntityToDTO(shopBuyerList);
        shopBuyerDTOList.stream().forEach(
                item->{
                    int level = item.getBuyTotalCount().intValue();
                    if (level<5){
                        list0.add(item);
                    }else if (level>5&&level<50){
                        list1.add(item);
                    }else if (level>50&&level<100){
                        list2.add(item);
                    }else if (level>100&&level<500){
                        list3.add(item);
                    }else if (level>500){
                        list4.add(item);
                    }
                }
        );
        map.put(0,list0);
        map.put(1,list1);
        map.put(2,list2);
        map.put(3,list3);
        map.put(4,list4);
        return map;
    }

    /**
     * 获取会员消费金额等级
     * @param
     */
    private Map<Integer, List<ShopBuyerDTO>> getMemberSpendingLevel() {
        //当前店铺的所有会员
        String sellerId="50000082";
        Map<Integer, List<ShopBuyerDTO>> map=new HashMap<>();
        List<ShopBuyerDTO> list0=new ArrayList<>();
        List<ShopBuyerDTO> list1=new ArrayList<>();
        List<ShopBuyerDTO> list2=new ArrayList<>();
        List<ShopBuyerDTO> list3=new ArrayList<>();
        List<ShopBuyerDTO> list4=new ArrayList<>();
        List<ShopBuyer> shopBuyerList = shopMapper.selectList(new LambdaQueryWrapper<ShopBuyer>().eq(ShopBuyer::getSellerId, sellerId));
        List<ShopBuyerDTO> shopBuyerDTOList = ShopBuyerConvert.INSTANCE.EntityToDTO(shopBuyerList);
        shopBuyerDTOList.stream().forEach(
                item->{
                    int level = item.getBuyTotalMoney().intValue();
                    if (level<100){
                        list0.add(item);
                    }else if (level>100&&level<1000){
                        list1.add(item);
                    }else if (level>1000&&level<5000){
                        list2.add(item);
                    }else if (level>5000&&level<10000){
                        list3.add(item);
                    }else if (level>10000){
                        list4.add(item);
                    }
                }
        );

        map.put(0,list0);
        map.put(1,list1);
        map.put(2,list2);
        map.put(3,list3);
        map.put(4,list4);
        return map;
    }

    /**
     * 获取店铺默认会员等级
     * @param
     */
    private  Map<Integer, List<ShopBuyerDTO>> getStoreMemberLevel() {
        //当前店铺的所有会员
        String sellerId="50000082";
        List<ShopBuyer> shopBuyerList = shopMapper.selectList(new LambdaQueryWrapper<ShopBuyer>().eq(ShopBuyer::getSellerId, sellerId));
        Map<Integer, List<ShopBuyerDTO>> collect = shopBuyerList.stream()
                .map(item -> ShopBuyerConvert.INSTANCE.EntityToDTO(item))
                .collect(Collectors.groupingBy(ShopBuyerDTO::getGrade));
        return collect;
    }
}
