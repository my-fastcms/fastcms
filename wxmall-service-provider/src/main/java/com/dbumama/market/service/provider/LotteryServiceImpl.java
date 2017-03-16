package com.dbumama.market.service.provider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dbumama.market.model.BuyerUser;
import com.dbumama.market.model.Lottery;
import com.dbumama.market.model.LotteryAward;
import com.dbumama.market.model.LotteryRecord;
import com.dbumama.market.model.LotteryTrade;
import com.dbumama.market.model.Prize;
import com.dbumama.market.model.PrizeType;
import com.dbumama.market.model.SellerUser;
import com.dbumama.market.service.api.exception.MarketBaseException;
import com.dbumama.market.service.api.lottery.DrawResultDto;
import com.dbumama.market.service.api.lottery.LotteryResultDto;
import com.dbumama.market.service.api.lottery.LotteryService;
import com.dbumama.market.service.api.lottery.LotteryServiceException;
import com.dbumama.market.service.api.lottery.LotteryTestResultDto;
import com.dbumama.market.service.api.prize.PrizeSendParamDto;
import com.dbumama.market.service.api.prize.PrizeService;
import com.dbumama.market.service.constants.Constants;
import com.dbumama.market.service.enmu.LotteryCondType;
import com.dbumama.market.service.enmu.LotteryType;
import com.dbumama.market.service.utils.DateTimeUtil;
import com.dbumama.market.service.utils.StringUtils;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.ActiveRecordException;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * wjun_java@163.com
 * 2016年6月27日
 */
@Service("lotteryService")
public class LotteryServiceImpl extends AbstractActivityService implements LotteryService{
	
	//总共8个奖品
	static final int TOTAL_AWARD_COUNT = 8;

	@Autowired
	private PrizeService prizeService;
	
	/* (non-Javadoc)
	 * @see com.dbumama.market.service.lottery.LotteryService#save(com.dbumama.market.model.Lottery)
	 */
	@Override
	@Transactional(rollbackFor = LotteryServiceException.class)
	public Lottery save(Lottery lottery, SellerUser seller, String condition) throws LotteryServiceException {
		
		try {
			lottery.save();
		}catch (ActiveRecordException e) {
			e.printStackTrace();
			throw new LotteryServiceException("系统异常，保存出错");
		}
		
		/*final String wirlessUrl = Constants.WUXIAN_DOMIAN + "/lottery/draw/?snick=" + StringUtils.urlEncode(seller.getNick()) + "&lotteryId="+lottery.getId();
		lottery.setWirlessUrl(wirlessUrl);*/
		
		/*try {
			String qrCode = genActivityCode(wirlessUrl, lottery.getLotteryName(), seller.getSessionKey());
			lottery.setQrCode(qrCode);
		} catch (ApiException e) {
			throw new LotteryServiceException("生成抽奖活动二维码出错");
		}*/

		try {
			lottery.update();
		} catch (ActiveRecordException e) {
			throw new LotteryServiceException("系统异常，保存出错");
		}
		
		if(StrKit.notBlank(condition)){
			if(lottery.getConditionType() == 2){//表示选择的是下单抽奖的条件
				JSONObject jsonObject = (JSONObject) JSON.parse(condition);
				LotteryTrade lottcondTrade = new LotteryTrade();
				lottcondTrade.setLotteryId(lottery.getId());
				lottcondTrade.setTradeType(jsonObject.getInteger("trade_type"));
				lottcondTrade.setConditionDay(jsonObject.getInteger("condition_day"));
				lottcondTrade.setConditionTradeEnddate(jsonObject.getDate("condition_trade_enddate"));
				lottcondTrade.setConditionTradeStartdate(jsonObject.getDate("condition_trade_startdate"));
				lottcondTrade.setTradeStatus(jsonObject.getInteger("trade_status"));
				lottcondTrade.setTradeMoney(jsonObject.getInteger("trade_money"));
				lottcondTrade.setActive(1);
				lottcondTrade.setCreated(new Date());
				lottcondTrade.setUpdated(new Date());
				try {
					lottcondTrade.save();	
				} catch (ActiveRecordException e) {
					e.printStackTrace();
					throw new LotteryServiceException("系统异常，保存出错");
				}
			}
		}
		return lottery;
	}

