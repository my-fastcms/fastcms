package com.dbumama.market.web.admin.product.controller;

import com.dbumama.market.model.ProductCategory;
import com.dbumama.market.service.api.product.ProductCategoryService;
import com.dbumama.market.service.api.product.ProductService;
import com.dbumama.market.web.admin.product.validator.ProdCategoryValidator;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.AdminBaseController;
import com.dbumama.market.web.core.plugin.spring.Inject.BY_NAME;
import com.jfinal.aop.Before;

@RouteBind(path = "category", viewPath = "category")
public class CategoryController extends AdminBaseController<ProductCategory>{
	
	@BY_NAME
	private ProductService productService;
	@BY_NAME
	private ProductCategoryService productCategoryService;
	
	public void index() {
		render("category_index.html");
	}
	
	public void list(){
		rendSuccessJson(productCategoryService.page(getSellerId(), getPageNo(), getPageSize()));
	}
	
	public void add(){
		ProductCategory productCategory=productCategoryService.findById(getParaToLong(0));
		setAttr("productCategory", productCategory);
		render("category_add.html");
	}
	
	@Before(ProdCategoryValidator.class)
	public void save(){
		try {
			ProductCategory category = getModel();
			if(category.getId() != null){
				category.update();
			}else{
				category.setSellerId(getSellerId());
				category.save();
			}
			rendSuccessJson();
		} catch (Exception e) {
			e.printStackTrace();
			rendFailedJson(e.getMessage());
		}
	}
	
	public void del(){
		String ids = getPara("ids");
		for(String id : ids.split("-")){
			Long count = productService.getProductCountByCategroyId(Long.valueOf(id));
			if(count != null && count>0) continue;//有商品的分类不能删除
			ProductCategory category = productCategoryService.findById(Long.valueOf(id));
			category.setActive(0);
			category.update();
		}
		rendSuccessJson("操作成功！");
	}
	
	@Override
	protected Class<ProductCategory> getModelClass() {
		return ProductCategory.class;
	}
	
}
