package com.fastcms.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.entity.Order;
import com.fastcms.entity.PaymentRecord;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 支付记录 服务类
 * @author wjun_java@163.com
 * @since 2021-06-19
 */
public interface IPaymentRecordService extends IService<PaymentRecord> {

	/**
	 * 获取商品对应的支付记录
	 * @param productId
	 * @return
	 */
	List<PaymentRecord> getPaymentRecordByProductId(Long productId);

	/**
	 * 获取用户商品支付记录
	 * @param productId
	 * @param userId
	 * @return
	 */
	List<PaymentRecord> getUserProductPaymentRecord(Long productId, Long userId);

	/**
	 * 检查文章是否需要支付
	 * @param articleId
	 * @param userId
	 * @return
	 */
	boolean checkNeedPay(Long articleId, Long userId);

	/**
	 * 支付记录分页列表
	 * @param pageParam
	 * @param queryWrapper
	 * @return
	 */
	Page<PaymentListVo> pagePaymentRecord(Page pageParam, QueryWrapper queryWrapper);

	/**
	 * 支付记录详情
	 * @param paymentId
	 * @return
	 */
	IPaymentRecordService.PaymentDetailVo getPaymentDetail(@Param("paymentId") Long paymentId);

	/**
	 * 支付记录
	 */
	class PaymentListVo implements Serializable {

		/**
		 * 支付记录id
		 */
		private Long id;

		/**
		 * 支付流水号
		 */
		private String trxNo;

		/**
		 * 支付金额
		 */
		private BigDecimal payAmount;

		/**
		 * 关联产品
		 */
		private String prodTitle;

		/**
		 * 支付用户
		 */
		private String userName;

		/**
		 * 支付用户
		 */
		private String nickName;

		/**
		 * 支付类型
		 */
		private String payType;

		/**
		 * 支付时间
		 */
		private LocalDateTime created;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getTrxNo() {
			return trxNo;
		}

		public void setTrxNo(String trxNo) {
			this.trxNo = trxNo;
		}

		public BigDecimal getPayAmount() {
			return payAmount == null ? BigDecimal.ZERO : payAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
		}

		public void setPayAmount(BigDecimal payAmount) {
			this.payAmount = payAmount;
		}

		public String getProdTitle() {
			return prodTitle;
		}

		public void setProdTitle(String prodTitle) {
			this.prodTitle = prodTitle;
		}

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

		public String getPayType() {
			return payType;
		}

		public void setPayType(String payType) {
			this.payType = payType;
		}

		public LocalDateTime getCreated() {
			return created;
		}

		public void setCreated(LocalDateTime created) {
			this.created = created;
		}

	}

	/**
	 * 支付记录详情
	 */
	class PaymentDetailVo extends PaymentListVo implements Serializable {
		/**
		 * 支付状态：0生成订单未支付（预支付）、1支付成功、 2支付失败
		 */
		private Integer payStatus;

		/**
		 * 支付银行类型
		 */
		private String payBankType;

		/**
		 * 订单金额
		 */
		private BigDecimal payAmount;

		/**
		 * 成功支付金额
		 */
		private BigDecimal paySuccessAmount;

		/**
		 * 支付成功时间
		 */
		private LocalDateTime paySuccessTime;

		/**
		 * 第三方支付平台
		 */
		private String thirdpartyType;

		/**
		 * 微信appid 或者 支付宝的appid，thirdparty 指的是支付的第三方比如微信、支付宝、PayPal等
		 */
		private String thirdpartyAppid;

		/**
		 * 商户号
		 */
		private String thirdpartyMchId;

		/**
		 * 交易类型
		 */
		private String thirdpartyTradeType;

		/**
		 * 第三方交易流水号
		 */
		private String thirdpartyTransactionId;

		/**
		 * 第三方交易用户openid
		 */
		private String thirdpartyUserOpenid;

		/**
		 * 备注
		 */
		private String remark;

		public Integer getPayStatus() {
			return payStatus;
		}

		public void setPayStatus(Integer payStatus) {
			this.payStatus = payStatus;
		}

		public String getPayBankType() {
			return payBankType;
		}

		public void setPayBankType(String payBankType) {
			this.payBankType = payBankType;
		}

		@Override
		public BigDecimal getPayAmount() {
			return payAmount;
		}

		@Override
		public void setPayAmount(BigDecimal payAmount) {
			this.payAmount = payAmount;
		}

		public BigDecimal getPaySuccessAmount() {
			return paySuccessAmount;
		}

		public void setPaySuccessAmount(BigDecimal paySuccessAmount) {
			this.paySuccessAmount = paySuccessAmount;
		}

		public LocalDateTime getPaySuccessTime() {
			return paySuccessTime;
		}

		public void setPaySuccessTime(LocalDateTime paySuccessTime) {
			this.paySuccessTime = paySuccessTime;
		}

		public String getThirdpartyType() {
			return thirdpartyType;
		}

		public void setThirdpartyType(String thirdpartyType) {
			this.thirdpartyType = thirdpartyType;
		}

		public String getThirdpartyAppid() {
			return thirdpartyAppid;
		}

		public void setThirdpartyAppid(String thirdpartyAppid) {
			this.thirdpartyAppid = thirdpartyAppid;
		}

		public String getThirdpartyMchId() {
			return thirdpartyMchId;
		}

		public void setThirdpartyMchId(String thirdpartyMchId) {
			this.thirdpartyMchId = thirdpartyMchId;
		}

		public String getThirdpartyTradeType() {
			return thirdpartyTradeType;
		}

		public void setThirdpartyTradeType(String thirdpartyTradeType) {
			this.thirdpartyTradeType = thirdpartyTradeType;
		}

		public String getThirdpartyTransactionId() {
			return thirdpartyTransactionId;
		}

		public void setThirdpartyTransactionId(String thirdpartyTransactionId) {
			this.thirdpartyTransactionId = thirdpartyTransactionId;
		}

		public String getThirdpartyUserOpenid() {
			return thirdpartyUserOpenid;
		}

		public void setThirdpartyUserOpenid(String thirdpartyUserOpenid) {
			this.thirdpartyUserOpenid = thirdpartyUserOpenid;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public String getPayStatusStr() {
			return Order.PayStatus.getValue(getPayStatus());
		}

	}

}