	/* (non-Javadoc)
	 * @see com.dbumama.market.service.lottery.LotteryService#update(com.dbumama.market.model.Lottery)
	 */
	@Override
	@Transactional(rollbackFor = LotteryServiceException.class)
	public Lottery update(Lottery lottery, SellerUser seller, String condition) throws LotteryServiceException {
		
		if(StrKit.isBlank(lottery.getQrCode())){
			final String wirlessUrl = Constants.WUXIAN_DOMIAN + "/lottery/draw/?snick=" + StringUtils.urlEncode(seller.getNick() + "&lotteryId="+lottery.getId());
			lottery.setWirlessUrl(wirlessUrl);
			/*try {
				String qrCode = genActivityCode(wirlessUrl, lottery.getLotteryName(), seller.getSessionKey());
				lottery.setQrCode(qrCode);
			} catch (ApiException e) {
				throw new LotteryServiceException("生成抽奖活动二维码出错");
			}*/
		}
		
		try {
			lottery.update();
		}catch (ActiveRecordException e) {
			e.printStackTrace();
			throw new LotteryServiceException("系统异常，更新出错");
		}
		if(StrKit.notBlank(condition)){
			if(lottery.getConditionType() == 2){//表示选择的是下单抽奖的条件
				JSONObject jsonObject = (JSONObject) JSON.parse(condition);
				LotteryTrade lottcondTrade;
				Long tradeId = jsonObject.getLong("id");
				if(tradeId == null) lottcondTrade = new LotteryTrade();
				else lottcondTrade = LotteryTrade.dao.findById(tradeId); 
				lottcondTrade.setLotteryId(lottery.getId());
				lottcondTrade.setTradeType(jsonObject.getInteger("trade_type"));
				lottcondTrade.setConditionDay(jsonObject.getInteger("condition_day"));
				lottcondTrade.setConditionTradeEnddate(jsonObject.getDate("condition_trade_enddate"));
				lottcondTrade.setConditionTradeStartdate(jsonObject.getDate("condition_trade_startdate"));
				lottcondTrade.setTradeStatus(jsonObject.getInteger("trade_status"));
				lottcondTrade.setTradeMoney(jsonObject.getInteger("trade_money"));
				lottcondTrade.setActive(1);
				lottcondTrade.setUpdated(new Date());
				try {
					if(lottcondTrade.getId() == null){
						lottcondTrade.save();
					}else{
						lottcondTrade.update();	
					}
				} catch (ActiveRecordException e) {
					e.printStackTrace();
					throw new LotteryServiceException("系统异常，保存出错");
				}
			}
		}
		return lottery;
	}

