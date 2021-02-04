package com.mxw.common.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author miaoXiaoWen
 * @email miaoXiaoWen@gmail.com
 * @date 2020-12-16 13:54:37
 */
@Data
@TableName("shop_label")
public class LabelDO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 标签id
	 */
	@TableId
	private Integer labelId;
	/**
	 * 标签内容
	 */
	private String labelName;
	/**
	 * 标签创建时间
	 */
	private Date createTime;
	/**
	 * 用户id
	 */
	private Integer sellerId;

}
