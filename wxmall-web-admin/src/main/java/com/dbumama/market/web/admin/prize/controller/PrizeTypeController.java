package com.dbumama.market.web.admin.prize.controller;

import com.dbumama.market.model.Prize;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.BaseController;
import com.jfinal.kit.StrKit;

/**
 * wjun_java@163.com
 * 2016年7月22日
 */
@RouteBind(path = "prize/type", viewPath = "prize")
public class PrizeTypeController extends BaseController{

	/**
	 * 手机流量
	 */
	public void flow(){
		//check flow 
		render("t_flow.html");
	}
	
	/**
	 * 自定义商品
	 */
	public void item(){
		setPrize();
		render("t_item.html");
	}
	
	/**
	 * 软件内积分
	 */
	public void jifen(){
		setPrize();
		render("t_jifen.html");
	}
	
	/**
	 * 淘金币
	 */
	public void taojinbi(){
		setPrize();
		render("t_taojinbi.html");
	}
	
	/**
	 * 红包
	 */
	public void redpack(){
		setPrize();
		render("t_redpack.html");
	}
	
	void setPrize(){
		if(!StrKit.isBlank(getPara("id"))){
			Prize p = Prize.dao.findById(getPara("id"));
			setAttr("prize", p);
		}
		setAttr("prizeTypeId", getParaToLong("typeId"));
	}
	
}
