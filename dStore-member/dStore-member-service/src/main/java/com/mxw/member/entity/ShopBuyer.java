package com.mxw.member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 卖家会员表
 * 
 * @author miaoXiaoWen
 * @email miaoXiaoWen@gmail.com
 * @date 2020-12-10 15:00:54
 */
@Data
@TableName("shop_buyer")
public class ShopBuyer implements Serializable {
	private static final long serialVersionUID = 3L;

	/**
	 * 编号id
	 */
	@TableId
	private Long shopBuyerId;
	/**
	 * 卖家id
	 */
	private Long sellerId;
	/**
	 * 买家昵称(旺旺名称)
	 */
	private String buyerNick;
	/**
	 * 收货人的姓名
	 */
	private String receiverName;
	/**
	 * 收货人的地址
	 */
	private String receiverAddress;
	/**
	 * 收货人的邮编码,保存到市（海外：000000）
	 */
	private String receiverZip;
	/**
	 * 收货人的手机号码
	 */
	private String receiverMobile;
	/**
	 * 类型1,移动2联通3电信4网通
	 */
	private Integer receiverMobileType;
	/**
	 * 下单次数（天）,当天多笔订单算一次
	 */
	private Integer buyEverydayCount;
	/**
	 * 交易次数（购买总次数）
	 */
	private Integer buyTotalCount;
	/**
	 * 交易金额（购买总金额）
	 */
	private BigDecimal buyTotalMoney;
	/**
	 * 付款件数
	 */
	private Integer paymentNum;
	/**
	 * 均件数
	 */
	private Integer avgNum;
	/**
	 * 交易主动关闭数
	 */
	private Integer closeNum;
	/**
	 * 交易主动关闭金额
	 */
	private BigDecimal closeMoney;
	/**
	 * 平均客单价，单位为元
	 */
	private BigDecimal guestPrice;
	/**
	 * 退款成功关闭数
	 */
	private Integer refundNum;
	/**
	 * 退款成功金额
	 */
	private BigDecimal refundMoney;
	/**
	 * 微信
	 */
	private String weChat;
	/**
	 * qq
	 */
	private String qq;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 买家支付宝账号
	 */
	private String buyerAlipayNo;
	/**
	 * 会员等级，0：店铺客户，1：普通会员，2：高级会员，3：VIP会员， 4：至尊VIP会员。如果不传入值则默认为全部等级。
	 */
	private Integer grade;
	/**
	 * 性别 0：未知 1：男 2：女  
	 */
	private Integer sex;
	/**
	 * 出生年份
	 */
	private String bornYear;
	/**
	 * 生日日期,格式MMdd
	 */
	private String birthday;
	/**
	 * 星座 0:白羊座 1:金牛座 2:双子座 3:巨蟹座 4:狮子座 5:处女座 6:天秤座 7:天蝎座 8:射手座 9:摩羯座 10:水瓶座 11:双鱼座
	 */
	private Integer constellation;
	/**
	 * 最后下单时间
	 */
	private Date buyerLastTime;
	/**
	 * 最早下单时间
	 */
	private Date buyerFristTime;
	/**
	 * 最早订单付款时间
	 */
	private Date buyerFristPayTime;
	/**
	 * 最后订单付款时间
	 */
	private Date buyerLastPayTime;
	/**
	 * 最早交易成功时间
	 */
	private Date buyerFristSucceedTime;
	/**
	 * 最后交易成功时间
	 */
	private Date buyerLastSucceedTime;
	/**
	 * 短信营销次数
	 */
	private Integer smsMarketingCount;
	/**
	 * 最后发送短信时间
	 */
	private Date lastSendTime;
	/**
	 * 复购周期（天）
	 */
	private Integer afterPeriod;
	/**
	 * 人群画像类型，1:学生 2:白领 3:公务员 4:IT 5:金融族 6:居家族 7:医护人员 8:其他
	 */
	private Integer crowdPortraitType;
	/**
	 * 聚划算敏感度
	 */
	private Integer jhsSensitivity;
	/**
	 * 万人团敏感度
	 */
	private Integer stepSensitivity;
	/**
	 * 是否给过中差评 0:否 1：是
	 */
	private Integer isNeutralBadRate;
	/**
	 * 是否黑名单 0:否 1：是
	 */
	private Integer isBlacklist;
	/**
	 * 标签ID组合，逗号分隔
	 */
	private String buyerLabelId;
	/**
	 * 领卡时间
	 */
	private Date registerDate;
	/**
	 * 积分余额
	 */
	private Integer pointBalance;
	/**
	 * 最后积分变更时间
	 */
	private Date lastPointUpdateTime;
	/**
	 * 积分系统 签到次数
	 */
	private Integer pointsSignInCount;
	/**
	 * 积分系统 参与大转盘次数
	 */
	private Integer pointsJoinBigWheelCount;
	/**
	 * 积分系统 兑换积分次数
	 */
	private Integer pointsExchangePointsCount;
	/**
	 * 积分系统 兑换次数(包含积分和实物)
	 */
	private Integer pointsExchangeCount;
	/**
	 * 积分系统 消耗积分
	 */
	private Integer pointsCostPoints;
	/**
	 * 积分系统 是否完善资料
	 */
	private Integer pointsIsFillInformation;
	/**
	 * 微信关注时间 如果用户曾多次关注，则取最后关注时间
	 */
	private Date wxSubscribeTime;
	/**
	 * 微信性别 0：未知 1：男 2：女
	 */
	private Integer wxSex;

}
