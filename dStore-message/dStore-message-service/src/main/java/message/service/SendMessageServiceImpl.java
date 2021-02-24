package message.service;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mxw.common.model.entity.MessageDO;
import com.mxw.common.model.vo.PageVO;
import com.mxw.message.api.SendMessageService;
import message.mapper.SendMessageMapper;
import message.utils.SendMessageUtils;
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
        String url="http://mt.yusms.com";
        String userName="lxw";
//        String mobiles="13450476669";
//        String content="【测试店铺】测试店铺营销信息，大优惠等你来选。回T退订";
//        String password="LXW@lxw1379";
        String content = messageDO.getContent();
        String mobile = messageDO.getMobile();
        messageDO.setSendTime(new Date());
        sendMessageMapper.insert(messageDO);
        boolean send = sendMessageUtils.send(content, mobile, url, userName, "password");
        System.out.println(send);
        return send;
    }

    @Override
    public PageVO<MessageDO> getAllMessageRecord(String sellerId) {
        QueryWrapper<MessageDO> wrapper = new QueryWrapper<MessageDO>().eq("seller_id", sellerId);
        List<MessageDO> messageDOS = sendMessageMapper.selectList(wrapper);
        return new PageVO<MessageDO>(messageDOS.size(), 0, 0, messageDOS);
    }

    @Override
    public PageVO<MessageDO> queryAllMemberByParam(MessageDO messageDO) {
        //使用mybatis的分页插件
        QueryWrapper<MessageDO> queryWrapper = new QueryWrapper();
        if (ObjectUtil.isNotNull(messageDO)) {
            queryWrapper.like(StringUtils.isNotBlank(messageDO.getMobile()), "mobile", messageDO.getMobile())
                    .like(StringUtils.isNotBlank(messageDO.getContent()), "content", messageDO.getContent());
        }
        List<MessageDO> messageDOS = sendMessageMapper.selectList(queryWrapper);
        return new PageVO<MessageDO>(messageDOS.size(), 0, 0, messageDOS);
    }
}
