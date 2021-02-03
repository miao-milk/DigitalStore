package com.mxw.member.api;

import com.mxw.common.model.entity.LabelEntity;

import java.util.List;

/**
 * 用户标签管理
 */
public interface LabelService {


    List<LabelEntity> queryallLabel();

    void addLabel(String labelContent);

    List<LabelEntity> queryLabelBySellerId(String sellerId);

    void addLabelBySellerId(String sellerId, String labelId);

    void deleteLabelBySellerId(String sellerId, String labelId);

    void deleteLabel(String[] ids);
}
