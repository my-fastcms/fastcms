package com.dbumama.market.web.admin.ump.validator;

import com.dbumama.market.web.core.validator.BaseJsonRenderValidator;
import com.jfinal.core.Controller;

public class PromotionValidator extends BaseJsonRenderValidator{

	@Override
	protected void validate(Controller c) {
		validateRequiredString("promotion_name", "error_promotion_name", "请输入折扣活动名称!");
		validateRequiredString("start_date", "error_start_date", "生效开始时间不能为空!");
		validateRequiredString("end_date", "error_end_date", "生效结束时间不能为空!");
		validateRequiredString("promotion_tag", "error_promotion_tag", "请填写活动标签!");
	}

}
