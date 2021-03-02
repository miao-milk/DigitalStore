package com.mxw.send.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mxw.common.model.entity.MessageDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SendMessageMapper extends BaseMapper<MessageDO> {

    @Update("UPDATE dStore_system.seller_info SET fee=fee-1  WHERE seller_id=#{sellerId}")
    void updateUserFee(Integer sellerId);
}
