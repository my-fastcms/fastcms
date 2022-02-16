package com.fastcms.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fastcms.entity.PaymentRecord;
import com.fastcms.mapper.PaymentRecordMapper;
import com.fastcms.service.IPaymentRecordService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 支付记录服务实现类
 * @author wjun_java@163.com
 * @since 2021-06-19
 */
@Service
public class PaymentRecordServiceImpl extends ServiceImpl<PaymentRecordMapper, PaymentRecord> implements IPaymentRecordService {

	@Override
	public PaymentRecord getPaymentRecordByTrxNo(String trxNo) {
		return getOne(Wrappers.<PaymentRecord>lambdaQuery().eq(PaymentRecord::getTrxNo, trxNo));
	}

	@Override
	public List<PaymentRecord> getPaymentRecordByProductId(Long productId) {
		return list(Wrappers.<PaymentRecord>lambdaQuery().eq(PaymentRecord::getProductRelativeId, productId));
	}

	@Override
	public boolean checkNeedPay(Long articleId) {
		List<PaymentRecord> paymentRecordList = getPaymentRecordByProductId(articleId);

		for (PaymentRecord paymentRecord : paymentRecordList) {
			//有成功支付的订单，无需再支付购买
			if (paymentRecord != null && paymentRecord.getPayStatus() == 0) {
				return false;
			}
		}

		return true;
	}

}
