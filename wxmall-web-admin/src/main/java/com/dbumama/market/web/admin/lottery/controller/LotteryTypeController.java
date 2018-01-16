package com.dbumama.market.web.admin.lottery.controller;

import com.dbumama.market.model.Lottery;
import com.dbumama.market.service.api.lottery.LotteryService;
import com.dbumama.market.service.enmu.LotteryType;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.BaseController;
import com.dbumama.market.web.core.plugin.spring.Inject.BY_NAME;
import com.jfinal.kit.StrKit;

/**
 * wjun_java@163.com
 * 2016年6月27日
 */
@RouteBind(path="lottery/show", viewPath="lottery")
public class LotteryTypeController extends BaseController{

	Lottery lottery;
	@BY_NAME
	private LotteryService lotteryService;
	
	public void jiugongge(){
		if(StrKit.notBlank(getPara(0))){
			lottery = lotteryService.findById(getParaToLong(0));
			if(lottery != null && lottery.getLotteryType() == LotteryType.L_JIUGONGGE.value){
				setAttr("lottery", lottery);
				//查询抽奖限制条件
				setAttr("condTrade", lotteryService.getLotteryTrade(getParaToLong(0)));	
			}
		}
		render("lt_jiugongge.html");
	}
	
	public void yaoyiyao(){
		if(StrKit.notBlank(getPara(0))){
			lottery = lotteryService.findById(getParaToLong(0));
			if(lottery != null && lottery.getLotteryType() == LotteryType.L_YAOYIYAO.value){
				setAttr("lottery", lottery);
				//查询抽奖限制条件
				setAttr("condTrade", lotteryService.getLotteryTrade(getParaToLong(0)));		
			}
		}
		render("lt_yaoyiyao.html");
	}
	
	public void guaguale(){
		if(StrKit.notBlank(getPara(0))){
			lottery = lotteryService.findById(getParaToLong(0));
			if(lottery != null && lottery.getLotteryType() == LotteryType.L_GUAGUALE.value){
				setAttr("lottery", lottery);
				//查询抽奖限制条件
				setAttr("condTrade", lotteryService.getLotteryTrade(getParaToLong(0)));		
			}
		}
		render("lt_guaguale.html");
	}
	
	public void fanfanle(){
		if(StrKit.notBlank(getPara(0))){
			lottery = lotteryService.findById(getParaToLong(0));
			if(lottery != null && lottery.getLotteryType() == LotteryType.L_FANFANLE.value){
				setAttr("lottery", lottery);
				//查询抽奖限制条件
				setAttr("condTrade", lotteryService.getLotteryTrade(getParaToLong(0)));		
			}
		}
		render("lt_fanfanle.html");
	}
	
	public void zhuanzhuan(){
		if(StrKit.notBlank(getPara(0))){
			lottery = lotteryService.findById(getParaToLong(0));
			if(lottery != null && lottery.getLotteryType() == LotteryType.L_ZHUANZHUAN.value){
				setAttr("lottery", lottery);
				//查询抽奖限制条件
				setAttr("condTrade", lotteryService.getLotteryTrade(getParaToLong(0)));		
			}
		}
		render("lt_zhuanzhuan.html");
	}
	
}
