package com.dbumama.market.service.provider;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dbumama.market.model.Qiandao;
import com.dbumama.market.model.QiandaoConfig;
import com.dbumama.market.model.QiandaoRecord;
import com.dbumama.market.model.QiandaoUser;
import com.dbumama.market.model.SellerUser;
import com.dbumama.market.service.api.exception.MarketBaseException;
import com.dbumama.market.service.api.prize.PrizeSendParamDto;
import com.dbumama.market.service.api.prize.PrizeSendResultDto;
import com.dbumama.market.service.api.prize.PrizeService;
import com.dbumama.market.service.api.qiandao.QiandaoException;
import com.dbumama.market.service.api.qiandao.QiandaoParamDto;
import com.dbumama.market.service.api.qiandao.QiandaoService;
import com.dbumama.market.service.api.qiandao.QiandaoSubmitParamDto;
import com.dbumama.market.service.api.qiandao.QiandaoSubmitResultDto;
import com.dbumama.market.service.utils.DateTimeUtil;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.ActiveRecordException;

/**
 * wjun_java@163.com
 * 2016年7月2日
 */
@Service("qiandaoService")
public class QiandaoServiceImpl extends AbstractActivityService implements QiandaoService{
	
	@Autowired
	private PrizeService prizeService;

	/* (non-Javadoc)
	 * @see com.dbumama.market.service.qiandao.QiandaoService#doSave(com.dbumama.market.model.Task, java.lang.String, com.dbumama.market.model.SellerUser)
	 */
	@Override
	@Transactional(rollbackFor = QiandaoException.class)
	public Qiandao doSave(Qiandao qiandao, String items, SellerUser sellerUser) 
			throws QiandaoException {
		if(StrKit.isBlank(items))
			throw new QiandaoException("签到参数错误");
		
		qiandao.setWirlessUrl("/qiandao/");
		//生成活动二维码
		/*try {
			String qrCode = genActivityCode(wirlessUrl, qiandao.getQiandaoName(), sellerUser.getSessionKey());
			qiandao.setQrCode(qrCode);
		} catch (Exception e1) {
			throw new QiandaoException("生成活动二维码出错");
		}*/
		
		try {
			qiandao.save();
		}catch (Exception e) {
			throw new QiandaoException("系统异常，保存签到出错");
		}
		
		JSONArray jsonArray = JSONArray.parseArray(items);
		for(int i=0;i<jsonArray.size();i++){
			JSONObject json = jsonArray.getJSONObject(i);
			QiandaoConfig qiandaoItem = new QiandaoConfig();
			qiandaoItem.setQiandaoId(qiandao.getId());
			qiandaoItem.set("sign_type", json.getString("signType"));
			qiandaoItem.set("sign_day", json.getString("signDay"));
			qiandaoItem.set("prize_id", json.getString("payPrize"));
			qiandaoItem.set("prize_name", json.getString("prizeName"));
			qiandaoItem.set("active", 1);
			qiandaoItem.set("created", new Date());
			qiandaoItem.set("updated", new Date());
			try {
				qiandaoItem.save();
			} catch (Exception e) {
				e.printStackTrace();
				throw new QiandaoException("系统异常，保存签到出错");
			}
		}
		return qiandao;
	}

