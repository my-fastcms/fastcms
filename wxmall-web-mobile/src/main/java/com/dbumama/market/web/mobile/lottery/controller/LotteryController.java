package com.dbumama.market.web.mobile.lottery.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dbumama.market.model.Lottery;
import com.dbumama.market.model.LotteryAward;
import com.dbumama.market.model.LotteryRecord;
import com.dbumama.market.model.LotteryTrade;
import com.dbumama.market.service.api.lottery.DrawResultDto;
import com.dbumama.market.service.api.lottery.LotteryService;
import com.dbumama.market.service.api.lottery.LotteryServiceException;
import com.dbumama.market.service.enmu.LotteryCondType;
import com.dbumama.market.service.enmu.LotteryType;
import com.dbumama.market.service.utils.DateTimeUtil;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.BaseMobileController;
import com.dbumama.market.web.core.plugin.spring.Inject.BY_NAME;

/**
 * wjun_java@163.com
 * 2016年7月20日
 */
@RouteBind(path="lottery")
public class LotteryController extends BaseMobileController{

	@BY_NAME
	private LotteryService lotteryService;
	
	public void index(){
		List<Lottery> lotterys = lotteryService.getVaildLotterys(getSellerId());
		setAttr("lotterys", lotterys);
		render("l_index.html");
	}
	
	public void draw(){
		Lottery lottery = lotteryService.findById(getParaToLong("lotteryId"));
		if(lottery == null || lottery.getSellerId() != getSellerId()){
			renderError(404);
			return;
		}
		
		List<LotteryRecord> lotteryRecords = lotteryService.getLotteryRecords(lottery.getId(), getBuyerId());
		List<LotteryRecord> todayRecords = new ArrayList<LotteryRecord>();
		//获取当前买家今天的抽奖次数
		for(LotteryRecord lr : lotteryRecords){
			String ltime = DateTimeUtil.FORMAT_YYYY_MM_DD.format(lr.getLotteryTime());
			String d = DateTimeUtil.FORMAT_YYYY_MM_DD.format(new Date());
			if(ltime.equals(d)){
				todayRecords.add(lr);
			}
		}
		setAttr("usetodaycount", todayRecords.size());
		setAttr("usetotalRecord", lotteryRecords.size());
		
		if(lottery.getConditionType() == LotteryCondType.COND_TRADE.value){
			LotteryTrade lottcond =lotteryService.getLotteryTrade(getParaToLong("lotteryId"));
			setAttr("lottcond", lottcond);
		}
		
		setAttr("lottery", lottery);
		
		if(lottery.getLotteryType() == LotteryType.L_JIUGONGGE.value){
			render("l_draw.html");
		}else if(lottery.getLotteryType() == LotteryType.L_GUAGUALE.value){
			render("l_guaguale.html");
		}else if(lottery.getLotteryType() == LotteryType.L_ZHUANZHUAN.value){
			render("l_zhuanzhuan.html");
		}
		
	}
	
	public void initView(){
		List<LotteryAward> lotteryAwards = lotteryService.initView(getParaToLong("lotteryId"));
		rendSuccessJson(lotteryAwards);
	}
	
	/**
	 * 开始抽奖
	 */
	public void start(){
		try {
			DrawResultDto result = lotteryService.lottery(getParaToLong("id"), getSellerId(), getBuyerId());
			rendSuccessJson(result);
		} catch (LotteryServiceException e) {
			rendFailedJson(e.getMessage());
		}
	}
	
}
