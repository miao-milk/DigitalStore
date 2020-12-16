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
 * @date 2020-12-15 10:09:45
 */
@Data
@TableName("buyer_label")
public class LabelEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 会员标签主键Id
	 */
	@TableId
	private Long buyerLabelId;
	/**
	 * 卖家昵称
	 */
	private String sellerNick;
	/**
	 * 买家标签名称
	 */
	private String labelName;
	/**
	 * 买家数
	 */
	private Integer buyerCount;
	/**
	 * 创建时间
	 */
	private Date createTime;

}
