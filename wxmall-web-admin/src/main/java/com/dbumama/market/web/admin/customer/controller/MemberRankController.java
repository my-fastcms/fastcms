package com.dbumama.market.web.admin.customer.controller;

import com.dbumama.market.model.MemberRank;
import com.dbumama.market.service.api.customer.CustomerException;
import com.dbumama.market.service.api.customer.MemberRankService;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.AdminBaseController;
import com.dbumama.market.web.core.plugin.spring.Inject.BY_NAME;

@RouteBind(path="memberRank", viewPath="customer")
public class MemberRankController extends AdminBaseController<MemberRank> {

	@BY_NAME
	private MemberRankService memberRankService;
	
	@Override
	protected Class<MemberRank> getModelClass() {
		return MemberRank.class;
	}
	
	public void index(){
		render("member_rank_index.html");
	}
	
	public void list(){
		try {
			rendSuccessJson(memberRankService.list(getSellerId()));
		} catch (CustomerException e) {
			rendFailedJson(e.getMessage());
		}
	}
	
	public void add(){
		MemberRank rank = memberRankService.findById(getParaToLong("id"));
		setAttr("rank", rank);
		render("member_rank_add.html");
	}
	
	public void save(){
		try {
			MemberRank memberRank = getModel();
			memberRank.setSellerId(getSellerId());
			memberRank.setActive(true);
			rendSuccessJson(memberRankService.save(memberRank));
		} catch (Exception e) {
			rendFailedJson(e.getMessage());
		}
	}

}
