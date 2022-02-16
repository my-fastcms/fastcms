package com.fastcms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.entity.PaymentRecord;

import java.util.List;

/**
 * 支付记录 服务类
 * @author wjun_java@163.com
 * @since 2021-06-19
 */
public interface IPaymentRecordService extends IService<PaymentRecord> {

	/**
	 * 根据订单流水号获取支付记录
	 * @param trxNo
	 * @return
	 */
	PaymentRecord getPaymentRecordByTrxNo(String trxNo);

	/**
	 * 获取商品对应的支付记录
	 * @param productId
	 * @return
	 */
	List<PaymentRecord> getPaymentRecordByProductId(Long productId);

	/**
	 * 检查文章是否需要支付
	 * @param articleId
	 * @return
	 */
	boolean checkNeedPay(Long articleId);

}
