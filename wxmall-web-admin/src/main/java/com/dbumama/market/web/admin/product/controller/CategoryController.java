package com.dbumama.market.web.admin.product.controller;

import java.util.ArrayList;
import java.util.List;

import com.dbumama.market.model.Product;
import com.dbumama.market.model.ProductCategory;
import com.dbumama.market.web.admin.product.validator.ProdCategoryValidator;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.AdminBaseController;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;

@RouteBind(path = "category", viewPath = "category")
public class CategoryController extends AdminBaseController<ProductCategory>{
	
	public void index() {
		render("category_index.html");
	}
	
	public void list(){
		List<Object> params = new ArrayList<Object>();
		params.add(getSellerId());
		StringBuilder sqlBuilder = new StringBuilder("from " + ProductCategory.table + " where seller_id=? and active=1");
		sqlBuilder.append(" order by orders asc ");
		Object [] o = new Object[params.size()];
		for(int i=0;i<params.size();i++){
			o [i] = params.get(i); 
		}
		
		Page<ProductCategory> productCategorypage=ProductCategory.dao.paginate(getPageNo(), getPageSize(), 
				"select * ", 
				sqlBuilder.toString(), 
				o);
		
		rendSuccessJson(productCategorypage);
	}
	
	public void add(){
		final Long id=getParaToLong(0);
		ProductCategory productCategory=ProductCategory.dao.findById(id);
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
			List<Product> products = Product.dao.find("select * from " + Product.table + " where product_category_id=? ", id);
			if(products != null && products.size()>0) continue;//有商品的分类不能删除
			ProductCategory category = ProductCategory.dao.findById(id);
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
