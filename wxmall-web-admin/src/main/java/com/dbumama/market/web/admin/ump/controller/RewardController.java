package com.dbumama.market.web.admin.ump.controller;

import com.dbumama.market.model.FullCut;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.AdminBaseController;

@RouteBind(path = "reward")
public class RewardController extends AdminBaseController<FullCut>{
	public void index(){
		render("/promotion/fullCut_index.html");
	}

	@Override
	protected Class<FullCut> getModelClass() {
		return FullCut.class;
	}

}
