/**
 * 文件名:PrizeType.java
 * 版本信息:1.0
 * 日期:2015-5-16
 * Copyright 广州点步信息科技
 * 版权所有
 */
package com.dbumama.market.service.enmu;

/**
 * @author: wjun.java@gmail.com
 * @date:2015-5-16
 */
public enum PrizeType {
	
	COUPON(1), 		//优惠卷
    PHONECASH(2), 	//话费
    ITEMOBJECT(3), 	//实物
    JIFENBAO(4),	//集分宝
    FLOW(5),		//流量包
    TAOJINBI(6),	//淘金币
    JIFEN(7);		//软件内积分

    public int value;

    PrizeType(int value) {
        this.value = value;
    }

    public static PrizeType valueOf(int value) {
        switch (value) {
            case 1:
                return COUPON;
            case 2:
                return PHONECASH;
            case 3:
                return ITEMOBJECT;
            case 4:
                return JIFENBAO;
            case 5:
            	return FLOW;
            case 6:
            	return TAOJINBI;
            default:
                return null;
        }
    }
    
}
