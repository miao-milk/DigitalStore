package com.mxw.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mxw.common.model.entity.BuyerLabelEntity;
import com.mxw.common.model.entity.LabelEntity;
import com.mxw.member.api.LabelService;
import com.mxw.member.mapper.BuyerLabelLinkMapper;
import com.mxw.member.mapper.LabelMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;


@Service
public class LabelServiceImpl  implements LabelService {

    @Autowired
    LabelMapper labelMapper;

    @Autowired
    BuyerLabelLinkMapper buyerLabelLinkMapper;

    @Override
    public List<LabelEntity> queryallLabel() {
        List<LabelEntity> labelList = labelMapper.selectList(null);
        return labelList;
    }

    @Override
    public void addLabel(String labelContent) {
        //创建标签实体类
        LabelEntity labelEntity = new LabelEntity();
        labelEntity.setLabelName(labelContent);
        labelEntity.setCreateTime(new Date());
        labelMapper.insert(labelEntity);
    }


    @Override
    public List<LabelEntity> queryLabelBySellerId(String sellerId) {

        List<LabelEntity> OweLabelList = labelMapper.selectListBySellerId(sellerId);
        return OweLabelList;
    }

    @Override
    public void addLabelBySellerId(String sellerId, String labelId) {
        //首先构建中间关系表entity
        BuyerLabelEntity buyerLabelEntity=new BuyerLabelEntity();
        buyerLabelEntity.setCreateTime(new Date());
        buyerLabelEntity.setSellerId(Integer.parseInt(sellerId));
        buyerLabelEntity.setLabelId(Integer.parseInt(labelId));
        //标识用户标签
        buyerLabelLinkMapper.insert(buyerLabelEntity);
    }

    @Override
    public void deleteLabelBySellerId(String sellerId, String labelId) {
        buyerLabelLinkMapper.delete(new LambdaQueryWrapper<BuyerLabelEntity>().eq(BuyerLabelEntity::getLabelId,labelId).eq(BuyerLabelEntity::getSellerId,sellerId));
    }
}