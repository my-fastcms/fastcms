package com.fastcms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.entity.PaymentRecord;

/**
 * <p>
 * 支付记录表 服务类
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2021-06-19
 */
public interface IPaymentRecordService extends IService<PaymentRecord> {

	PaymentRecord getPaymentRecordByTrxNo(String trxNo);

}
