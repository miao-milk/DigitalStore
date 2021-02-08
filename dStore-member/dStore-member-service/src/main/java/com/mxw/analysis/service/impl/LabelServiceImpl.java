package com.mxw.analysis.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.util.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mxw.common.enumCode.CommonErrorCode;
import com.mxw.common.exception.MyException;
import com.mxw.common.model.entity.BuyerLabelDO;
import com.mxw.common.model.entity.LabelDO;
import com.mxw.analysis.api.LabelService;
import com.mxw.analysis.mapper.BuyerLabelLinkMapper;
import com.mxw.analysis.mapper.LabelMapper;
import org.apache.commons.lang3.StringUtils;
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
    public List<LabelDO> queryallLabel(String sellerId) {
        //获取该用户的下的标签列表
        QueryWrapper<LabelDO> queryWrapper = new QueryWrapper();
        queryWrapper.eq("seller_id",sellerId);
        List<LabelDO> collect = labelMapper.selectList(queryWrapper);
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
        if (StringUtils.isBlank(labelContent)){
            throw new MyException(CommonErrorCode.ERROR_CODE_10008);
        }
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