	/* (non-Javadoc)
	 * @see com.dbumama.market.service.lottery.LotteryService#list(int, int)
	 */
	@Override
	public Page<LotteryResultDto> list(int pageNo, int pageSize, Map<String, String> paramsMap) throws LotteryServiceException {
		List<Object> params = new ArrayList<Object>();
		if(StrKit.isBlank(paramsMap.get("sellerId")))
			throw new LotteryServiceException("卖家Id必传参数");
		
		params.add(paramsMap.get("sellerId"));
		
		StringBuilder sqlBuilder = new StringBuilder("from " + Lottery.table + " where seller_id = ? and active=1 ");
		if(StrKit.notBlank(paramsMap.get("qname"))){
			sqlBuilder.append(" and lottery_name like ? ");
			params.add("%" +paramsMap.get("qname")+ "%");
		}
		sqlBuilder.append(" order by updated desc ");
		Object [] o = new Object[params.size()];
		for(int i=0;i<params.size();i++){
			o [i] = params.get(i); 
		}
		Page<Lottery> lotteryPage = Lottery.dao.paginate(pageNo, pageSize, 
				"select * ", 
				sqlBuilder.toString(), 
				o);
		
		List<LotteryResultDto> lotteryVos = new ArrayList<LotteryResultDto>();
		for(Lottery lottery : lotteryPage.getList()){
			LotteryResultDto lvo = new LotteryResultDto();
			lvo.setId(lottery.getId());
			lvo.setLotteryName(lottery.getLotteryName());
			lvo.setStartDate(lottery.getStartDate());
			lvo.setEndDate(lottery.getEndDate());
			lvo.setQrCode(lottery.getQrCode());
			if(getUsedAuthUser(Long.valueOf(paramsMap.get("sellerId"))) != null){
				lvo.setWirlessUrl("http://"+getUsedAuthUser(Long.valueOf(paramsMap.get("sellerId"))).getAppId()+".dbumama.com/lottery/draw/?lotteryId="+lottery.getId());
			}
			lvo.setLotteryType(lottery.getLotteryType());
			if(lottery.getLotteryType() == LotteryType.L_JIUGONGGE.value){
				lvo.setLotteryTypeCH("九宫格");
			}else if(lottery.getLotteryType() == LotteryType.L_GUAGUALE.value){
				lvo.setLotteryTypeCH("刮刮乐");
			}else if(lottery.getLotteryType() == LotteryType.L_YAOYIYAO.value){
				lvo.setLotteryTypeCH("摇一摇");
			}else if(lottery.getLotteryType() == LotteryType.L_FANFANLE.value){
				lvo.setLotteryTypeCH("翻翻乐");
			}else if(lottery.getLotteryType() == LotteryType.L_ZHUANZHUAN.value){
				lvo.setLotteryTypeCH("大转盘");
			}else {
				lvo.setLotteryTypeCH("未知类型");
			}
			if(DateTimeUtil.nowDate().after(lvo.getEndDate())){
				lvo.setStatus("已结束");
			}
			if(DateTimeUtil.nowDate().before(lvo.getEndDate())){
				lvo.setStatus("未开始");
			}
			if(DateTimeUtil.nowDate().after(lvo.getStartDate()) && DateTimeUtil.nowDate().before(lvo.getEndDate())){
				lvo.setStatus("进行中");
			}
			
			//总天数
			int totalDay = Math.abs(DateTimeUtil.compareDay(lvo.getStartDate(), lvo.getEndDate()));
			int hadDay = Math.abs(DateTimeUtil.compareDay(DateTimeUtil.nowDate(), lvo.getStartDate()));
			int leftDay = totalDay - hadDay;
			lvo.setHadDay(hadDay);
			lvo.setLeftDay(leftDay);
			
			Long awards = Db.queryLong(" select count(*) from " + LotteryAward.table + " where lottery_id=? ", lottery.getId());
			if(awards == 0){
				lvo.setHadAwardStatus("未设置奖品");
			}
			
			lotteryVos.add(lvo);
		}
		
		return new Page<LotteryResultDto>(lotteryVos, pageNo, pageSize, lotteryPage.getTotalPage(), lotteryPage.getTotalRow());
	}

