package com.mxw.member.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mxw.common.model.param.MemberParam;
import com.mxw.common.model.vo.MemberVO;
import com.mxw.common.model.vo.PageVO;
import com.mxw.member.convert.ShopBuyerConvert;
import com.mxw.member.mapper.ShopMapper;
import com.mxw.member.entity.ShopBuyer;
import com.mxw.member.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;


@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    ShopMapper shopMapper;

    @Override
    public PageVO<MemberVO> queryPage(MemberParam memberParam) {

//        //使用mybatis的分页插件
//        IPage<ShopBuyer> page = new Page();
//        page.setPages(memberParam.getPage());
//        page.setSize(memberParam.getPageSize());
//        LambdaQueryWrapper<ShopBuyer> lambdaQueryWrapper = new LambdaQueryWrapper();
////        if (ObjectUtil.isNotNull(memberParam)){
////            lambdaQueryWrapper.like(StringUtils.isNotBlank(memberParam.getBuyerNick()),ShopBuyer::getBuyerNick,memberParam.getBuyerNick())
////                               .eq(StringUtils.isNotBlank(memberParam.getReceiverMobile()),ShopBuyer::getReceiverMobile,memberParam.getReceiverMobile());
////        }
//        IPage<ShopBuyer> buyerIPage = shopMapper.selectPage(page, null);
//        long total = buyerIPage.getTotal();
//        long current = buyerIPage.getCurrent();
//        long size = buyerIPage.getSize();
//        if (buyerIPage.getTotal()>0){
//            List<MemberVO> memberVOList = ShopBuyerConvert.INSTANCE.EntityToDTO(buyerIPage.getRecords());
//            return new PageVO<MemberVO>(total,current,size,memberVOList);
//        }
        return null;
    }

    @Override
    public List<ShopBuyer> queryAll() {
        List<ShopBuyer> shopBuyers = shopMapper.selectList(null);
        return shopBuyers;
    }


}