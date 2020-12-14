package com.mxw.member.convert;

import com.mxw.common.model.vo.MemberVO;
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
    MemberVO ShopBuyerEntityToMemberVO(ShopBuyer shopBuyer);
    //把ShopBuyerList转为MemberVoList
    List<MemberVO> ShopBuyerEntityToMemberVOList(List<ShopBuyer> shopBuyer);
}
