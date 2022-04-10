package com.fastcms.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fastcms.entity.PaymentRecord;
import com.fastcms.service.IPaymentRecordService;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 支付记录表 Mapper 接口
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2021-06-19
 */
public interface PaymentRecordMapper extends BaseMapper<PaymentRecord> {

    Page<IPaymentRecordService.PaymentListVo> pagePaymentRecord(Page pageParam, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);

    IPaymentRecordService.PaymentDetailVo getPaymentDetail(@Param("paymentId") Long paymentId);

}
