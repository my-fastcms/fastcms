package com.dbumama.market.web.mobile.categroy.controller;

import java.util.List;

import com.dbumama.market.model.ProductCategory;
import com.dbumama.market.service.api.product.ProductCategoryService;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.BaseMobileController;
import com.dbumama.market.web.core.plugin.spring.Inject.BY_NAME;

@RouteBind(path="category", viewPath="category")
public class CategoryController extends BaseMobileController{

	@BY_NAME
	ProductCategoryService productCategoryService;
	
	public void index(){
		List<ProductCategory> categories = productCategoryService.list();
		setAttr("categories", categories);
		render("index.html");
	}
	
}
