package com.fastcms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fastcms.entity.PaymentRecord;
import com.fastcms.mapper.PaymentRecordMapper;
import com.fastcms.service.IPaymentRecordService;
import org.springframework.stereotype.Service;

/**
 * 支付记录服务实现类
 * @author wjun_java@163.com
 * @since 2021-06-19
 */
@Service
public class PaymentRecordServiceImpl extends ServiceImpl<PaymentRecordMapper, PaymentRecord> implements IPaymentRecordService {

	@Override
	public PaymentRecord getPaymentRecordByTrxNo(String trxNo) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("trx_no", trxNo);
		return getOne(queryWrapper);
	}
}
