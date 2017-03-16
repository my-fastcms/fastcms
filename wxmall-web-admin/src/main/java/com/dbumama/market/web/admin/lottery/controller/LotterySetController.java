package com.dbumama.market.web.admin.lottery.controller;

import com.dbumama.market.model.Lottery;
import com.dbumama.market.service.enmu.LotteryType;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.BaseController;

/**
 * wjun_java@163.com
 * 2016年7月3日
 */
@RouteBind(path = "lottery/set", viewPath = "lottery")
public class LotterySetController extends BaseController{
	Lottery lottery;
	public void jiugongge(){
		lottery = Lottery.dao.findById(getPara(0));
		if(lottery !=null && lottery.getLotteryType() ==LotteryType.L_JIUGONGGE.value) setAttr("lottery", lottery);
		render("prize_setting_jiugongge.html");
	}
	
	public void guaguale(){
		lottery = Lottery.dao.findById(getPara(0));
		if(lottery !=null && lottery.getLotteryType() ==LotteryType.L_GUAGUALE.value) setAttr("lottery", lottery);
		render("prize_setting_guaguale.html");		
	}
	
	public void yaoyiyao(){
		lottery = Lottery.dao.findById(getPara(0));
		if(lottery !=null && lottery.getLotteryType() ==LotteryType.L_YAOYIYAO.value) setAttr("lottery", lottery);
		render("prize_setting_yaoyiyao.html");		
	}
	
	public void fanfanle(){
		lottery = Lottery.dao.findById(getPara(0));
		if(lottery !=null && lottery.getLotteryType() ==LotteryType.L_FANFANLE.value) setAttr("lottery", lottery);
		render("prize_setting_fanfanle.html");		
	}
	
	public void zhuanzhuan(){
		lottery = Lottery.dao.findById(getPara(0));
		if(lottery !=null && lottery.getLotteryType() ==LotteryType.L_ZHUANZHUAN.value) setAttr("lottery", lottery);
		render("prize_setting_zhuanzhuan.html");		
	}
	
}
