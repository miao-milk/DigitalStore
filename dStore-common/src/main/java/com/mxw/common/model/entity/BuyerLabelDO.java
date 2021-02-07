package com.mxw.common.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 会员标签信息表
 * 
 * @author miaoXiaoWen
 * @email miaoXiaoWen@gmail.com
 * @date 2020-12-16 13:54:37
 */
@Data
@TableName("shop_buyer_label")
public class BuyerLabelDO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 会员标签主键Id
	 */
	@TableId
	private Integer id;
	/**
	 * 用户id
	 */
	private Integer shopBuyerId;
	/**
	 * 标签id
	 */
	private Integer labelId;

	/**
	 *用户与标签绑定时间
	 */
	private Date createTime;

	/**
	 *标签权值
	 */
	private Integer weight;

}
