/**
 * 文件名:PrizeController.java
 * 版本信息:1.0
 * 日期:2015-5-26
 * Copyright 广州点步信息科技
 * 版权所有
 */
package com.dbumama.market.web.mobile.prize.controller;

import java.util.List;

import com.dbumama.market.model.Prize;
import com.dbumama.market.service.utils.DateTimeUtil;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.BaseMobileController;

/**
 * @author: wjun.java@gmail.com
 * @date:2015-5-26
 */
@RouteBind(path="prize")
public class PrizeController extends BaseMobileController{
	
	/**
	 * 展示卖家设置的可兑换奖品列表，供买家用积分进行奖品兑换
	 */
	public void list(){
		/*String sellerNick = getSellerNick();
		String buyerNick = getBuyerMixNick();*/
		//查询粉丝的联系方式
		/*List<BuyerContact> buyerContacts = BuyerContact.dao.find("select * from " + BuyerContact.TABLENAME + " where buyer_mixnick = ? and seller_nick = ? ", 
				new Object[]{buyerNick, sellerNick});
		
		if(buyerContacts!=null && buyerContacts.size()>0){
			setAttr("buyerContact", buyerContacts.get(0));
		}*/
		
		String currDate = DateTimeUtil.getDateTimeString();
		
		//查询卖家定义的奖品列表 在有效时间范围内可见的 并且是没有删除的奖品 并且是兑换进行中的奖品
		List<Prize> prizes = Prize.dao.find("select * from " + Prize.table 
				+ " where active=1 and seller_id =? "
				+ " and start_date<=str_to_date('"+currDate+"', '%Y-%m-%d %H:%i:%s') and end_date >=str_to_date('"+currDate+"', '%Y-%m-%d %H:%i:%s')"
				+ " order by created desc ", 
				new Object []{getSellerId()});
		setAttr("prizes", prizes);
		render("prize.html");
	}
	
	public void detail(){
	}
	
}
