package com.dbumama.market.web.admin.agent.controller;

import java.math.BigDecimal;

import com.dbumama.market.model.CommissionRate;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.AdminBaseController;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * 佣金
 * @author wangjun
 *
 */
@RouteBind(path="commission")
public class CommissionController extends AdminBaseController<CommissionRate>{

	/**
	 * 各分销商佣金
	 */
	public void index(){
		render("/agent/commission_index.html");
	}
	
	/**
	 * 各分销商佣金列表
	 */
	public void list (){
		final String select = "select ac.total_commission, a.id, bu.nickname, bu.headimgurl, a.agent_name, a.agent_phone ";
		String sqlExceptSelect = "from "
		+ " (select SUM(commission_value) as total_commission, agent_id from t_agent_commission group BY agent_id) ac "
		+ " LEFT JOIN t_agent a on ac.agent_id = a.id "
		+ " LEFT JOIN t_buyer_user bu on a.buyer_id = bu.id " 
		+ " where a.seller_id = ? ";
		
		Page<Record> records = Db.paginate(getPageNo(), getPageSize(), select, sqlExceptSelect, getSellerId());
		rendSuccessJson(records);
	}
	
	/**
	 * 佣金占比设置
	 */
	public void rate(){
		CommissionRate rate = CommissionRate.dao.findFirst("select * from " + CommissionRate.table + " where seller_id=?", getSellerId());
		if(rate != null) setAttr("rate", rate);
		render("/agent/commission_rate_set.html");
	}
	
	/**
	 * 保存佣金比率设置
	 */
	public void saveRate(){
		try {
			CommissionRate rate = getModel();
			BigDecimal total = rate.getSelfUpRate().add(rate.getSecondUpRate()).add(rate.getThirdUpRate()).setScale(2, BigDecimal.ROUND_HALF_UP);
			if(total.intValue() > 50){
				rendFailedJson("总比率不能超过订单金额的50%");
				return;
			}
			if(rate.getId() == null){
				rate.setSellerId(getSellerId());
				rate.setActive(true);
				rate.save();
			}else{
				rate.update();
			}
			rendSuccessJson();
		} catch (Exception e) {
			rendFailedJson(e.getMessage());
		}
	}
	
	@Override
	protected Class<CommissionRate> getModelClass() {
		return CommissionRate.class;
	}

}
