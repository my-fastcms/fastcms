package com.dbumama.market.service.api.receiver;

import java.util.List;

import com.dbumama.market.model.BuyerReceiver;

/**
 * 买家收货地址
 * @author wangjun
 *
 */
public interface ReceiverService {

	/**
	 * 获取买家收货地址
	 * @param buyerId
	 * @return
	 */
	List<BuyerReceiver> getBuyerReceiver(Long buyerId);
	
	/**
	 * 根据Id获取收货地址
	 * @param receiverId
	 * @return
	 */
	BuyerReceiver findById(Long receiverId);
	
	/**
	 * 保存收货地址
	 * @param submitParam
	 * @throws ReceiverException
	 */
	BuyerReceiver save(BuyerReceiverSubmitParamDto submitParam) throws ReceiverException;
	
	/**
	 * 获取用户默认收获地址
	 * @param buyerId
	 * @return
	 */
	BuyerReceiver getDefaultReceiver(Long buyerId);
}