	/* (non-Javadoc)
	 * @see com.dbumama.market.service.api.lottery.LotteryService#lottery(com.dbumama.market.model.Lottery, java.lang.Long, java.lang.Long)
	 */
	@Override
	@Transactional(rollbackFor = LotteryServiceException.class)
	public DrawResultDto lottery(Long lotteryId, Long sellerId, Long buyerId) throws LotteryServiceException {
		Lottery lottery = Lottery.dao.findById(lotteryId);
		if (lottery == null || lottery.getActive() != 1) {
			throw new LotteryServiceException("奖池不存在或没有激活！");
		}
		if(buyerId == null)
			throw new LotteryServiceException("客官，请先授权再抽奖！");
		
		SellerUser seller = SellerUser.dao.findById(sellerId);
		if(seller == null) throw new LotteryServiceException("掌柜的失踪了。。。");

		List<LotteryAward> lawards = LotteryAward.dao
				.find("select * from " + LotteryAward.table + " where lottery_id=? ", lottery.getId());
		
		if(lawards == null || lawards.size()<=0)
			throw new LotteryServiceException("奖池中没有设置奖品，不可抽奖！");
		
		//进行行锁，防止并发
		BuyerUser buyer = BuyerUser.dao.findFirst
				(" select * from " + BuyerUser.table + " where id=? for update ", buyerId);
		
		if(buyer == null || buyer.getActive() != 1)
			throw new LotteryServiceException("粉丝不存在或者被禁了，请联系公众号客服");

		//check 用户抽奖条件检查
		try {
			checkCondition(lottery, seller, buyer);	
		} catch (Exception e) {
			throw new LotteryServiceException(e.getMessage());
		}
		
		int all_count = lottery.getAllCount();// 用户总抽奖上限
		if(all_count >0){//0为不做限制
			Long userAllCount = Db.queryLong(
					"Select count(id) From " + LotteryRecord.table + " where buyer_id=? and lottery_id=? ", 
					buyer.getId(), lottery.getId());
			if (userAllCount != null && userAllCount.intValue() >= all_count) {
				throw new LotteryServiceException("您抽奖的次数已经超过最大抽奖次数，不能再抽奖了！");
			}
		}
		
		int day_count = lottery.getDayCount();// 用户当日抽奖上限
		if(day_count >0){//0为不做限制
			Long userDayCount = Db.queryLong(
					"Select count(id) From " + LotteryRecord.table
							+ " where buyer_id=? and lottery_id=? and lottery_time between ? And ? ",
					buyerId, lotteryId,
					DateTimeUtil.toDate(DateTimeUtil.FORMAT_YYYY_MM_DD.format(new Date()) + " 00:00:00"), new Date());
			if (userDayCount !=null && userDayCount.intValue() >= day_count) {
				throw new LotteryServiceException("您抽奖的次数已经超过单日最大抽奖次数，明天再来吧！");
			}
		}
		
		//概率
		List<Double> sortOrignalRates = getSortOrignalRates(lawards);
		// 取出抽奖物品，随机抽奖
		int index = lottery(sortOrignalRates);
		
		DrawResultDto dResult = new DrawResultDto();
		if(index>=0 && index<lawards.size() && lawards.get(index) != null) {
			//表示中奖
			//返回中奖奖品的位置
			PrizeSendParamDto prizeSendParamDto = new PrizeSendParamDto();
			prizeSendParamDto.setSeller(seller);
			prizeSendParamDto.setBuyer(buyer);
			prizeSendParamDto.setActivityId(lottery.getId());
			prizeSendParamDto.setActivityType(lottery.getActivityType());
			prizeSendParamDto.setPrizeId(lawards.get(index).getPrizeId());
			try {
				prizeService.sendPrize(prizeSendParamDto);
			} catch (MarketBaseException e) {
				throw new LotteryServiceException(e.getMessage());
			}
			if(lottery.getLotteryType() == LotteryType.L_JIUGONGGE.value){
				dResult.setIndex(lawards.get(index).getAwardPos());	
			}else{
				dResult.setIndex(index);
			}
			dResult.setLuck(true);
			dResult.setName(lawards.get(index).getAwardName());
		}else {
			//不能落到中奖的位置
			dResult.setIndex(index);
			dResult.setLuck(false);
			dResult.setName("感谢参与");
		}
		//不管中奖没中奖都需要根据抽奖条件对抽奖用户进行扣除相应的积分
		pay4Lottery(lottery, seller, buyer);
		
		// 增加一条抽奖记录
		LotteryRecord lotteryRecord = new LotteryRecord();
		lotteryRecord.setBuyerId(buyerId);
		lotteryRecord.setLotteryId(lottery.getId());
		lotteryRecord.setLotteryTime(new Date());
		lotteryRecord.setLotteryMonth(DateTimeUtil.FORMAT_YYYY_MM.format(new Date()));
		lotteryRecord.setCreated(new Date());
		lotteryRecord.setUpdated(new Date());
		lotteryRecord.save();
		return dResult;
	}
	
