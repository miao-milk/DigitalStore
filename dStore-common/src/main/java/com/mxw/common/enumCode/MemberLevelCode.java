package com.mxw.common.enumCode;


public enum MemberLevelCode {
    /**
     * 店铺会员等级
     */
    Store_Level_10000(0,"店铺客户"),
    Store_Level_10001(1,"普通会员"),
    Store_Level_10002(2,"高级会员"),
    Store_Level_10003(3,"VIP会员"),
    Store_Level_10004(4,"至尊VIP会员"),

    /**
     * 消费金额会员等级
     */
    Spending_Level_10000(0,"铜牌会员"),
    Spending_Level_10001(1,"银牌会员"),
    Spending_Level_10002(2,"黄金会员"),
    Spending_Level_10003(3,"白金会员"),
    Spending_Level_10004(4,"钻石会员"),

    /**
     * 消费次数会员等级
     */
    Transaction_Level_10000(0,"发展客户"),
    Transaction_Level_10001(1,"挽留客户"),
    Transaction_Level_10002(2,"保持客户"),
    Transaction_Level_10003(3,"价值客户"),
    Transaction_Level_10004(4,"大客户")
    ;

    private int code;
    private String desc;

    MemberLevelCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
