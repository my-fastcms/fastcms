package com.dbumama.market.web.admin.product.controller;

import java.util.List;

import com.dbumama.market.model.ProductGroup;
import com.dbumama.market.model.ProductGroupSet;
import com.dbumama.market.service.api.group.ProductGroupService;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.AdminBaseController;
import com.dbumama.market.web.core.plugin.spring.Inject.BY_NAME;

@RouteBind(path = "group", viewPath = "group")
public class ProductGroupController extends AdminBaseController<ProductGroup>{
	@BY_NAME
	private ProductGroupService productGroupService;
	public void index() {
		render("group_index.html");
	}
	
	public void list(){
		rendSuccessJson(productGroupService.page(getSellerId(), getPageNo(), getPageSize()));
	}
	
	public void add(){
		if(getParaToLong("pid") != null){
			setAttr("group",productGroupService.findById(getParaToLong("pid")));
			List<ProductGroupSet> groupProducts = productGroupService.getProductGroupSetsByGroupId(getParaToLong("pid"));
			setAttr("groupProducts", groupProducts);
		}
		render("group_add.html");
	}
	
	public void save(){
		try {
			final String productIds = getPara("product_ids");
			ProductGroup productGroup=getModel();
			productGroupService.save(productGroup, productIds, getSellerId());
			rendSuccessJson();
		} catch (Exception e) {
			rendFailedJson(e.getMessage());
		}
		
	}	
	
	@Override
	protected Class<ProductGroup> getModelClass() {
		return ProductGroup.class;
	}

}
