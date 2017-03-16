package com.dbumama.market.web.admin.lottery.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dbumama.market.model.AuthUser;
import com.dbumama.market.model.Lottery;
import com.dbumama.market.service.api.lottery.LotteryService;
import com.dbumama.market.service.api.lottery.LotteryServiceException;
import com.dbumama.market.service.api.lottery.LotteryTestResultDto;
import com.dbumama.market.service.enmu.LotteryType;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.AdminBaseController;
import com.dbumama.market.web.core.plugin.spring.Inject;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * wjun_java@163.com
 * 2016年6月26日
 */
@RouteBind(path="lottery", viewPath="lottery")
public class LotteryController extends AdminBaseController<Lottery>{

	@Inject.BY_NAME
	private LotteryService lotteryService;
	
	public void index(){
		List<AuthUser> authUsers = AuthUser.dao.find("select * from " + AuthUser.table + " where seller_id=?", getSellerId());
		setAttr("authUsers", authUsers);
		render("lt_index.html");
	}
	
	public void list(){
		Map<String, String> params = new HashMap<String, String>();
		params.put("sellerId", getSellerId().toString());
		if(StrKit.notBlank(getPara("qname"))){
			params.put("qname", getPara("qname"));
		}
		try {
			rendSuccessJson(lotteryService.list(getPageNo(), getPageSize(), params));	
		} catch (LotteryServiceException e) {
			rendFailedJson(e.getMessage());
		}
	}
	
	public void type(){
		render("lt_types.html");
	}
	
	public void save(){
		String condition = getPara("condition");
		try {
			Lottery lottery = getModel();
			if(lottery.getId() == null){
				lottery.setSellerId(getSellerId());
				try {
					rendSuccessJson(lotteryService.save(lottery, getSellerUser(), condition));
				} catch (LotteryServiceException e) {
					rendFailedJson(e.getMessage());
				}
			}else{
				try {
					rendSuccessJson(lotteryService.update(lottery, getSellerUser(), condition));
				} catch (LotteryServiceException e) {
					rendFailedJson(e.getMessage());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			rendFailedJson("获取抽奖信息失败！");
		}
	}
	
	public void del(){
		String ids = getPara("ids");
		for(String id : ids.split("-")){
			//check del
			Lottery lottery = Lottery.dao.findById(id);
			lottery.setActive(0);
			lottery.update();
		}
		rendSuccessJson("操作成功！");
	}
	
	public void wins(){
		render("lt_wins.html");
	}
	
	public void winlist(){
		final String select = " select l.lottery_name, b.nickname, p.prize_name, p.prize_single_cash, t.type_name ";
		final String sqlExceptSelect = "from t_prize_send_record psr "
		+ " LEFT JOIN t_lottery l on psr.activity_id = l.id "
		+ " LEFT JOIN t_buyer_user b on psr.buyer_id = b.id "
		+ " LEFT JOIN t_prize p on psr.prize_id = p.id "
		+ " LEFT JOIN t_prize_type t on p.prize_type_id = t.id "
		+ " where psr.activity_type = 2 and l.seller_id = ? ";
		Page<Record> pages = Db.paginate(getPageNo(), getPageSize(), select, sqlExceptSelect, getSellerId());
		rendSuccessJson(pages);
	}
	
	public void test(){
		Lottery lottery = Lottery.dao.findById(getPara(0));
		setAttr("lottery", lottery);
		if(lottery.getLotteryType() == LotteryType.L_JIUGONGGE.value) setAttr("action", "jiugongge");
		else if(lottery.getLotteryType() == LotteryType.L_GUAGUALE.value) setAttr("action", "guaguale");
		else if(lottery.getLotteryType() == LotteryType.L_YAOYIYAO.value) setAttr("action", "yaoyiyao");
		else if(lottery.getLotteryType() == LotteryType.L_FANFANLE.value) setAttr("action", "fanfanle");
		else if(lottery.getLotteryType() == LotteryType.L_ZHUANZHUAN.value) setAttr("action", "zhuanzhuan");
		else setAttr("action", "jiugongge");
		render("lt_test.html");
	}
	
	public void testdata(){
		Long lotteryId = getParaToLong("lotteryId");
		try {
			List<LotteryTestResultDto> result = lotteryService.test(lotteryId, getParaToInt("testCount"));
			rendSuccessJson(result);
		} catch (LotteryServiceException e) {
			rendFailedJson(e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see com.dbumama.market.web.common.controller.AdminBaseController#getModelClass()
	 */
	@Override
	protected Class<Lottery> getModelClass() {
		return Lottery.class;
	}
	
}
