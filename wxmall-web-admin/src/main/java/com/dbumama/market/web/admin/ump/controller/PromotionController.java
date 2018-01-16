package com.dbumama.market.web.admin.ump.controller;

import com.dbumama.market.model.Promotion;
import com.dbumama.market.service.api.product.ProductException;
import com.dbumama.market.service.api.product.ProductParamDto;
import com.dbumama.market.service.api.product.ProductResultDto;
import com.dbumama.market.service.api.ump.PromotionParamDto;
import com.dbumama.market.service.api.ump.PromotionResultDto;
import com.dbumama.market.service.api.ump.PromotionService;
import com.dbumama.market.service.api.ump.UmpException;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.AdminBaseController;
import com.dbumama.market.web.core.plugin.spring.Inject.BY_NAME;
import com.jfinal.plugin.activerecord.Page;

@RouteBind(path = "promotion")
public class PromotionController extends AdminBaseController<Promotion>{
	@BY_NAME
	private PromotionService promotionService;
	
	public void index(){
		render("/promotion/promotion_index.html");
	}
	
	public void set(){
		if(getParaToLong("pid") != null){
			setAttr("promotion", promotionService.getPromotionInfo(getParaToLong("pid")));
		}
		render("/promotion/promotion_set.html");
	}
	
	public void listProducts(){
		ProductParamDto productParamDto = new ProductParamDto(getSellerId(), getPageNo());
		try {
			Page<ProductResultDto> pages = promotionService.getProductsNoPromotionPage(productParamDto);
			rendSuccessJson(pages);
		} catch (ProductException e) {
			rendFailedJson(e.getMessage());
		}
	}
	
	public void listDiscountProducts(){
		ProductParamDto productParamDto = new ProductParamDto(getSellerId(), getPageNo());
		productParamDto.setPromotionId(getParaToLong("promotionId"));
		try {
			Page<ProductResultDto> pages = promotionService.getProductsPromotionPage(productParamDto);
			rendSuccessJson(pages);
		} catch (ProductException e) {
			rendFailedJson(e.getMessage());
		}
	}
	
	public void list(){
		PromotionParamDto promotionParam = new PromotionParamDto(getSellerId(), getPageNo());
		try {
			Page<PromotionResultDto> pages = promotionService.list(promotionParam);
			rendSuccessJson(pages);
		} catch (UmpException e) {
			rendFailedJson(e.getMessage());
		}
	}
	
	public void save(){
		try {
			Promotion promotion = getModel();
			String promotionSetItems = getPara("zhekouItems");
			promotionService.save(promotion, getSellerId(), promotionSetItems);
			rendSuccessJson();
		} catch (Exception e) {
			rendFailedJson(e.getMessage());
		}
	}
	
	public void del(){
		String ids = getPara("ids");
		for(String id : ids.split("-")){
			Promotion promotion = promotionService.findById(Long.valueOf(id));
			promotion.setActive(false);
			promotion.update();
		}
		rendSuccessJson("操作成功！");
	}
	
	@Override
	protected Class<Promotion> getModelClass() {
		return Promotion.class;
	}

}
