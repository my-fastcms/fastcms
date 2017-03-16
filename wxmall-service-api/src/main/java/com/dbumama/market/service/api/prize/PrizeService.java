package com.dbumama.market.service.api.prize;

import com.dbumama.market.service.api.exception.MarketBaseException;

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
	
}
