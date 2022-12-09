package com.fastcms.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.common.exception.FastcmsException;
import com.fastcms.entity.UserAmountPayout;
import com.fastcms.entity.UserAmountStatement;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 提现申请表 服务类
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2022-04-09
 */
public interface IUserAmountPayoutService extends IService<UserAmountPayout> {

	/**
	 * 提现记录分页列表
	 * @param pageParam
	 * @param queryWrapper
	 * @return
	 */
	Page<CashOutListVo> pageCashOut(Page pageParam, QueryWrapper queryWrapper);

	/**
	 * 用户提现
	 * @param amount
	 * @return
	 * @throws FastcmsException
	 */
	String cashOut(Long userId, BigDecimal amount) throws FastcmsException;

	/**
	 * 审核提现单
	 * @param payoutId
	 * @throws FastcmsException
	 */
	void auditCashOut(Long payoutId) throws FastcmsException;

	/**
	 * 获取提现申请单详情
	 * @param payoutId
	 * @return
	 */
	CashOutDetailVo getCashOutDetail(Long payoutId);

	/**
	 * 获取用户提现列表
	 * @param queryWrapper
	 * @return
	 */
	List<IUserAmountPayoutService.CashOutListVo> getUserCashOutList(@Param(Constants.WRAPPER) QueryWrapper queryWrapper);

	/**
	 * 用户提现记录
	 */
	class CashOutListVo implements Serializable {

		/**
		 * id
		 */
		private Long id;

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
		 * 提现类型
		 */
		private Integer payType;

		/**
		 * 审核类型
		 */
		private Integer auditType;

		/**
		 * 支付账号
		 */
		private String payTo;

		/**
		 * 状态
		 */
		private Integer status;

		/**
		 * 提现时间
		 */
		private LocalDateTime created;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
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

		public Integer getPayType() {
			return payType;
		}

		public void setPayType(Integer payType) {
			this.payType = payType;
		}

		public Integer getAuditType() {
			return auditType;
		}

		public void setAuditType(Integer auditType) {
			this.auditType = auditType;
		}

		public String getPayTo() {
			return payTo;
		}

		public void setPayTo(String payTo) {
			this.payTo = payTo;
		}

		public Integer getStatus() {
			return status;
		}

		public void setStatus(Integer status) {
			this.status = status;
		}

		public LocalDateTime getCreated() {
			return created;
		}

		public void setCreated(LocalDateTime created) {
			this.created = created;
		}

		public String getStatusStr() {
			return UserAmountPayout.PayOutStatus.getValue(getStatus());
		}

		public String getAuditTypeStr() {
			return UserAmountPayout.AuditType.getValue(getAuditType());
		}

	}

	/**
	 * 提现单详情
	 */
	class CashOutDetailVo extends CashOutListVo implements Serializable {

		/**
		 * 提现用户
		 */
		private Long userId;

		/**
		 * 拒绝理由
		 */
		private String feedback;

		/**
		 * 用户收入流水
		 */
		List<UserAmountStatement> userAmountStatementList;

		public List<UserAmountStatement> getUserAmountStatementList() {
			return userAmountStatementList;
		}

		public void setUserAmountStatementList(List<UserAmountStatement> userAmountStatementList) {
			this.userAmountStatementList = userAmountStatementList;
		}

		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

		public String getFeedback() {
			return feedback;
		}

		public void setFeedback(String feedback) {
			this.feedback = feedback;
		}
	}

	interface UserAmountI18n {
		String USER_AMOUNT_PAYOUT_MONEY_NOT_NULL = "fastcms.user.amount.payout.money.not.null";
		String USER_AMOUNT_CASHOUT_MONEY_GREATER_THAN_0 = "fastcms.user.amount.cashout.money.greater.than.0";
		String USER_AMOUNT_CASHOUT_MONEY_LESS_THAN = "fastcms.user.amount.cashout.money.less.than";
		String USER_AMOUNT_CASHOUT_DAY_COUNT = "fastcms.user.amount.cashout.day.count";
		String USER_AMOUNT_CASHOUT_IS_NULL = "fastcms.user.amount.is.null";
		String USER_AMOUNT_IS_NOT_ENOUGH = "fastcms.user.amount.is.not.enough";
		String USER_AMOUNT_CASHOUT_MONEY_LIMIT = "fastcms.user.amount.cashout.money.limit";
		String USER_AMOUNT_MONEY_APPROVAL_IS_NOT = "fastcms.user.amount.money.approval.is.not";
		String USER_AMOUNT_MONEY_APPROVAL_IS_ING = "fastcms.user.amount.money.approval.is.approvaling";
		String USER_AMOUNT_CASHOUT_SUCCESS = "fastcms.user.amount.cashout.success";
		String USER_AMOUNT_CASHOUT_VERIFY_NOT_EXIST = "fastcms.user.amount.cashout.verify.not.exist";
		String USER_AMOUNT_CASHOUT_VERIFY_IS_PASS = "fastcms.user.amount.cashout.verify.is.pass";
		String USER_AMOUNT_CASHOUT_VERIFY_IS_NOT_PASS = "fastcms.user.amount.cashout.verify.is.not.pass";
		String USER_AMOUNT_PAYMENT_PLATFORM_NOT_EXIST = "fastcms.user.amount.payment.platform.not.exist";
		String USER_AMOUNT_TRANSFER_SERVICE_CLASS_NOT_EXIST = "fastcms.user.amount.transfer.service.class.not.exist";
		String USER_AMOUNT_TRANSFER_THIRD_FAIL = "fastcms.user.amount.transfer.third.fail";
		String USER_AMOUNT_CASHOUT = "fastcms.user.amount.cashout";
		String USER_AMOUNT_OPENID_EMPTY = "fastcms.user.amount.openid.empty";
	}

}
