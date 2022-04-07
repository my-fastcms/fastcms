package com.fastcms.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.common.exception.FastcmsException;
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
     * 用户提现
     * @param amount
     * @return
     * @throws FastcmsException
     */
    void cashOut(Long userId, BigDecimal amount) throws FastcmsException;

    /**
     * 提现记录分页列表
     * @param pageParam
     * @param queryWrapper
     * @return
     */
    Page<CashOutListVo> pageCashOut(Page pageParam, QueryWrapper queryWrapper);

    /**
     * 用户提现记录
     */
    class CashOutListVo implements Serializable {
        /**
         * 提现用户
         */
        private String userName;

        /**
         * 提现用户
         */
        private String nickName;

        /**
         * 真实姓名
         */
        private String realName;

        /**
         * 提现金额
         */
        private BigDecimal amount;

        /**
         * 手续费
         */
        private BigDecimal payFee;

        /**
         * 支付类型
         */
        private String payType;

        /**
         * 支付账号
         */
        private String payAccount;

        /**
         * 状态
         */
        private Integer status;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        public BigDecimal getPayFee() {
            return payFee;
        }

        public void setPayFee(BigDecimal payFee) {
            this.payFee = payFee;
        }

        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        public String getPayAccount() {
            return payAccount;
        }

        public void setPayAccount(String payAccount) {
            this.payAccount = payAccount;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }
    }

}
