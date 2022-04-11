package com.fastcms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.egzosn.pay.common.bean.TransferOrder;
import com.egzosn.pay.common.bean.TransferType;
import com.fastcms.common.exception.FastcmsException;
import com.fastcms.common.utils.StrUtils;
import com.fastcms.entity.User;
import com.fastcms.entity.UserAmount;
import com.fastcms.entity.UserAmountPayout;
import com.fastcms.entity.UserAmountStatement;
import com.fastcms.mapper.UserAmountPayoutMapper;
import com.fastcms.payment.FastcmsPayServiceManager;
import com.fastcms.payment.PaymentPlatform;
import com.fastcms.payment.PaymentPlatformService;
import com.fastcms.service.IUserAmountPayoutService;
import com.fastcms.service.IUserAmountService;
import com.fastcms.service.IUserAmountStatementService;
import com.fastcms.service.IUserService;
import com.fastcms.utils.ApplicationUtils;
import com.fastcms.utils.UserAmountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 提现
 * @author wjun_java@163.com
 * @since 2022-04-09
 */
@Service
public class UserAmountPayoutServiceImpl extends ServiceImpl<UserAmountPayoutMapper, UserAmountPayout> implements IUserAmountPayoutService {

	@Autowired
	private IUserAmountStatementService userAmountStatementService;

	@Autowired
	private IUserAmountService userAmountService;

	@Autowired
	private PaymentPlatformService paymentPlatformService;

	@Autowired
	private IUserService userService;

	final String defaultPlatformName = "wxPay";

	protected volatile Lock amountLock = new ReentrantLock();
	protected volatile Lock payoutLock = new ReentrantLock();

