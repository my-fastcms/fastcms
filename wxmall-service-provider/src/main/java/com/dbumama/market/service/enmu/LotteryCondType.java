package com.dbumama.market.service.enmu;

/**
 * 抽奖条件
 * wjun_java@163.com
 * 2016年8月3日
 */
public enum LotteryCondType {

    COND_NO(1),			//不限条件 
    COND_TRADE(2), 		//下单抽奖
    COND_JIFEN(3), 		//积分抽奖
    COND_TAOJINB(4);	//淘金币抽奖

    public int value;

    LotteryCondType(int value) {
        this.value = value;
    }

    public static LotteryCondType valueOf(int value) {
        switch (value) {
            case 1:
                return COND_NO;
            case 2:
                return COND_TRADE;
            case 3:
                return COND_JIFEN;
            case 4:
                return COND_TAOJINB;
            default:
                return null;
        }
    }
	
}
