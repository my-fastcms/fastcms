package com.dbumama.market.web.admin.lottery.controller;

import java.util.List;

import com.dbumama.market.model.LotteryAward;
import com.dbumama.market.model.Prize;
import com.dbumama.market.model.PrizeType;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.AdminBaseController;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;

/**
 * wjun_java@163.com
 * 2016年6月30日
 */
@RouteBind(path="lottery/award", viewPath="lottery")
public class LotteryAwardController extends AdminBaseController<LotteryAward>{
	
	public void list(){
		final String sql = "select award.*, prize.prize_type_id, prize.publish_count, prize.had_out_count, pt.type_name "
				+ "from " + LotteryAward.table + " award "
				+ " left join " + Prize.table + " prize on award.prize_id = prize.id "
				+ " left join " + PrizeType.table + " pt on prize.prize_type_id = pt.id "
				+ " where award.lottery_id = ? ";
		
		List<Record> records = Db.find(sql, getPara("lotteryId"));
		rendSuccessJson(records);
	}
	
	public void show(){
		rendSuccessJson(LotteryAward.dao.findById(getPara("awardId")));
	}

	@Before(Tx.class)
	public void save(){
		try {
			LotteryAward award = getModel();
			if(award.getId() == null){
				//check
				List<LotteryAward> awards = LotteryAward.dao.find("select * from " + LotteryAward.table
						+ " where lottery_id=? ", award.getLotteryId());
				if(awards != null && awards.size() >= 7){
					rendFailedJson("最多增加7个奖品");
					return;
				}
				//对位置进行处理，删除之前位置的奖品
				int pos = award.getAwardPos();
				Long lotteryId = award.getLotteryId();
				LotteryAward aw = LotteryAward.dao.findFirst(" select * from " + LotteryAward.table
						+ " where lottery_id=? and award_pos=? ", lotteryId, pos);
				if(aw != null){
					aw.delete();
				}
				award.save();
			}else {
				award.update();
			}
			rendSuccessJson();
		} catch (Exception e) {
			e.printStackTrace();
			rendFailedJson(e.getMessage());
		}
	}
	
	public void del(){
		LotteryAward.dao.deleteById(getPara("id"));
		rendSuccessJson();
	}
	
	/* (non-Javadoc)
	 * @see com.dbumama.market.web.common.controller.AdminBaseController#getModelClass()
	 */
	@Override
	protected Class<LotteryAward> getModelClass() {
		return LotteryAward.class;
	}

}
