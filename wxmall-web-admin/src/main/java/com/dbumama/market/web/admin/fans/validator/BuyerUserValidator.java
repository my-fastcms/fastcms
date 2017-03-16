package com.dbumama.market.web.admin.fans.validator;

import com.dbumama.market.web.core.validator.BaseJsonRenderValidator;
import com.jfinal.core.Controller;

public class BuyerUserValidator extends BaseJsonRenderValidator{

	@Override
	protected void validate(Controller c) {
//		validateRequiredString("nick", "error_nick", "请输入混淆昵称!");
		validateRequiredString("real_nick", "error_real_nick", "请输入粉丝真实旺旺号!");	
		validateRegex("scroe", intPatten, "error_scroe", "粉丝积分数必须是整数");
		validateRegex("prize_count", intPatten, "error_prize_count", "粉丝抽奖机会数必须是整数");	
	}

}
