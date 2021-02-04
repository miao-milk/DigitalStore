package com.mxw.member.convert;

import com.mxw.common.model.entity.ShopBuyerDO;
import com.mxw.member.dto.ShopBuyerDTO;
import com.mxw.member.entity.ShopBuyer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

//设置组件模型为Spring
@Mapper(componentModel = "spring")
public interface ShopBuyerConvert {
    //转换类实例
    ShopBuyerConvert INSTANCE= Mappers.getMapper(ShopBuyerConvert.class);

    //把ShopBuyer转为MemberVo
    ShopBuyerDTO EntityToDTO(ShopBuyer shopBuyer);
    //把ShopBuyerList转为MemberVoList
    List<ShopBuyerDTO> EntityToDTO(List<ShopBuyer> shopBuyer);

    //把ShopBuyer转为MemberVo
    ShopBuyerDO EntityToDetail(ShopBuyer shopBuyer);

    //把ShopBuyer转为MemberVo
    ShopBuyer DetailToEntity(ShopBuyerDO shopBuyer);
}
