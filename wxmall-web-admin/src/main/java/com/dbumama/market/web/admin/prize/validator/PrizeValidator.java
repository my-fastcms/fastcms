package com.dbumama.market.web.admin.prize.validator;

import com.dbumama.market.model.PrizeType;
import com.dbumama.market.web.core.validator.BaseJsonRenderValidator;
import com.jfinal.core.Controller;

public class PrizeValidator extends BaseJsonRenderValidator {

	@Override
	protected void validate(Controller c) {
		validateRequiredString("prize_type_id", "error_prize_type_id", "奖品类型不能为空!");
		int prizeTypeId = Integer.valueOf(c.getPara("prize_type_id"));
		PrizeType prizeType = PrizeType.dao.findById(prizeTypeId);
		if("redpack".equals(prizeType.getTypeCode())){
			validateRequiredString("prize_memo", "error_prize_memo", "请输入红包祝福语!");
		}
		validateRequiredString("prize_single_cash", "error_prize_single_cash", "请输入单份奖品" + prizeType.getTypeName() + "数量!");
		validateRequiredString("prize_name", "error_prize_name", "请输入奖品名称!");
		validateRequiredString("publish_count", "error_publish_count", "请输入奖品总数量!");
		validateRequiredString("prize_price", "error_prize_price", "请输入奖品价值!");
		//validateRequiredString("prize_img", "error_prize_img", "请输入奖品图片!");
	}

}