	/**
	 * 用户抽奖后，针对不同的抽奖条件，对用户进行相关消费操作
	 * @param lottery
	 * @param seller
	 * @param buyer
	 */
	private void pay4Lottery(Lottery lottery, SellerUser seller, BuyerUser buyer) throws LotteryServiceException {
		if(lottery.getConditionType() == LotteryCondType.COND_TRADE.value){
			
		}else if(lottery.getConditionType() == LotteryCondType.COND_JIFEN.value){
			//积分
			buyer.setScore(buyer.getScore() - lottery.getNeedPay() < 0 ? 0 : buyer.getScore() - lottery.getNeedPay());
			buyer.update();
		}else if(lottery.getConditionType() == LotteryCondType.COND_TAOJINB.value){
			/*try {
				//淘金币
				CoinBuyerConsumeRequest req = new CoinBuyerConsumeRequest();
				req.setBuyerNick(buyer.getRealNick());
				req.setAmount(Long.valueOf(lottery.getNeedPay()));
				req.setComments("参与抽奖[" + lottery.getLotteryName() + "]消费");
				req.setFeatureString("{bizId:"+lottery.getId()+"}");
				req.setOutId(lottery.getId());
				req.setOutParentId(lottery.getId());
				req.setToken("1d502118-cd63-4657-8e40-612d52eb5011");
				CoinBuyerConsumeResponse rsp = taobaoClientFactory.get().execute(req);
				if(rsp == null || !rsp.isSuccess()) throw new LotteryServiceException("扣除您的淘金币失败，本次抽奖不算");
			} catch (ApiException e) {
				e.printStackTrace();
				throw new LotteryServiceException(e.getMessage());
			}*/
		}else if(lottery.getConditionType() == LotteryCondType.COND_NO.value){
			//不做处理
		}else {
			throw new LotteryServiceException("不支持的消费类型");
		}
	}
	
