package com.mxw.common.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 卖家会员高级分组分类表
 * 
 * @author miaoXiaoWen
 * @email miaoXiaoWen@gmail.com
 * @date 2020-12-15 15:46:47
 */
@Data
@TableName("shop_buyer_senior_group_category")
public class ShopSeniorGroupCategoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Long shopBuyerSeniorGroupCategoryId;
	/**
	 * 分类名称
	 */
	private String categoryName;
	/**
	 * 卖家昵称
	 */
	private String sellerNick;
	/**
	 * 简介
	 */
	private String instruction;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 1 正在使用 2 被停用（或删除）
	 */
	private Integer categoryStatus;

}
