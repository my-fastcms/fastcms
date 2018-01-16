package com.dbumama.market.web.admin.weipage.controller;

import com.dbumama.market.model.WeiPage;
import com.dbumama.market.model.WeipageCategory;
import com.dbumama.market.service.api.weipage.WeiPageService;
import com.dbumama.market.web.admin.weipage.controller.validator.WeippageCategoryValidator;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.AdminBaseController;
import com.dbumama.market.web.core.plugin.spring.Inject.BY_NAME;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;


@RouteBind(path="weipage/cat", viewPath="weipage/cat")
public class WeipageCategoryController   extends AdminBaseController<WeipageCategory> {

	@BY_NAME
	private WeiPageService weiPageService;
	
	public void index() {
		render("cat_index.html");
	}
	
	public void edit(){
		final Long id=getParaToLong();
		if(id!=null){
			WeipageCategory category=weiPageService.findCategoryById(id);
			setAttr("category", category);
		}
		render("cat_edit.html");
	}
	
	public void list(){
		rendSuccessJson(weiPageService.pageCategory(getSellerId(), getPageNo(), getPageSize()));
	}
	
	@Before(WeippageCategoryValidator.class)
	public void save(){
		try {
			WeipageCategory category = getModel();
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
		Long id = this.getParaToLong("id");
		if(id!=null){
			Long l=Db.queryLong("select count(*) from " + WeiPage.table +" where category_id=?",id);
			if(l!=null&&l.longValue()>0l){
				rendFailedJson("分类下有微页面，不能删除！");
				return;
			}
			weiPageService.deleteCategoryById(id);
		}
		
		rendSuccessJson("操作成功！");
	}
	
	@Override
	protected Class<WeipageCategory> getModelClass() {
		return WeipageCategory.class;
	}

}
