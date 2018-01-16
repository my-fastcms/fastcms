package com.dbumama.market.web.admin.prize.controller;

import java.util.List;

import com.dbumama.market.model.PrizeType;
import com.dbumama.market.service.api.prize.PrizeService;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.BaseController;
import com.dbumama.market.web.core.plugin.spring.Inject.BY_NAME;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * 奖品发放情况查询
 * wjun_java@163.com
 * 2016年7月21日
 */
@RouteBind(path="prize/sendrcd", viewPath="prize")
public class PrizeSendRecordController extends BaseController{

	@BY_NAME
	private PrizeService prizeService;
	
	public void index(){
		List<PrizeType> prizeTypes = prizeService.getPrizeTypes();
		setAttr("prizeTypes", prizeTypes);
		render("p_send_record.html");
	}
	
	public void list(){
		/* 统计奖品发放情况  */
		final String select = "SELECT p.prize_name, pt.type_name, u.nickname, v.activity_name, v.activity_type, v.activity_type_name, psr.updated," 
					+ " CASE WHEN psr.`status` = 1 THEN '自动发放成功' " 
					+ "		 WHEN psr.`status` = 2 THEN '自动发放失败' "
					+ "		 WHEN psr.`status` = 3 THEN '待人工发放' "
					+ "			ELSE '人工发放成功' END as send_status ";
		final String sqlExceptSelect = "from t_prize_send_record psr "
				 +" INNER JOIN activities_view v on psr.activity_id = v.id and psr.activity_type = v.activity_type " 
				 +" LEFT JOIN t_prize p on psr.prize_id = p.id " 
				 +" LEFT JOIN t_prize_type pt on p.prize_type_id = pt.id " 
				 +" LEFT JOIN t_buyer_user u on psr.buyer_id = u.id " 
				 +" where v.seller_id = ? ";
		Page<Record> pages = Db.paginate(getPageNo(), getPageSize(), select, sqlExceptSelect, getSellerId());
		rendSuccessJson(pages);
	}
	
}
