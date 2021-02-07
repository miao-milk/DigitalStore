package com.mxw.member.api;

import com.mxw.common.model.entity.ShopBuyerDO;
import com.mxw.common.model.vo.GroupDetailVO;
import com.mxw.common.model.vo.GroupVO;

import java.util.List;

/**
 * 用户分组
 */
public interface GroupService {

    //获取分组树结构
    List<GroupVO> getGroupTree(String sellerId);

    void addGroup(String sellerId, String content, String pid);

    void editGroup(String sellerId, String content, String id);

    void deleteGroup(String sellerId, String id);

    List<ShopBuyerDO> getGroupMember(String sellerId, String id);

    GroupDetailVO getGroupDetail(String sellerId, String id);

    void addGroupMember(String id,String shopBuyerId);
}
