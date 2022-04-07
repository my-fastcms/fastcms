package com.fastcms.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.entity.PaymentRecord;

import java.io.Serializable;
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
	 * 支付记录
	 */
	class PaymentListVo implements Serializable {

		/**
		 * 支付流水号
		 */
		private String trxNo;

		/**
		 * 支付金额
		 */
		private String payAmount;

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

		public String getTrxNo() {
			return trxNo;
		}

		public void setTrxNo(String trxNo) {
			this.trxNo = trxNo;
		}

		public String getPayAmount() {
			return payAmount;
		}

		public void setPayAmount(String payAmount) {
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

	}

}
