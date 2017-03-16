package com.dbumama.market.web.admin.lottery.controller;

import com.dbumama.market.model.Lottery;
import com.dbumama.market.model.LotteryTrade;
import com.dbumama.market.service.enmu.LotteryType;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.BaseController;
import com.jfinal.kit.StrKit;

/**
 * wjun_java@163.com
 * 2016年6月27日
 */
@RouteBind(path="lottery/show", viewPath="lottery")
public class LotteryTypeController extends BaseController{

	Lottery lottery;
	
	public void jiugongge(){
		if(StrKit.notBlank(getPara(0))){
			lottery = Lottery.dao.findById(getPara(0));
			if(lottery != null && lottery.getLotteryType() == LotteryType.L_JIUGONGGE.value){
				setAttr("lottery", lottery);
				//查询抽奖限制条件
				setAttr("condTrade", LotteryTrade.dao.findFirst("select * from " + LotteryTrade.table + " where lottery_id = ?", getPara(0)));	
			}
		}
		render("lt_jiugongge.html");
	}
	
	public void yaoyiyao(){
		if(StrKit.notBlank(getPara(0))){
			lottery = Lottery.dao.findById(getPara(0));
			if(lottery != null && lottery.getLotteryType() == LotteryType.L_YAOYIYAO.value){
				setAttr("lottery", lottery);
				//查询抽奖限制条件
				setAttr("condTrade", LotteryTrade.dao.findFirst("select * from " + LotteryTrade.table + " where lottery_id = ?", getPara(0)));	
			}
		}
		render("lt_yaoyiyao.html");
	}
	
	public void guaguale(){
		if(StrKit.notBlank(getPara(0))){
			lottery = Lottery.dao.findById(getPara(0));
			if(lottery != null && lottery.getLotteryType() == LotteryType.L_GUAGUALE.value){
				setAttr("lottery", lottery);
				//查询抽奖限制条件
				setAttr("condTrade", LotteryTrade.dao.findFirst("select * from " + LotteryTrade.table + " where lottery_id = ?", getPara(0)));	
			}
		}
		render("lt_guaguale.html");
	}
	
	public void fanfanle(){
		if(StrKit.notBlank(getPara(0))){
			lottery = Lottery.dao.findById(getPara(0));
			if(lottery != null && lottery.getLotteryType() == LotteryType.L_FANFANLE.value){
				setAttr("lottery", lottery);
				//查询抽奖限制条件
				setAttr("condTrade", LotteryTrade.dao.findFirst("select * from " + LotteryTrade.table + " where lottery_id = ?", getPara(0)));	
			}
		}
		render("lt_fanfanle.html");
	}
	
	public void zhuanzhuan(){
		if(StrKit.notBlank(getPara(0))){
			lottery = Lottery.dao.findById(getPara(0));
			if(lottery != null && lottery.getLotteryType() == LotteryType.L_ZHUANZHUAN.value){
				setAttr("lottery", lottery);
				//查询抽奖限制条件
				setAttr("condTrade", LotteryTrade.dao.findFirst("select * from " + LotteryTrade.table + " where lottery_id = ?", getPara(0)));	
			}
		}
		render("lt_zhuanzhuan.html");
	}
	
}