	/* (non-Javadoc)
	 * @see com.dbumama.market.service.qiandao.QiandaoService#doUpdate(com.dbumama.market.model.Task, java.lang.String, com.dbumama.market.model.SellerUser)
	 */
	@Override
	@Transactional(rollbackFor = QiandaoException.class)
	public Qiandao doUpdate(Qiandao qiandao, String items, SellerUser sellerUser) 
			throws QiandaoException {
		if(StrKit.isBlank(items))
			throw new QiandaoException("签到参数错误");
		qiandao.setUpdated(new Date());
		if(StrKit.isBlank(qiandao.getQrCode())){
			qiandao.setWirlessUrl("/qiandao/");
		}
		
		try {
			qiandao.update();
		} catch (Exception e) {
			e.printStackTrace();
			throw new QiandaoException("系统异常，保存签到出错");
		}
		
		JSONArray jsonArray = JSONArray.parseArray(items);
		for(int i=0;i<jsonArray.size();i++){
			JSONObject json = jsonArray.getJSONObject(i);
			if(json.getLong("itemId") == null){
				QiandaoConfig qiandaoItem = new QiandaoConfig();
				qiandaoItem.setQiandaoId(qiandao.getId());
				qiandaoItem.set("sign_type", json.getString("signType"));
				qiandaoItem.set("sign_day", json.getString("signDay"));
				qiandaoItem.set("prize_id", json.getString("payPrize"));
				qiandaoItem.set("prize_name", json.getString("prizeName"));
				qiandaoItem.set("active", 1);
				qiandaoItem.set("created", new Date());
				qiandaoItem.set("updated", new Date());
				try {
					qiandaoItem.save();
				} catch (Exception e) {
					e.printStackTrace();
					throw new QiandaoException("系统异常，保存签到出错");
				}
			}
			
			String flag = json.getString("updateDel");
			if(StrKit.notBlank(flag) && "update".equals(flag)){
				QiandaoConfig qiandaoItem = QiandaoConfig.dao.findById(json.getLong("itemId"));
				if(qiandaoItem != null){
					qiandaoItem.setQiandaoId(qiandao.getId());
					qiandaoItem.set("sign_type", json.getString("signType"));
					qiandaoItem.set("sign_day", json.getString("signDay"));
					qiandaoItem.set("prize_id", json.getString("payPrize"));
					qiandaoItem.set("prize_name", json.getString("prizeName"));
					qiandaoItem.set("active", 1);
					qiandaoItem.set("updated", new Date());
					try {
						qiandaoItem.update();
					} catch (Exception e) {
						e.printStackTrace();
						throw new QiandaoException("系统异常，保存签到出错");
					}
				}
			}else if(StrKit.notBlank(flag) && "del".equals(flag)){
				QiandaoConfig qiandaoItem = QiandaoConfig.dao.findById(json.getLong("itemId"));
				if(qiandaoItem != null){
					try {
						qiandaoItem.delete();
					} catch (Exception e) {
						e.printStackTrace();
						throw new QiandaoException("系统异常，保存签到出错");
					}
				}
			}
		}
		return qiandao;
	}
	
	/* (non-Javadoc)
	 * @see com.dbumama.market.service.api.qiandao.QiandaoService#getCurrQiandao()
	 */
	@Override
	public String getCurrQiandao(QiandaoParamDto qiandaoParamDto) throws QiandaoException {
		Long buyerId = qiandaoParamDto.getBuyer().getId();
		Long sellerId = qiandaoParamDto.getSeller().getId();
		Date currDate = DateTimeUtil.nowDate();
		Qiandao qiandao = Qiandao.dao.findFirst("select * from " + Qiandao.table + " where seller_id=? " 
				+ "and start_date<=? and end_date >=? ", 
				sellerId, currDate, currDate);
		if(qiandao == null){
			throw new QiandaoException("公众号没有设置签到活动");
		}
		
		Map<String, String> result = new HashMap<String, String>();
		result.put("qiandao", JSON.toJSONString(qiandao));
		
		/*Date endDate = qiandao.get("end_date");
		result.put("end_timestamp", (endDate.getTime() - new Date().getTime())/1000 + "");*/
		
		//获取签到设置信息,1为连续签到，2为累计签到，3为指定日期签到
		List<QiandaoConfig> qiandaoItems = QiandaoConfig.dao.find("select * from " + QiandaoConfig.table 
				+ " where qiandao_id=? ", qiandao.getId());
		result.put("qiandaoItems", JSON.toJSONString(qiandaoItems));		
		
		String dateStr = DateTimeUtil.FORMAT_YYYY_MM.format(new Date());
		//查询粉丝当月签到流水记录
		List<QiandaoRecord> qiandaoRecords = QiandaoRecord.dao.find("select * from " + QiandaoRecord.table 
				+ " where qiandao_id=? and qiandao_month=? and buyer_id=? ", 
				qiandao.getId(), dateStr, buyerId);
		List<String> qiandaoTimeList = new ArrayList<String>();
		for(QiandaoRecord qRecord : qiandaoRecords) {
			Date qiandaoTime = qRecord.getQiandaoTime();
			String timeStr = DateTimeUtil.FORMAT_YYYY_MM_DD.format(qiandaoTime);
			String time = timeStr.split("-")[2];//获取当月签到日期比如7-30就是30
			if(time.startsWith("0")){
				time = time.substring(1, 2);
			}
			qiandaoTimeList.add(time);
		}
		result.put("qiandaoRecords", JSON.toJSONString(qiandaoTimeList));
		
		//查询粉丝针对该活动的签到明细记录
		QiandaoUser qiandaoUser = QiandaoUser.dao.findFirst("select * from " + QiandaoUser.table 
				+ " where buyer_Id=? and qiandao_id=? ", 
				buyerId, qiandao.getId());
		result.put("qiandaoUser", JSON.toJSONString(qiandaoUser));
		return JSON.toJSONString(result);
	}
	
