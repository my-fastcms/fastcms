/**
 * 文件名:PrizeController.java
 * 版本信息:1.0
 * 日期:2015-5-26
 * Copyright 广州点步信息科技
 * 版权所有
 */
package com.dbumama.market.web.mobile.prize.controller;

import com.dbumama.market.service.api.prize.PrizeService;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.BaseMobileController;
import com.dbumama.market.web.core.plugin.spring.Inject.BY_NAME;

/**
 * @author: wjun.java@gmail.com
 * @date:2015-5-26
 */
@RouteBind(path="prize")
public class PrizeController extends BaseMobileController{
	@BY_NAME
	private PrizeService prizeService;
	/**
	 * 展示卖家设置的可兑换奖品列表，供买家用积分进行奖品兑换
	 */
	public void list(){
		setAttr("prizes", prizeService.getVaildPrizes(getSellerId()));
		render("prize.html");
	}
	
	public void detail(){
	}
	
}
