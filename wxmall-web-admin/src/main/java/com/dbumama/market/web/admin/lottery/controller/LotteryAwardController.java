package com.dbumama.market.web.admin.lottery.controller;

import com.dbumama.market.model.LotteryAward;
import com.dbumama.market.service.api.lottery.LotteryService;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.AdminBaseController;
import com.dbumama.market.web.core.plugin.spring.Inject.BY_NAME;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;

/**
 * wjun_java@163.com
 * 2016年6月30日
 */
@RouteBind(path="lottery/award", viewPath="lottery")
public class LotteryAwardController extends AdminBaseController<LotteryAward>{
	
	@BY_NAME
	private LotteryService lotteryService;
	
	public void list(){
		rendSuccessJson(lotteryService.listLottAwards(getParaToLong("lotteryId")));
	}
	
	public void show(){
		rendSuccessJson(lotteryService.findAwardById(getParaToLong("awardId")));
	}

	@Before(Tx.class)
	public void save(){
		try {
			LotteryAward award = getModel();
			lotteryService.saveAward(award);
			rendSuccessJson();
		} catch (Exception e) {
			e.printStackTrace();
			rendFailedJson(e.getMessage());
		}
	}
	
	public void del(){
		lotteryService.deleteAwardById(getParaToLong("id"));
		rendSuccessJson();
	}
	
	/* (non-Javadoc)
	 * @see com.dbumama.market.web.common.controller.AdminBaseController#getModelClass()
	 */
	@Override
	protected Class<LotteryAward> getModelClass() {
		return LotteryAward.class;
	}

}
