package com.mxw.member.api;

import com.mxw.common.model.entity.LabelDO;
import com.mxw.common.model.vo.LabelVO;
import com.mxw.common.model.vo.PageVO;

import java.util.List;

/**
 * 用户标签管理
 */
public interface LabelService {


    List<LabelDO> queryallLabel(String sellerId);

    List<LabelDO> queryLabelByParams(LabelDO labelDO);

    void addLabel(String sellerId,String labelContent);

    void deleteLabel(String[] ids);

}
