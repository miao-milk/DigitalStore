package com.mxw.message.api;

import com.mxw.common.model.entity.MessageDO;
import com.mxw.common.model.vo.PageVO;

/**
 * 发送短信
 */
public interface SendMessageService {
    Boolean sendMessage(MessageDO messageDO);

    PageVO<MessageDO> getAllMessageRecord(String sellerId);

    PageVO<MessageDO> queryAllMemberByParam(MessageDO messageDO);
}