	@Override
	@Transactional(rollbackFor = QiandaoException.class)
	public QiandaoSubmitResultDto submit(QiandaoSubmitParamDto qiandaoSubmitParamDto) throws QiandaoException {
		if(qiandaoSubmitParamDto == null 
				|| qiandaoSubmitParamDto.getQiandaoId() == null 
				|| qiandaoSubmitParamDto.getBuyer() == null
				|| qiandaoSubmitParamDto.getSeller() == null)
			throw new QiandaoException("调用签到接口提交参数不全，请核对");
		
		if(qiandaoSubmitParamDto.getBuyer().getId() ==null){
			throw new QiandaoException("客官，请先授权再签到");
		}
		
		Qiandao qiandao = Qiandao.dao.findById(qiandaoSubmitParamDto.getQiandaoId());
		if(qiandao == null) throw new QiandaoException("找不到签到活动");
		
		if(qiandao.getNeedCollectShop() != null && qiandao.getNeedCollectShop() == 1){
			if(qiandaoSubmitParamDto.getBuyer().getSubscribe() == null || qiandaoSubmitParamDto.getBuyer().getSubscribe() !=1){
				throw new QiandaoException("需要关注公众号才能签到");
			}
		}
		
		QiandaoUser qiandaoUser = QiandaoUser.dao.findFirst("select * from " + QiandaoUser.table 
				+ " where qiandao_id=? and buyer_id=? ", 
				qiandaoSubmitParamDto.getQiandaoId(), qiandaoSubmitParamDto.getBuyer().getId());
		
		if(qiandaoUser == null){
			qiandaoUser = new QiandaoUser();
			qiandaoUser.setQiandaoId(qiandaoSubmitParamDto.getQiandaoId());
			qiandaoUser.setBuyerId(qiandaoSubmitParamDto.getBuyer().getId());
			qiandaoUser.setCQiandaoCount(1);
			qiandaoUser.setQiandaoCount(1);
			qiandaoUser.setSignDate(new Date());
			qiandaoUser.setCreated(new Date());
			qiandaoUser.setUpdated(new Date());
			qiandaoUser.setActive(1);
			try {
				qiandaoUser.save();
			} catch (ActiveRecordException e) {
				throw new QiandaoException(e.getMessage());
			}
		}else {
			Date qiandaoTime = qiandaoUser.getSignDate();//获取签到时间
			//计算签到时间跟当前时间差，如果为0的话，说明是重复签到；1的话说明是连续签到；大于1的情况都算间断性签到
			int d = DateTimeUtil.compareDay(new Date(), qiandaoTime);
			if(d == 0){
				//说明当天已经签过到了
				throw new QiandaoException("你今天已经签过到了，明天再来吧");
			}else{
				//根据活动奖励规则进行奖励设置
				int cqiandaoCount = d == 1 ? qiandaoUser.getCQiandaoCount() + 1 : 1;
				//1 说明是连续签到 更新连续签到次数  （隔一天签就算不连续签到）不连续的话，连续签到次数从1开始计算
				qiandaoUser.setCQiandaoCount(cqiandaoCount);
				//每签到一次，记录签到次数
				qiandaoUser.setQiandaoCount(qiandaoUser.getQiandaoCount() + 1);
				qiandaoUser.setSignDate(new Date());//设置签到时间
				qiandaoUser.setUpdated(new Date());
				try {
					qiandaoUser.update();
				} catch (ActiveRecordException e) {
					throw new QiandaoException(e.getMessage());
				}
			}
		}
		
		//记录签到流水
		QiandaoRecord qiandaoRecord = new QiandaoRecord();
		qiandaoRecord.setQiandaoId(qiandaoSubmitParamDto.getQiandaoId());
		qiandaoRecord.setBuyerId(qiandaoSubmitParamDto.getBuyer().getId());
		qiandaoRecord.setQiandaoTime(new Date());
		qiandaoRecord.setQiandaoMonth(DateTimeUtil.FORMAT_YYYY_MM.format(new Date()));
		qiandaoRecord.setCreated(new Date());
		qiandaoRecord.setUpdated(new Date());
		try {
			qiandaoRecord.save();
		} catch (Exception e) {
			throw new QiandaoException(e.getMessage());
		}
		
		try {
			//奖励
			QiandaoSubmitResultDto result = reward(qiandao, qiandaoUser, qiandaoSubmitParamDto);
			return result;
		} catch (QiandaoException e) {
			e.printStackTrace();
			throw new QiandaoException(e.getMessage());
		}
		
	}

