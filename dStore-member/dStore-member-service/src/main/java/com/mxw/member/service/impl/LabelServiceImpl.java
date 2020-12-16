package com.mxw.member.service.impl;

import com.mxw.common.model.entity.LabelEntity;
import com.mxw.member.api.LabelService;
import com.mxw.member.mapper.LabelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("labelService")
public class LabelServiceImpl  implements LabelService {

    @Autowired
    LabelMapper labelMapper;

    @Override
    public List<LabelEntity> queryallLabel() {
        List<LabelEntity> labelList = labelMapper.selectList(null);
        return labelList;
    }

    @Override
    public void addLabel() {

    }
}