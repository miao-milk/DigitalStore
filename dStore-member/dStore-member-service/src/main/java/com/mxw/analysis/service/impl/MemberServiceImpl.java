package com.mxw.analysis.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mxw.common.enumCode.CommonErrorCode;
import com.mxw.common.exception.MyException;
import com.mxw.common.model.dto.BuyerLabelDTO;
import com.mxw.common.model.dto.NewTradeUsersDTO;
import com.mxw.common.model.entity.BuyerLabelDO;
import com.mxw.common.model.entity.LabelDO;
import com.mxw.common.model.entity.ShopBuyerDO;
import com.mxw.common.model.entity.TradeEverydayDO;
import com.mxw.common.model.vo.LabelVO;
import com.mxw.common.model.vo.MemberConsumptionLevelVO;
import com.mxw.common.model.vo.PageVO;
import com.mxw.analysis.api.MemberService;
import com.mxw.analysis.mapper.BuyerLabelLinkMapper;
import com.mxw.analysis.mapper.LabelMapper;
import com.mxw.analysis.mapper.ShopMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    ShopMapper shopMapper;
    @Autowired
    LabelMapper labelMapper;
    @Autowired
    BuyerLabelLinkMapper buyerLabelLinkMapper;

    @Override
    public PageVO<ShopBuyerDO> queryShopBuyer(String sellerId) {
        QueryWrapper<ShopBuyerDO> wrapper = new QueryWrapper<ShopBuyerDO>().eq("seller_id", sellerId);
        List<ShopBuyerDO> shopBuyers = shopMapper.selectList(wrapper);
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

    @Override
    public MemberConsumptionLevelVO queryMemberConsumptionLevel(String sellerId) {
        QueryWrapper<ShopBuyerDO> wrapper = new QueryWrapper<ShopBuyerDO>().eq("seller_id", sellerId);
        List<ShopBuyerDO> shopBuyers = shopMapper.selectList(wrapper);
        //找出高级，中级,普通人群
        int seniorNumber = 0, intermediateNumber = 0, ordinaryNumber = 0;
        BigDecimal SeniorAmount = new BigDecimal("0");
        BigDecimal intermediateAmount = new BigDecimal("0");
        BigDecimal normalAmount = new BigDecimal("0");
        BigDecimal decimal1 = new BigDecimal("10000");
        BigDecimal decimal2 = new BigDecimal("1000");
        /**
         * if(a.compareTo(b) > -1){
         *     System.out.println("a大于等于b");
         * }
         * if(a.compareTo(b) < 1){
         *     System.out.println("a小于等于b");
         * }
         */
        for (ShopBuyerDO shopBuyer : shopBuyers) {
            if (shopBuyer.getBuyTotalMoney().compareTo(decimal1) > -1) {
                //大于10000的属于高级人群
                seniorNumber++;
                SeniorAmount = shopBuyer.getBuyTotalMoney().add(SeniorAmount);
            }
            if (shopBuyer.getBuyTotalMoney().compareTo(decimal1) < 1 && shopBuyer.getBuyTotalMoney().compareTo(decimal2) > -1) {
                //小于10000大于1000的属于中级人群
                intermediateNumber++;
                intermediateAmount = shopBuyer.getBuyTotalMoney().add(intermediateAmount);
            }
            if (shopBuyer.getBuyTotalMoney().compareTo(decimal2) < 1) {
                //小于1000属于普通人群
                ordinaryNumber++;
                normalAmount = shopBuyer.getBuyTotalMoney().add(normalAmount);
            }
        }
        MemberConsumptionLevelVO memberConsumptionLevelVO = new MemberConsumptionLevelVO();
        memberConsumptionLevelVO.setPeopleTotal(shopBuyers.size());
        memberConsumptionLevelVO.setSeniorNumber(seniorNumber);
        memberConsumptionLevelVO.setIntermediateNumber(intermediateNumber);
        memberConsumptionLevelVO.setOrdinaryNumber(ordinaryNumber);

        memberConsumptionLevelVO.setSeniorAmount(SeniorAmount);
        memberConsumptionLevelVO.setIntermediateAmount(intermediateAmount);
        memberConsumptionLevelVO.setOrdinaryAmount(normalAmount);

        return memberConsumptionLevelVO;
    }

    @Override
    public void addLabelByshopBuyerId(String sellerId,String shopBuyerId, String labelContent) {
        //先往标签库中添加标签
        if (StringUtils.isBlank(labelContent)){
            throw new MyException(CommonErrorCode.ERROR_CODE_10008);
        }
        LabelDO labelDO = new LabelDO();
        labelDO.setSellerId(Integer.parseInt(sellerId));
        labelDO.setLabelName(labelContent);
        labelDO.setCreateTime(new Date());
        labelMapper.insert(labelDO);
        QueryWrapper<LabelDO> wrapper = new QueryWrapper<>();
        wrapper.eq("label_name", labelContent).eq("seller_id", sellerId);
        LabelDO one = labelMapper.selectOne(wrapper);
        //再往关联表进行关联
        BuyerLabelDO buyerLabelDO = new BuyerLabelDO();
        buyerLabelDO.setCreateTime(new Date());
        buyerLabelDO.setShopBuyerId(Integer.parseInt(shopBuyerId));
        buyerLabelDO.setLabelId(one.getLabelId());
        buyerLabelLinkMapper.insert(buyerLabelDO);
    }

    @Override
    public void deleteLabelByshopBuyerId(String sellerId, String shopBuyerId, String labelContent) {
        //先查询标签获取id
        QueryWrapper<LabelDO> wrapper = new QueryWrapper<>();
        wrapper.eq("label_name", labelContent).eq("seller_id", sellerId);
        LabelDO one = labelMapper.selectOne(wrapper);
        Integer labelId = one.getLabelId();
        labelMapper.deleteById(labelId);
        QueryWrapper<BuyerLabelDO> buyerLabelDOWrapper = new QueryWrapper<>();
        buyerLabelDOWrapper.eq("label_id", labelId).eq("shop_buyer_id", shopBuyerId);
        buyerLabelLinkMapper.delete(buyerLabelDOWrapper);
    }

    @Override
    public int getTodayUser(String sellerId) {
        QueryWrapper<ShopBuyerDO> wrapper = new QueryWrapper<>();
        String today = DateUtil.format(new Date(), "yyyy-MM-dd 00:00:00");
        wrapper.eq("seller_id", sellerId).ge("create_time",today);
        Integer integer = shopMapper.selectCount(wrapper);
        return integer;
    }

}
