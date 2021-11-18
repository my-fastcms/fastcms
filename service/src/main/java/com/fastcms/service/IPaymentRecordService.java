package com.fastcms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.entity.PaymentRecord;

/**
 * 支付记录 服务类
 * @author wjun_java@163.com
 * @since 2021-06-19
 */
public interface IPaymentRecordService extends IService<PaymentRecord> {

	PaymentRecord getPaymentRecordByTrxNo(String trxNo);

}
