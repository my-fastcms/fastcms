package com.fastcms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fastcms.entity.Order;
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
	public List<PaymentRecord> getPaymentRecordByProductId(Long productId) {
		return list(Wrappers.<PaymentRecord>lambdaQuery().eq(PaymentRecord::getProductRelativeId, productId));
	}

	@Override
	public List<PaymentRecord> getUserProductPaymentRecord(Long productId, Long userId) {
		return list(Wrappers.<PaymentRecord>lambdaQuery().eq(PaymentRecord::getProductRelativeId, productId).eq(PaymentRecord::getCreateUserId, userId));
	}

	@Override
	public boolean checkNeedPay(Long articleId, Long userId) {
		List<PaymentRecord> paymentRecordList = getUserProductPaymentRecord(articleId, userId);

		for (PaymentRecord paymentRecord : paymentRecordList) {
			//有成功支付的订单，无需再支付购买
			if (paymentRecord != null && paymentRecord.getPayStatus() == Order.STATUS_PAY_SUCCESS) {
				return false;
			}
		}

		return true;
	}

	@Override
	public Page<PaymentListVo> pagePaymentRecord(Page pageParam, QueryWrapper queryWrapper) {
		return getBaseMapper().pagePaymentRecord(pageParam, queryWrapper);
	}

	@Override
	public PaymentDetailVo getPaymentDetail(Long paymentId) {
		return getBaseMapper().getPaymentDetail(paymentId);
	}

}
