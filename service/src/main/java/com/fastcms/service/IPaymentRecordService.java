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
	 * 获取商品对应的支付记录
	 * @param productId
	 * @return
	 */
	List<PaymentRecord> getPaymentRecordByProductId(Long productId);

	/**
	 * 获取用户商品支付记录
	 * @param productId
	 * @param userId
	 * @return
	 */
	List<PaymentRecord> getUserProductPaymentRecord(Long productId, Long userId);

	/**
	 * 检查文章是否需要支付
	 * @param articleId
	 * @param userId
	 * @return
	 */
	boolean checkNeedPay(Long articleId, Long userId);

}
