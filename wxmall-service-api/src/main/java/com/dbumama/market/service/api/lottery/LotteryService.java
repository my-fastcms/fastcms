package com.dbumama.market.service.api.lottery;

import java.util.List;
import java.util.Map;

import com.dbumama.market.model.Lottery;
import com.dbumama.market.model.SellerUser;
import com.jfinal.plugin.activerecord.Page;

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
	
	
}
