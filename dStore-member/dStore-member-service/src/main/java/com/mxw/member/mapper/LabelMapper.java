package com.mxw.member.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mxw.common.model.dto.BuyerLabelDTO;
import com.mxw.common.model.entity.LabelDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


import java.util.List;

@Mapper
public interface LabelMapper extends BaseMapper<LabelDO> {

    @Select("SELECT * FROM shop_buyer_label sbl LEFT JOIN shop_label sl ON sbl.label_id = sl.label_id WHERE sl.seller_id=#{sellerId}")
    List<BuyerLabelDTO> selectListBySellerId(@Param("sellerId") String sellerId);


}
