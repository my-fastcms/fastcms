/**
 * 文件名:PrizeController.java
 * 版本信息:1.0
 * 日期:2015-5-9
 * Copyright 广州点步信息科技
 * 版权所有
 */
package com.dbumama.market.web.admin.prize.controller;

import java.util.List;

import com.dbumama.market.model.Prize;
import com.dbumama.market.model.PrizeType;
import com.dbumama.market.service.api.prize.PrizeService;
import com.dbumama.market.web.admin.prize.validator.PrizeValidator;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.AdminBaseController;
import com.dbumama.market.web.core.plugin.spring.Inject.BY_NAME;
import com.jfinal.aop.Before;

/**
 * @author: wjun.java@gmail.com
 * @date:2015-5-9
 */
@RouteBind(path="prize", viewPath="prize")
public class PrizeController extends AdminBaseController<Prize>{
	
	@BY_NAME
	private PrizeService prizeService;
	
	public void index(){
		//读取prizeType信息
		List<PrizeType> prizeTypes = prizeService.getPrizeTypes();
		setAttr("prizeTypes", prizeTypes);
		this.render("p_index.html");
	}
	
	public void selectPrize(){
		List<PrizeType> prizeTypes = prizeService.getPrizeTypes();
		setAttr("prizeTypes", prizeTypes);
	}
	
//	@Before(CacheInterceptor.class)
	public void list(){
		rendSuccessJson(prizeService.list(getSellerId(), getPageNo(), getPageSize(), getParaToInt("type"), getPara("qname")));
	}
	
	@Before(PrizeValidator.class)
	public void save() {
		try {
			prizeService.save(getModel());
			rendSuccessJson("操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			rendFailedJson("保存失败！");
		}
	}
	
	public void del() {
//		CacheKit.removeAll(CACHENAME);
		String ids = getPara("ids");
		for(String id : ids.split("-")){
			Prize prize = prizeService.findById(Long.valueOf(id));
			prize.setActive(0);
			prize.update();
		}
		rendSuccessJson("操作成功！");
	}
	
	//选择店铺商品
	public void showItems(){}
	
	/* (non-Javadoc)
	 * @see com.dbumama.market.web.common.controller.AdminBaseController#getModelClass()
	 */
	@Override
	protected Class<Prize> getModelClass() {
		return Prize.class;
	}
}
