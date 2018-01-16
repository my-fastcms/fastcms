package com.dbumama.market.service.api.lottery;

import java.util.List;
import java.util.Map;

import com.dbumama.market.model.Lottery;
import com.dbumama.market.model.LotteryAward;
import com.dbumama.market.model.LotteryRecord;
import com.dbumama.market.model.LotteryTrade;
import com.dbumama.market.model.SellerUser;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * wjun_java@163.com
 * 2016年6月27日
 */
public interface LotteryService {

	public Lottery save(Lottery lottery, SellerUser seller, String condition) throws LotteryServiceException;
	
	public Lottery update(Lottery lottery, SellerUser seller, String condition) throws LotteryServiceException;
	
	public Page<LotteryResultDto> list (int pageNo, int pageSize, Map<String, String> paramsMap) throws LotteryServiceException;
	
	/**
	 * 手机端九宫格抽奖
	 * @param lotteryId
	 * @param sellerId
	 * @param buyerId
	 * @return
	 * @throws LotteryServiceException
	 */
	public DrawResultDto lottery (Long lotteryId, Long sellerId, Long buyerId) throws LotteryServiceException;
	
	/**
	 * 根据奖品概率模拟用户抽奖情况
	 * @param lotteryId
	 * @return
	 * @throws LotteryServiceException
	 */
	public List<LotteryTestResultDto> test(Long lotteryId, Integer testCount) throws LotteryServiceException;
	
	Lottery findById(Long id);
	
	LotteryAward findAwardById(Long id);
	
	List<Lottery> getVaildLotterys(Long sellerId);
	
	List<Record> listLottAwards(Long lotteryId);
	
	LotteryAward saveAward(LotteryAward award);
	
	void deleteAwardById(Long id);
	
	List<LotteryAward> initView(Long lotteryId);
	
	List<LotteryRecord> getLotteryRecords(Long lotteryId, Long buyerId);
	
	LotteryTrade getLotteryTrade(Long lotteryId);
	
}
