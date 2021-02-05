package com.mxw.member.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.util.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mxw.common.model.dto.BuyerLabelDTO;
import com.mxw.common.model.entity.BuyerLabelDO;
import com.mxw.common.model.entity.LabelDO;
import com.mxw.common.model.entity.ShopBuyerDO;
import com.mxw.common.model.vo.LabelVO;
import com.mxw.common.model.vo.PageVO;
import com.mxw.member.api.LabelService;
import com.mxw.member.mapper.BuyerLabelLinkMapper;
import com.mxw.member.mapper.LabelMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class LabelServiceImpl  implements LabelService {

    @Autowired
    LabelMapper labelMapper;

    @Autowired
    BuyerLabelLinkMapper buyerLabelLinkMapper;

    @Override
    public List<LabelDO> queryallLabel(String sellerId) {
        //获取该用户的下的标签列表
        List<BuyerLabelDTO> buyerLabelDTOS = labelMapper.selectListBySellerId(sellerId);
        List<LabelDO> collect = buyerLabelDTOS.stream().map(dto -> {
            LabelDO labelDO = new LabelDO();
            labelDO.setLabelId(dto.getLabelId());
            labelDO.setCreateTime(dto.getCreateTime());
            labelDO.setLabelName(dto.getLabelName());
            return labelDO;
        }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public List<LabelDO> queryLabelByParams(LabelDO labelDO) {
        //使用mybatis的分页插件
        QueryWrapper<LabelDO> queryWrapper = new QueryWrapper();
        if (ObjectUtil.isNotNull(labelDO)) {
            queryWrapper.like(StringUtils.isNotBlank(labelDO.getLabelName()), "label_name", labelDO.getLabelName());
        }
        List<LabelDO> labelDOS = labelMapper.selectList(queryWrapper);
        return labelDOS;
    }

    @Override
    public void addLabel(String sellerId,String labelContent) {
        //创建标签实体类
        LabelDO labelDO = new LabelDO();
        labelDO.setLabelName(labelContent);
        labelDO.setCreateTime(new Date());
        labelDO.setSellerId(Integer.parseInt(sellerId));
        labelMapper.insert(labelDO);
    }


    @Override
    public void deleteLabel(String[] ids) {
        List list = CollectionUtils.arrayToList(ids);
        for (Object o : list) {
            Integer id=Integer.parseInt(o+"");
            //首先删除该标签下的用户
            QueryWrapper<BuyerLabelDO> wrapper = new QueryWrapper<>();
            wrapper.eq("label_id",id);
            buyerLabelLinkMapper.delete(wrapper);
            //再删除标签
            labelMapper.deleteById(id);
        }
        System.out.println("删除成功");
    }

}