package com.mxw.message.api;

import com.mxw.common.model.entity.MessageDO;

/**
 * 发送短信
 */
public interface SendMessageService {
    Boolean sendMessage(MessageDO messageDO);
}
