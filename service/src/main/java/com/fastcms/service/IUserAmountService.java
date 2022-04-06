package com.fastcms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.common.exception.FastcmsException;
import com.fastcms.entity.UserAmount;

import java.math.BigDecimal;

/**
 * <p>
 * 用户余额 服务类
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2022-03-28
 */
public interface IUserAmountService extends IService<UserAmount> {

    /**
     * 用户提现
     * @param amount
     * @return
     * @throws FastcmsException
     */
    void cashOut(Long userId, BigDecimal amount) throws FastcmsException;

}
