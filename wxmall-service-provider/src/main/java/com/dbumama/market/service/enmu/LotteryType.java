package com.dbumama.market.service.enmu;

/**
 * wjun_java@163.com
 * 2016年8月3日
 */
public enum LotteryType {

    L_JIUGONGGE(1), L_GUAGUALE(2), L_YAOYIYAO(3), L_FANFANLE(4), L_ZHUANZHUAN(5);

    public int value;

    LotteryType(int value) {
        this.value = value;
    }

    public static LotteryType valueOf(int value) {
        switch (value) {
            case 1:
                return L_JIUGONGGE;
            case 2:
                return L_GUAGUALE;
            case 3:
                return L_YAOYIYAO;
            case 4:
                return L_FANFANLE;
            case 5:
            	return L_ZHUANZHUAN;
            default:
                return null;
        }
    }
	
}
