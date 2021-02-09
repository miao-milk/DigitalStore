package com.mxw.common.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO implements Serializable {

    /**
     * 用户username
     */
    private String userName;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 盐
     */
    private String token;
}
