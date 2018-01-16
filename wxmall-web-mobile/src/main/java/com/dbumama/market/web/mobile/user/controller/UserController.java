package com.dbumama.market.web.mobile.user.controller;

import com.dbumama.market.model.MemberRank;
import com.dbumama.market.model.Order;
import com.dbumama.market.service.api.customer.MemberRankService;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.BaseMobileController;
import com.dbumama.market.web.core.plugin.spring.Inject.BY_NAME;
import com.jfinal.plugin.activerecord.Db;

@RouteBind(path = "user")
public class UserController extends BaseMobileController{

	@BY_NAME
	private MemberRankService memberRankService;
	
	public void index(){
		//待付款
		Long unpayedCount = Db.queryLong("select count(*) from " + Order.table + " where buyer_id=? and order_type=0 and payment_status=0", getBuyerId());
		//已支付，未发货
		Long payedCount = Db.queryLong("select count(*) from " + Order.table + " where buyer_id=? and order_type=0 and payment_status=1 and shipping_status=0", getBuyerId());
		//已支付，未发货
		Long shippedCount = Db.queryLong("select count(*) from " + Order.table + " where buyer_id=? and order_type=0 and payment_status=1 and shipping_status=1", getBuyerId());
		//交易成功
		Long complateCount = Db.queryLong("select count(*) from " + Order.table + " where buyer_id=? and order_type=0 and order_status=2", getBuyerId());
		setAttr("unpayedCount", unpayedCount);
		setAttr("payedCount", payedCount);
		setAttr("shippedCount", shippedCount);
		setAttr("complateCount", complateCount);
		
		if(getBuyerUser().getMemberRankId() != null){
			MemberRank rank = memberRankService.findById(getBuyerUser().getMemberRankId());
			setAttr("rank", rank);
		}
		render("/user/index.html");
	}
	
	/**
	 * 去到会员卡激活界面
	 */
	public void active_card(){
		Long cardId = getParaToLong("cardId");
		setAttr("cardId", cardId);
		render("/user/active_card.html");
	}
	
}
