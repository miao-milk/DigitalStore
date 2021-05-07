package com.mxw.send.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mxw.common.model.entity.MessageDO;
import com.mxw.common.model.vo.PageVO;
import com.mxw.message.api.SendMessageService;
import com.mxw.send.mapper.SendMessageMapper;
import com.mxw.send.utils.SendMessageUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@Service
public class SendMessageServiceImpl implements SendMessageService {
    @Autowired
    SendMessageMapper sendMessageMapper;


    @Override
    public Boolean sendMessage(MessageDO messageDO) {
        SendMessageUtils sendMessageUtils = new SendMessageUtils();
        String url = "http://mt.yusms.com";
        String userName = "lxw";
        String content = "【接口测试】" + messageDO.getContent();
        String mobile = messageDO.getMobile();
        messageDO.setSendTime(new Date());
        this.sendMessageMapper.insert(messageDO);
        boolean send = sendMessageUtils.send(content, mobile, url, userName, "password");

        //boolean send = sendMessageUtils.send(content, mobile, url, userName, "LXW@lxw1379");
        System.out.println(send);
        Integer sellerId = messageDO.getSellerId();
        //扣费
        sendMessageMapper.updateUserFee(sellerId);
        return send;
    }

    @Override
    public PageVO<MessageDO> getAllMessageRecord(String sellerId) {
        QueryWrapper<MessageDO> wrapper = (QueryWrapper) (new QueryWrapper()).eq("seller_id", sellerId);
        List<MessageDO> messageDOS = this.sendMessageMapper.selectList(wrapper);
        return new PageVO((long) messageDOS.size(), 0L, 0L, messageDOS);
    }

    @Override
    public PageVO<MessageDO> queryAllMemberByParam(MessageDO messageDO, String sellerId) {
        QueryWrapper<MessageDO> queryWrapper = new QueryWrapper();
        if (ObjectUtil.isNotNull(messageDO)) {
            ((QueryWrapper) queryWrapper.like(StringUtils.isNotBlank(messageDO.getMobile()), "mobile", messageDO.getMobile()))
                    .like(StringUtils.isNotBlank(messageDO.getContent()), "content", messageDO.getContent()).eq("seller_id", sellerId);
        }

        List<MessageDO> messageDOS = this.sendMessageMapper.selectList(queryWrapper);
        return new PageVO((long) messageDOS.size(), 0L, 0L, messageDOS);
    }
}
