package com.mxw.user.model.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author miaoXiaoWen
 * @email miaoXiaoWen@gmail.com
 * @date 2020-12-29 13:54:37
 */
@Data
@TableName("sys_user")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private static final long serialVersionUID = 1L;
    /**
     * 主键Id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户username
     */
    @TableField("username")
    private String userName;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 盐
     */
    private String salt;
}

