package com.fastcms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.entity.UserAmount;

import java.io.Serializable;
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

    /**
     * 获取用户余额
     * @return
     */
    UserAmountVo getUserAmount();

    class UserAmountVo implements Serializable {

        /**
         * 用户余额
         */
        BigDecimal amount;

        /**
         * 审核中的提现金额
         */
        BigDecimal unAuditAmount;

        /**
         * 用户可提现余额
         * 用户余额 - 用户申请中待审核的余额
         */
        BigDecimal withInAmount;

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        public BigDecimal getUnAuditAmount() {
            return unAuditAmount;
        }

        public void setUnAuditAmount(BigDecimal unAuditAmount) {
            this.unAuditAmount = unAuditAmount;
        }

        public BigDecimal getWithInAmount() {
            return amount.subtract(unAuditAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
        }

        public void setWithInAmount(BigDecimal withInAmount) {
            this.withInAmount = withInAmount;
        }
    }

}
