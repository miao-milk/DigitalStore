package com.mxw.common.model.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserInfoDTO implements Serializable {

    private String sellerNick;
    private Integer sex;
    private Double fee;
    private String mobile;
    private String address;
    private Date birthday;
    private String introduction;
    private String imageUrl;
    private String newPassword;
    private String oldPassword;

}
