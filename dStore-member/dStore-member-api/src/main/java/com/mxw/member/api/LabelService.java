package com.mxw.member.api;

import com.mxw.common.model.entity.LabelEntity;
import com.mxw.common.utils.ResponseUtils;

import java.util.List;

/**
 * 用户标签管理
 */
public interface LabelService {


    List<LabelEntity> queryallLabel();

    void addLabel();
}
