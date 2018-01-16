/**
 * 文件名:PrizeType.java
 * 版本信息:1.0
 * 日期:2015-5-16
 * Copyright 广州点步信息科技
 * 版权所有
 */
package com.dbumama.market.service.enmu;

/**
 * 活动奖励类型
 * @author: wjun.java@gmail.com
 * @date:2015-5-16
 */
public enum PayType {
	
    PAYSCORE(1), 	//奖励积分
    PAYLUCK(2), 	//奖励抽奖机会
    PAYFLOW(3);		//奖励流量包

    public int value;

    PayType(int value) {
        this.value = value;
    }

    public static PayType valueOf(int value) {
        switch (value) {
            case 1:
                return PAYSCORE;
            case 2:
                return PAYLUCK;
            case 3:
            	return PAYFLOW;
            default:
                return null;
        }
    }
}