	private void checkCondition(Lottery lottery, SellerUser seller, BuyerUser user) throws LotteryServiceException{
		//是否开启强制关注
		if(lottery.getNeedCollectShop() !=null && lottery.getNeedCollectShop() == 1){
			if(user.getSubscribe() == null || user.getSubscribe() !=1){
				throw new LotteryServiceException("需要关注公众号，才能抽奖");
			}
		}
		
		//检查用户抽奖条件
		if(lottery.getConditionType() == LotteryCondType.COND_TRADE.value){
			//下单抽奖
			LotteryTrade lottcondTrade = LotteryTrade.dao.findFirst
					("select * from " + LotteryTrade.table + " where lottery_id=?", lottery.getId());
			if(lottcondTrade == null) throw new LotteryServiceException("抽奖失败，系统数据异常，请联系管理员");
			
			/*String startDate = "";
			String endDate = "";
			if(lottcondTrade.getTradeType() == 1){
				//根据天数来获取时间段
				int day = lottcondTrade.getConditionDay();
				//当前用户抽奖的时间
				startDate = DateTimeUtil.getPreviousDateTimeString(day);
				endDate = DateTimeUtil.getDateTimeString();
			}else {
				//type 为 2 直接指定时间段
				startDate = DateTimeUtil.toDateTimeString(lottcondTrade.getConditionTradeStartdate());
				endDate = DateTimeUtil.toDateTimeString(lottcondTrade.getConditionTradeEnddate());
			}*/
			
			//获取订单
			/*try {
				//订单状态
				//订单金额
				int tradeStatus = lottcondTrade.getTradeStatus();
				String status = tradeStatus == 1 ? "TRADE_FINISHED" : "WAIT_SELLER_SEND_GOODS";
				int tradeMoney = lottcondTrade.getTradeMoney();
				List<Trade> tradeList = tradeService.getTradeList(seller, startDate, endDate);
				boolean hasPass = false;
				for(Trade trade : tradeList){
					if(trade.getBuyerNick().equals(user.getRealNick())
							&& status.equals(trade.getStatus())
							&& Integer.valueOf(trade.getTotalFee())>=tradeMoney) {
						hasPass = true;
					}
				}
				if(!hasPass) throw new LotteryServiceException("你没有符合要求的订单，不允许抽奖");
			} catch (ApiException e) {
				e.printStackTrace();
				throw new LotteryServiceException(e.getMessage());
			}*/
			
		}else if(lottery.getConditionType() == LotteryCondType.COND_JIFEN.value){
			//积分抽奖
			if(user.getScore()<lottery.getNeedPay())
				throw new LotteryServiceException("你的积分不够本次抽奖");
		}else if(lottery.getConditionType() == LotteryCondType.COND_TAOJINB.value){
			/*try {
				//淘金币抽奖
				CoinBuyerSumRequest req = new CoinBuyerSumRequest();
				CoinBuyerSumResponse rsp = taobaoClientFactory.get().execute(req, seller.getSessionKey());
				if(rsp == null || !rsp.isSuccess()) throw new LotteryServiceException("获取你的淘金币余额失败");
				if(rsp.getAmount() == 0 || rsp.getAmount() < lottery.getNeedPay()){
					throw new LotteryServiceException("你的淘金币数量不足，下单可以赚钱更多的淘金币");
				}
			} catch (ApiException e) {
				e.printStackTrace();
				throw new LotteryServiceException(e.getMessage());
			}*/
		}else if(lottery.getConditionType() == LotteryCondType.COND_NO.value){
			//不做条件限制
		}else {
			throw new LotteryServiceException("不支持的抽奖条件类型");
		}
	}
	
	/**
	 * 抽奖
	 * @param orignalRates
	 * 原始的概率列表，保证顺序和实际物品对应
	 * @return 物品的索引
	 */
	private int lottery(List<Double> orignalRates) {
		if (orignalRates == null || orignalRates.isEmpty()) {
			return -1;
		}
		int size = orignalRates.size();
		// 计算总概率，这样可以保证不一定总概率是1
		double sumRate = 0d;
		for (double rate : orignalRates) {
			sumRate += rate;
		}
		// 计算每个物品在总概率的基础下的概率情况
		List<Double> sortOrignalRates = new ArrayList<Double>(size + 1);
		Double tempSumRate = 0d;
		for (double rate : orignalRates) {
			tempSumRate += rate;
			sortOrignalRates.add(tempSumRate / sumRate);
		}
		// 根据区块值来获取抽取到的物品索引
		double nextDouble = Math.random();
		sortOrignalRates.add(nextDouble);
		Collections.sort(sortOrignalRates);
		int index = sortOrignalRates.indexOf(nextDouble);
		// 最后的一个概率，向前推一步
		if (index == size) {
			return size - 1;
		} else {
			return index;
		}
	}
	
	private List<Double> getSortOrignalRates(List<LotteryAward> lawards) {
		List<Double> totalRates = new ArrayList<Double>();
		for(LotteryAward award : lawards){
			totalRates.add(award.getAwardRate());
		}
		return getSortOrignalRatesByRecord(totalRates);
	}
	
