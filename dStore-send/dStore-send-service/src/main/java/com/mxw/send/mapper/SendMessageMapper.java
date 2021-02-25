package com.mxw.send.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mxw.common.model.entity.MessageDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SendMessageMapper extends BaseMapper<MessageDO> {
}