	@Override
	public Page<CashOutListVo> pageCashOut(Page pageParam, QueryWrapper queryWrapper) {
		return getBaseMapper().pageCashOut(pageParam, queryWrapper);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void cashOut(Long userId, BigDecimal amount) throws FastcmsException {
		if (amount == null) {
			throw new FastcmsException("提现金额不能为空");
		} else if (amount.compareTo(BigDecimal.ZERO) <=0) {
			throw new FastcmsException("提现金额必须大于0元");
		} else if (UserAmountUtils.getCashOutAmountDayMaxValue().compareTo(BigDecimal.ZERO) > 1
				&& amount.compareTo(UserAmountUtils.getCashOutAmountDayMaxValue())>1) {
			throw new FastcmsException("提现金额必须小于或等于" + UserAmountUtils.getCashOutAmountDayMaxValue());
		}

		try {
			amountLock.lock();

			if (UserAmountUtils.getCashOutAmountDayMaxTimeValue() > 0) {
				Long todayPayoutCount = getBaseMapper().getUserTodayPayoutCount(userId);
				if (todayPayoutCount.intValue() > UserAmountUtils.getCashOutAmountDayMaxTimeValue()) {
					throw new FastcmsException("单日最多提现" + UserAmountUtils.getCashOutAmountDayMaxTimeValue() + "次");
				}
			}

			UserAmount userAmount = userAmountService.getOne(Wrappers.<UserAmount>lambdaQuery().eq(UserAmount::getUserId, userId));
			if (userAmount == null) {
				throw new FastcmsException("用户余额为空");
			}

			if (userAmount.getAmount().compareTo(amount) <0) {
				throw new FastcmsException("用户余额不足");
			}

			if (UserAmountUtils.getCashOutAmountDayBalanceMaxValue().compareTo(BigDecimal.ZERO) > 1
				&& userAmount.getAmount().compareTo(UserAmountUtils.getCashOutAmountDayBalanceMaxValue()) < 1) {
				//余额满多少金额才允许提现
				throw new FastcmsException("余额满" + UserAmountUtils.getCashOutAmountDayBalanceMaxValue() + "才可以提现");
			}

			UserAmountPayout userAmountPayout = new UserAmountPayout();
			userAmountPayout.setAmount(amount);
			userAmountPayout.setStatus(UserAmountPayout.AMOUNT_STATUS_AUDIT);
			userAmountPayout.setPayTo(userService.getUserOpenId(userAmount.getUserId(), User.SourceType.WX_MINI_PROGRAM.name().toLowerCase()));
			userAmountPayout.setPayType(UserAmountUtils.isEnableAmountCashOutAudit() ? 1 : 0);
			save(userAmountPayout);

			if (!UserAmountUtils.isEnableAmountCashOutAudit()) {
				//无需审核的情况下
				doCashOut(userAmount, userAmountPayout);
			}

		} finally {
			amountLock.unlock();
		}

	}

	@Override
	public void auditCashOut(Long payoutId) throws FastcmsException {
		UserAmountPayout amountPayout = getById(payoutId);
		if (amountPayout == null) throw new FastcmsException("提现审核单不存在");
		else if (amountPayout.getStatus() == UserAmountPayout.AMOUNT_STATUS_PASS) throw new FastcmsException("该提现申请已审核通过");
		else if (amountPayout.getStatus() == UserAmountPayout.AMOUNT_STATUS_REFUSE) throw new FastcmsException("该提现申请已拒绝");

		try {
			payoutLock.lock();

			if (UserAmountUtils.getCashOutAmountDayMaxTimeValue() > 0) {
				Long todayPayoutCount = getBaseMapper().getUserTodayPayoutCount(amountPayout.getUserId());
				if (todayPayoutCount.intValue() > UserAmountUtils.getCashOutAmountDayMaxTimeValue()) {
					throw new FastcmsException("单日最多提现" + UserAmountUtils.getCashOutAmountDayMaxTimeValue() + "次");
				}
			}

			UserAmount userAmount = userAmountService.getOne(Wrappers.<UserAmount>lambdaQuery().eq(UserAmount::getUserId, amountPayout.getUserId()));
			if (userAmount == null) {
				throw new FastcmsException("用户余额为空");
			}

			if (userAmount.getAmount().compareTo(amountPayout.getAmount()) <0) {
				throw new FastcmsException("用户余额不足");
			}

			if (UserAmountUtils.getCashOutAmountDayBalanceMaxValue().compareTo(BigDecimal.ZERO) > 1
					&& userAmount.getAmount().compareTo(UserAmountUtils.getCashOutAmountDayBalanceMaxValue()) < 1) {
				//余额满多少金额才允许提现
				throw new FastcmsException("余额满" + UserAmountUtils.getCashOutAmountDayBalanceMaxValue() + "才可以提现");
			}

			doCashOut(userAmount, amountPayout);
		} finally {
			payoutLock.unlock();
		}
	}

	@Override
	public CashOutDetailVo getCashOutDetail(Long payoutId) {
		CashOutDetailVo cashOutDetail = getBaseMapper().getCashOutDetail(payoutId);
		List<UserAmountStatement> list = userAmountStatementService.list(Wrappers.<UserAmountStatement>lambdaQuery()
				.eq(UserAmountStatement::getUserId, cashOutDetail.getUserId())
				.eq(UserAmountStatement::getAction, UserAmountStatement.AMOUNT_ACTION_ADD)
				.last("limit 0, 15")
				.orderByDesc(UserAmountStatement::getCreated)
		);
		cashOutDetail.setUserAmountStatementList(list);
		return cashOutDetail;
	}

	/**
	 * 做实际转账操作
	 * @param userAmount
	 * @param userAmountPayout
	 * @throws FastcmsException
	 */
	void doCashOut(UserAmount userAmount, UserAmountPayout userAmountPayout) throws FastcmsException {
		PaymentPlatform platform = paymentPlatformService.getPlatform(defaultPlatformName);
		if (platform == null) {
			throw new FastcmsException("未找到第三方支付平台，请安装支付插件");
		}

		final String userOpenId = userService.getUserOpenId(userAmount.getUserId(), User.SourceType.WX_MINI_PROGRAM.name().toLowerCase());
		if (StrUtils.isBlank(userOpenId)) {
			throw new FastcmsException("用户账户为空");
		}

		try {
			TransferOrder transferOrder = new TransferOrder();
			transferOrder.setTransferType(WxTransferType.TRANSFERS);
			transferOrder.setPayeeAccount(userOpenId);
			transferOrder.setAmount(userAmountPayout.getAmount());
			transferOrder.setOutNo(StrUtils.getSnowNo());
			Map<String, Object> transfer = ApplicationUtils.getBean(FastcmsPayServiceManager.class).transfer(defaultPlatformName, transferOrder);

			if (transfer.get("return_code") != null && transfer.get("return_code").equals("SUCCESS")
					&& transfer.get("result_code") != null && transfer.get("result_code").equals("SUCCESS")) {
				//转账成功
				//插入流水变动记录
				UserAmountStatement userAmountStatement = new UserAmountStatement();
				userAmountStatement.setAction(UserAmountStatement.AMOUNT_ACTION_DEL);
				userAmountStatement.setUserId(userAmount.getUserId());
				userAmountStatement.setActionType(UserAmountStatement.AMOUNT_ACTION_TYPE_CASHOUT);
				userAmountStatement.setActionDesc("提现");
				userAmountStatement.setOldAmount(userAmount.getAmount());
				userAmountStatement.setChangeAmount(userAmountPayout.getAmount());
				userAmountStatement.setNewAmount(userAmount.getAmount().subtract(userAmountPayout.getAmount()));
				userAmountStatementService.save(userAmountStatement);

				userAmountPayout.setStatus(UserAmountPayout.AMOUNT_STATUS_PASS);
				userAmountPayout.setStatementId(userAmountStatement.getId());
				saveOrUpdate(userAmountPayout);

				//更改账户余额
				userAmount.setAmount(userAmountStatement.getNewAmount());
				userAmountService.updateById(userAmount);
			} else {
				throw new FastcmsException(transfer.get("err_code") + ",msg:" + transfer.get("err_code_des"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new FastcmsException("调用第三方支付平台的转账接口失败");
		}
	}

	public enum WxTransferType implements TransferType {
		/**
		 * 转账到零钱
		 */
		TRANSFERS("mmpaymkttransfers/promotion/transfers"),
		/**
		 * 查询转账到零钱的记录
		 */
		GETTRANSFERINFO("mmpaymkttransfers/gettransferinfo"),
		/**
		 * 转账到银行卡
		 */
		PAY_BANK("mmpaysptrans/pay_bank"),
		/**
		 * 查询转账到银行卡的记录
		 */
		QUERY_BANK("mmpaysptrans/query_bank"),

		;

		WxTransferType(String method) {
			this.method = method;
		}

		private String method;
		@Override
		public String getType() {
			return this.name();
		}
		@Override
		public String getMethod() {
			return this.method;
		}


		@Override
		public Map<String, Object> setAttr(Map<String, Object> attr, TransferOrder order) {
			return attr;
		}
	}

}
