package com.dbumama.market.web.admin.product.validator;

import com.dbumama.market.web.core.validator.BaseJsonRenderValidator;
import com.jfinal.core.Controller;

public class ProductValidator extends BaseJsonRenderValidator{

	@Override
	protected void validate(Controller c) {
		validateRequiredString("product_name", "error_product_name", "请输入商品名称!");
		validateRequiredString("product_price", "error_product_price", "请输入商品价格!");
	}

}
