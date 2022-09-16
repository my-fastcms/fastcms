package com.fastcms.service;

import com.baomidou.mybatisplus.extension.service.IService;
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
     * 更新用户账户余额
     * @param userId
     * @param action
     * @param changeAmount
     * @param orderId
     * @param desc
     */
    void updateUserAmount(Long userId, String action, BigDecimal changeAmount, Long orderId, String desc);

    /**
     * 增加用户余额
     * @param userId
     * @param changeAmount
     * @param orderId
     * @param desc
     */
    void addUserAmount(Long userId, BigDecimal changeAmount, Long orderId, String desc);

    /**
     * 减少用户余额
     * @param userId
     * @param changeAmount
     * @param orderId
     * @param desc
     */
    void delUserAmount(Long userId, BigDecimal changeAmount, Long orderId, String desc);

}
