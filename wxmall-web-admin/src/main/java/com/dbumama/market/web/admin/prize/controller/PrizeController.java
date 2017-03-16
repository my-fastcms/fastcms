/**
 * 文件名:PrizeController.java
 * 版本信息:1.0
 * 日期:2015-5-9
 * Copyright 广州点步信息科技
 * 版权所有
 */
package com.dbumama.market.web.admin.prize.controller;

import java.util.ArrayList;
import java.util.List;

import com.dbumama.market.model.Prize;
import com.dbumama.market.model.PrizeType;
import com.dbumama.market.service.api.prize.PrizeResultDto;
import com.dbumama.market.service.utils.DateTimeUtil;
import com.dbumama.market.web.admin.prize.validator.PrizeValidator;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.AdminBaseController;
import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;

/**
 * @author: wjun.java@gmail.com
 * @date:2015-5-9
 */
@RouteBind(path="prize", viewPath="prize")
public class PrizeController extends AdminBaseController<Prize>{
	
	private static final String CACHENAME = "/prize/list";
	private static final String CACHEKEY = "prize_list_date";
	
	public void index(){
		//读取prizeType信息
		List<PrizeType> prizeTypes = PrizeType.dao.find(" select * from " + PrizeType.table + " where active=1 ");
		setAttr("prizeTypes", prizeTypes);
		this.render("p_index.html");
	}
	
	public void selectPrize(){
		List<PrizeType> prizeTypes = PrizeType.dao.find(" select * from " + PrizeType.table + " where active=1 ");
		setAttr("prizeTypes", prizeTypes);
	}
	
//	@Before(CacheInterceptor.class)
	public void list(){
		List<Object> params = new ArrayList<Object>();
		params.add(getSellerId());
		StringBuilder sqlBuilder = new StringBuilder(" from "+Prize.table + " p " 
				+ " left join " + PrizeType.table + " pt on p.prize_type_id = pt.id "
				+ " where p.seller_id = ? and p.active=1 and pt.active=1 ");
		if(getParaToInt("type")!=null){
			sqlBuilder.append(" and prize_type_id=? ");
			params.add(getParaToInt("type"));
		}
		if(StrKit.notBlank(getPara("qname"))){
			sqlBuilder.append(" and prize_name like ? ");
			params.add("%" +getPara("qname")+ "%");
		}
		sqlBuilder.append(" order by created desc ");
		Object [] o = new Object[params.size()];
		for(int i=0;i<params.size();i++){
			o [i] = params.get(i); 
		}
		
		Page<Record> prizePage = Db.paginateByCache(CACHENAME, CACHEKEY + getPara("qname") + getParaToInt("type") + "_" + getPageNo(), getPageNo(), getPageSize(), 
				"select p.*, pt.id as type_id, pt.type_name, pt.type_code ",
				sqlBuilder.toString(),
				o);
		
		List<PrizeResultDto> prizeVos = new ArrayList<PrizeResultDto>();
		for(Record prize : prizePage.getList()){
			PrizeResultDto prizeVo = new PrizeResultDto();
			prizeVo.setId(prize.getLong("id"));
			prizeVo.setPrizeImg(prize.getStr("prize_img"));
			prizeVo.setPrizeName(prize.getStr("prize_name"));
			prizeVo.setPrizePrice(prize.getStr("prize_price"));
			prizeVo.setPublishCount(prize.getInt("publish_count").toString());
			prizeVo.setHadOutCount(prize.getInt("had_out_count").toString());
			prizeVo.setPrizeTypeId(prize.getLong("type_id"));
			prizeVo.setPrizeType(prize.getStr("type_name"));
			prizeVo.setTypeCode(prize.getStr("type_code"));
			if(prize.getInt("active") == 1){
				prizeVo.setStatus("有效");
			}else{
				prizeVo.setStatus("已删除");
			}
			if(prize.getDate("start_date") != null){
				prizeVo.setStartDate(DateTimeUtil.toDateString(prize.getDate("start_date")));
			}
			if(prize.getDate("end_date") != null){
				prizeVo.setEndDate(DateTimeUtil.toDateString(prize.getDate("end_date")));
			}
			prizeVos.add(prizeVo);
		}
		
		Page<PrizeResultDto> pageVos = new Page<PrizeResultDto>(prizeVos, getPageNo(), getPageSize(), prizePage.getTotalPage(), prizePage.getTotalRow());
		rendSuccessJson(pageVos);
	}
	
	@Before(PrizeValidator.class)
	public void save() {
		CacheKit.removeAll(CACHENAME);
		Prize prize = null;
		try {
			prize = getModel();
			int prizeTypeId = Integer.valueOf(getPara("prize_type_id"));
			PrizeType prizeType = PrizeType.dao.findById(prizeTypeId);
			if("redpack".equals(prizeType.getTypeCode())){
				if(getSellerId() == 1){
					rendFailedJson("体验账号不可以创建红包");
					return;
				}
			}
			
			if(prize.getLong("id") != null)
				prize.update();
			else {
				prize.setSellerId(getSellerId());
				prize.setActive(1);
				prize.save();
			}
			rendSuccessJson("操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			rendFailedJson("保存失败！");
		}
	}
	
	public void del() {
		CacheKit.removeAll(CACHENAME);
		String ids = getPara("ids");
		for(String id : ids.split("-")){
			Prize prize = Prize.dao.findById(id);
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