	//奖励
	private QiandaoSubmitResultDto reward(Qiandao qiandao, QiandaoUser qiandaoUser, QiandaoSubmitParamDto qiandaoSubmitParamDto) throws QiandaoException{
		List<QiandaoConfig> qiandaoItems = new ArrayList<QiandaoConfig>();
		QiandaoSubmitResultDto qiandaoResultDto = new QiandaoSubmitResultDto();
		
		//准备奖品发放公共参数
		PrizeSendParamDto prizeSendParamDto = new PrizeSendParamDto();
		prizeSendParamDto.setBuyer(qiandaoSubmitParamDto.getBuyer());
		prizeSendParamDto.setSeller(qiandaoSubmitParamDto.getSeller());
		prizeSendParamDto.setActivityId(qiandao.getId());
		prizeSendParamDto.setActivityType(qiandao.getActivityType());
		
		//看是否开启指定日签到，优先指定日期签到，奖励只能领取一次，领取指定日期的奖励后，不能领取连续签到的奖励
		if(qiandao.getAsSignPrize() == 1){
			qiandaoItems = QiandaoConfig.dao.find("select * from " + QiandaoConfig.table 
					+ " where qiandao_id=? and sign_type = 3", qiandao.getId());
			for(QiandaoConfig qiandaoItem : qiandaoItems){
				if(DateTimeUtil.toDateString(qiandaoUser.getSignDate()).equals(qiandaoItem.getSignDay())) {
					//送奖品
					prizeSendParamDto.setPrizeId(qiandaoItem.getPrizeId());
					prizeSendParamDto.setReason("指定日期签到奖励");
					try {
						PrizeSendResultDto prizeSendResultDto = prizeService.sendPrize(prizeSendParamDto);
						qiandaoResultDto.setMsg(prizeSendResultDto.getPrize().getPrizeName());
						qiandaoResultDto.setHasPrize(true);
						return qiandaoResultDto;
					} catch (MarketBaseException e) {
						throw new QiandaoException(e.getMessage());
					}
				}
			}
		}
		
		//检查连续签到或者累计签到
		qiandaoItems = QiandaoConfig.dao.find("select * from " + QiandaoConfig.table 
				+ " where qiandao_id=? and sign_type = ? ", qiandao.getId(), qiandao.getQiandaoType());
		for(QiandaoConfig qiandaoItem : qiandaoItems){
			//签到是累计送，还是连续送
			if(qiandao.getQiandaoType() == 1){//1连续送
				if(qiandaoUser.getCQiandaoCount() % Integer.parseInt(qiandaoItem.getSignDay()) == 0){
					//送奖品
					prizeSendParamDto.setPrizeId(qiandaoItem.getPrizeId());
					prizeSendParamDto.setReason("连续签到奖励");
					try {
						PrizeSendResultDto prizeSendResultDto = prizeService.sendPrize(prizeSendParamDto);
						qiandaoResultDto.setMsg(prizeSendResultDto.getPrize().getPrizeName());
						qiandaoResultDto.setHasPrize(true);
						return qiandaoResultDto;
					} catch (MarketBaseException e) {
						throw new QiandaoException(e.getMessage());
					}
				}
			}else if(qiandao.getQiandaoType() == 2){//2累计送
				if(Integer.parseInt(qiandaoItem.getSignDay()) == qiandaoUser.getQiandaoCount()){
					//送奖品
					prizeSendParamDto.setPrizeId(qiandaoItem.getPrizeId());
					prizeSendParamDto.setReason("累计签到奖励");
					try {
						PrizeSendResultDto prizeSendResultDto = prizeService.sendPrize(prizeSendParamDto);
						qiandaoResultDto.setMsg(prizeSendResultDto.getPrize().getPrizeName());
						qiandaoResultDto.setHasPrize(true);
						return qiandaoResultDto;
					} catch (MarketBaseException e) {
						throw new QiandaoException(e.getMessage());
					}
				}
			}	
		}
		
		return qiandaoResultDto;
	}

}
