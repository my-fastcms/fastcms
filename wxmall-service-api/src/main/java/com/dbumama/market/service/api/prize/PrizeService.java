package com.dbumama.market.service.api.prize;

import java.util.List;

import com.dbumama.market.model.Prize;
import com.dbumama.market.model.PrizeType;
import com.dbumama.market.service.api.exception.MarketBaseException;
import com.jfinal.plugin.activerecord.Page;

/**
 * wjun_java@163.com
 * 2016年7月20日
 */
public interface PrizeService {

	/**
	 * 给买家发放奖品
	 * @param prizeSendParamDto
	 * @throws MarketBaseException
	 */
	public PrizeSendResultDto sendPrize(PrizeSendParamDto prizeSendParamDto) throws MarketBaseException;
	
	Prize findById(Long prizeId);
	
	List<Prize> getVaildPrizes(Long sellerId);
	
	Page<PrizeResultDto> list(Long sellerId, Integer pageNo, Integer pageSize, Integer type, String keyword);
	
	Prize save(Prize prize) throws MarketBaseException;
	
	List<PrizeType> getPrizeTypes();
}
