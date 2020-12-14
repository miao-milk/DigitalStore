package com.mxw.member.service.impl;

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
import java.util.List;


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
}
