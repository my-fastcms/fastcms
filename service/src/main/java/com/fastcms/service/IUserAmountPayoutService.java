package com.fastcms.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.common.exception.FastcmsException;
import com.fastcms.entity.UserAmountPayout;
import com.fastcms.entity.UserAmountStatement;

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

		public String getPayTypeStr() {
			return UserAmountPayout.PayOutType.getValue(getPayType());
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

}
