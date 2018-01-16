package com.dbumama.market.service.enmu;

public enum PaymentStatus {
	/** 未支付 */
	unpaid,

	/** 部分支付 */
	partialPayment,

	/** 已支付 */
	paid,

	/** 部分退款 */
	partialRefunds,

	/** 已退款 */
	refunded
}
