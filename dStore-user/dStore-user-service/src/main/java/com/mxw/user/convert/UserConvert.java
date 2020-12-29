package com.mxw.user.convert;

import com.mxw.common.model.dto.UserDTO;
import com.mxw.common.model.entity.ShopBuyerDetail;
import com.mxw.user.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


//设置组件模型为Spring
@Mapper(componentModel = "spring")
public interface UserConvert {

    //转换类实例
    UserConvert INSTANCE= Mappers.getMapper(UserConvert.class);
    //把User转为UserDTO
    UserDTO EntityToDTO(User User);

    //把UserDTO转为User
    User DTOToEntity(UserDTO User);

}
