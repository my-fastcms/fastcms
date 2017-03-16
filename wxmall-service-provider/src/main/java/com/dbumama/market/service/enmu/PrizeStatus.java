/**
 * 文件名:PrizeStatus.java
 * 版本信息:1.0
 * 日期:2015-5-16
 * Copyright 广州点步信息科技
 * 版权所有
 */
package com.dbumama.market.service.enmu;

/**
 * 奖品兑换状态
 * @author: wjun.java@gmail.com
 * @date:2015-5-16
 */
public enum PrizeStatus {
	
    NO_PUBLISH(1), 	//未发布状态，买家是不可见的
    PAST(2),		//不在有效时间范围内的任务，
    PUBLISH(3); 	//买家可见 已发布

    public int value;

    PrizeStatus(int value) {
        this.value = value;
    }

    public static PrizeStatus valueOf(int value) {
        switch (value) {
            case 1:
                return NO_PUBLISH;
            case 2:
                return PAST;
            case 3:
            	return PUBLISH;
            default:
                return null;
        }
    }
}