	private List<Double> getSortOrignalRatesByRecord(List<Double> totalRate){
		//概率
		List<Double> sortOrignalRates = new ArrayList<Double>(totalRate.size());
		double awardTotalRate = 0d;
		//需要根据会员充值等级加载不同的中奖概率
		for (Double lv : totalRate) {
			// 奖品概率
			double probility = lv / 100d;
			if(probility <0) probility = 0d;
			sortOrignalRates.add(probility);
			awardTotalRate += probility;
		}
		//剩余的没有设置奖品的位置数量
		int leftCount = TOTAL_AWARD_COUNT - totalRate.size();
		Double leftRate = 1-awardTotalRate;
		for(int i=0;i<leftCount;i++){
			sortOrignalRates.add(leftRate/leftCount);
		}
		return sortOrignalRates;
	}
	
	/**
	 * 获取剩余的中奖概率
	 * @param lawards
	 * @return
	 */
	private Double getLeftRateByRecord(List<Double> totalRate){
		double awardTotalRate = 0d;
		//需要根据会员充值等级加载不同的中奖概率
		for (Double lv : totalRate) {
			// 奖品概率
			double probility = lv / 100d;
			if(probility <0) probility = 0d;
			awardTotalRate += probility;
		}
		return 1-awardTotalRate > 0 ? 1-awardTotalRate : 0;
	}

	/* (non-Javadoc)
	 * @see com.dbumama.market.service.api.lottery.LotteryService#test(java.lang.Long)
	 */
	@Override
	public List<LotteryTestResultDto> test(Long lotteryId, Integer testCount) throws LotteryServiceException {
		if(testCount == null || testCount<10 || testCount > 50000)
			throw new LotteryServiceException("模拟次数在10到50000次之间");
		
		List<Record> lawards = Db
				.find("select a.*, p.publish_count, t.type_name from " + LotteryAward.table + " a " 
						+ " left join " + Prize.table + " p on a.prize_id=p.id "
						+ " left join " + PrizeType.table + " t on p.prize_type_id = t.id "
						+ " where lottery_id=? ", lotteryId);
		
		if(lawards == null || lawards.size()<=0)
			throw new LotteryServiceException("没有设置奖品");
		
		List<Double> totalRates = new ArrayList<Double>();
		for(Record r : lawards){
			totalRates.add(r.getDouble("award_rate"));
		}
		
		//概率
		List<Double> sortOrignalRates = getSortOrignalRatesByRecord(totalRates);
		Map<Integer, Integer> count = new HashMap<Integer, Integer>();		
		for(int i=0;i<testCount;i++){
			// 取出抽奖物品
			int index = lottery(sortOrignalRates);
			Integer value = count.get(index);
			count.put(index, value == null ? 1 : value + 1);
		}
		
		List<LotteryTestResultDto> ltResultList = new ArrayList<LotteryTestResultDto>();
		for (Entry<Integer, Integer> entry : count.entrySet()) {
			LotteryTestResultDto testRdto = new LotteryTestResultDto();
	        if(entry.getKey()>=0 && entry.getKey()<lawards.size()){
	        	testRdto.setAwardName(lawards.get(entry.getKey()).getStr("award_name"));
	        	testRdto.setAwardRate(lawards.get(entry.getKey()).getDouble("award_rate").toString());
	        	testRdto.setAwardType(lawards.get(entry.getKey()).getStr("type_name"));
	        	testRdto.setPrizeCount(lawards.get(entry.getKey()).getInt("publish_count"));
	        	testRdto.setLuckCount(entry.getValue());
	        }else{
	        	testRdto.setAwardName("感谢参与");
	        	testRdto.setAwardType("未中奖");
	        	testRdto.setPrizeCount(1000000);
	        	testRdto.setAwardRate(String.valueOf((getLeftRateByRecord(totalRates)*100)));
	        	testRdto.setLuckCount(entry.getValue());
	        }
	        ltResultList.add(testRdto);
	    }
		Collections.sort(ltResultList, new Comparator<LotteryTestResultDto>() {
            public int compare(LotteryTestResultDto o1, LotteryTestResultDto o2) {
                return o2.getLuckCount() - o1.getLuckCount();
            }
	    });
		return ltResultList;
	}
	
}
