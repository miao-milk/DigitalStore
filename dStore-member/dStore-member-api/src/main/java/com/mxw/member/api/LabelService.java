package com.mxw.member.api;

import com.mxw.common.model.entity.LabelDO;
import com.mxw.common.model.vo.LabelVO;

import java.util.List;

/**
 * 用户标签管理
 */
public interface LabelService {


    List<LabelDO> queryallLabel(String sellerId);

    void addLabel(String labelContent);

    void deleteLabel(String[] ids);

}
