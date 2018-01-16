/**
 * 文件名:PayService.java
 * 版本信息:1.0
 * 日期:2015-11-2
 * 广州点步信息科技版权所有
 */
package com.dbumama.market.service.api.pay;

import java.math.BigDecimal;
import java.util.TreeMap;

import com.dbumama.market.model.BuyerUser;

/**
 * @author: wjun_java@163.com
 * @date:2015-11-2
 */
public interface PayService {
	
	/**
	 * 用户参与抽奖活动支付充值后回调到此方法
	 * @param user
	 * @param fee
	 * @throws PayException
	 */
	public void resultLotteryCallback(BuyerUser user, TreeMap<String, Object> params) throws PayException;
	
	/**
	 * 用户在商城支付订单后，进行回调处理
	 * @param user
	 * @param params
	 * @throws PayException
	 */
	public void resultOrderCallback(BuyerUser user, TreeMap<String, Object> params) throws PayException;
	
	/**
	 * 调用微信统一下单接口
	 * @param openId
	 * @param tradeNo
	 * @param payFee
	 * @param ip
	 * @return
	 * @throws PayException
	 */
	public TreeMap<String, Object> prepareToPay(String openId, String tradeNo, BigDecimal payFee, String desc, String ip) throws PayException;
	
	/**
	 * 小程序发起支付请求
	 * @param openId
	 * @param tradeNo
	 * @param payFee
	 * @param desc
	 * @param ip
	 * @param authUser
	 * @return
	 * @throws PayException
	 */
	public TreeMap<String, Object> wxAppPrepareToPay(String openId, String tradeNo, BigDecimal payFee, String desc, String ip) throws PayException;
	
}
