package com.mxw.common.utils;

import com.mxw.common.model.entity.ShopBuyer;
import com.mxw.common.model.vo.MemberVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 对象转换：entity->vo
 *         entity->dto
 */
@Mapper
public interface ObjectConvert {
    //转换类实例
    ObjectConvert INSTANCE= Mappers.getMapper(ObjectConvert.class);

    //把ShopBuyer转为MemberVo
    MemberVO ShopBuyerEntityToMemberVO(ShopBuyer shopBuyer);
    //把ShopBuyerList转为MemberVoList
    List<MemberVO> ShopBuyerEntityToMemberVOList(List<ShopBuyer> shopBuyer);
}
