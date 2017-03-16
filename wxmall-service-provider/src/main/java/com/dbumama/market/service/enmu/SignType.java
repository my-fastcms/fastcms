/**
 * 文件名:PrizeType.java
 * 版本信息:1.0
 * 日期:2015-5-16
 * Copyright 广州点步信息科技
 * 版权所有
 */
package com.dbumama.market.service.enmu;

/**
 * 签到类型
 * @author: wjun.java@gmail.com
 * @date:2015-5-16
 */
public enum SignType {
	
    SIGN_JOIN(1), 	//连续签到
    SIGN_ADD(2), 	//累计签到
	SIGN_ASSIGN(3); //指定日期签到

    public int value;

    SignType(int value) {
        this.value = value;
    }

    public static SignType valueOf(int value) {
        switch (value) {
            case 1:
                return SIGN_JOIN;
            case 2:
                return SIGN_ADD;
            case 3:
                return SIGN_ASSIGN;
            default:
                return null;
        }
    }
}
