package com.fastcms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fastcms.common.exception.FastcmsException;
import com.fastcms.common.exception.I18nFastcmsException;
import com.fastcms.common.utils.StrUtils;
import com.fastcms.entity.UserAmount;
import com.fastcms.entity.UserAmountPayout;
import com.fastcms.entity.UserAmountStatement;
import com.fastcms.entity.UserOpenid;
import com.fastcms.extension.FastcmsTransferService;
import com.fastcms.extension.FastcmsTransferServiceManager;
import com.fastcms.mapper.UserAmountPayoutMapper;
import com.fastcms.payment.PaymentPlatform;
import com.fastcms.payment.PaymentPlatformService;
import com.fastcms.service.IUserAmountPayoutService;
import com.fastcms.service.IUserAmountService;
import com.fastcms.service.IUserAmountStatementService;
import com.fastcms.service.IUserService;
import com.fastcms.utils.I18nUtils;
import com.fastcms.utils.UserAmountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.fastcms.service.IUserAmountPayoutService.UserAmountI18n.*;

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

	@Autowired
	private FastcmsTransferServiceManager fastcmsTransferServiceManager;

	final String defaultPlatformName = "wxPay";

	protected volatile Lock amountLock = new ReentrantLock();
	protected volatile Lock payoutLock = new ReentrantLock();

	@Override
	public Page<CashOutListVo> pageCashOut(Page pageParam, QueryWrapper queryWrapper) {
		return getBaseMapper().pageCashOut(pageParam, queryWrapper);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String cashOut(Long userId, BigDecimal amount) throws FastcmsException {
		if (amount == null) {
			throw new I18nFastcmsException(USER_AMOUNT_PAYOUT_MONEY_NOT_NULL);
		} else if (amount.compareTo(BigDecimal.ZERO) <=0) {
			throw new I18nFastcmsException(USER_AMOUNT_CASHOUT_MONEY_GREATER_THAN_0);
		} else if (UserAmountUtils.getCashOutAmountDayMaxValue().compareTo(BigDecimal.ZERO) == 1
				&& amount.compareTo(UserAmountUtils.getCashOutAmountDayMaxValue()) == 1) {
			throw new I18nFastcmsException(USER_AMOUNT_CASHOUT_MONEY_LESS_THAN, UserAmountUtils.getCashOutAmountDayMaxValue().toString());
		}

		try {
			amountLock.lock();

			if (UserAmountUtils.getCashOutAmountDayMaxTimeValue() > 0) {
				Long todayPayoutCount = getBaseMapper().getUserTodayPayoutCount(userId);
				if (todayPayoutCount.intValue() > UserAmountUtils.getCashOutAmountDayMaxTimeValue()) {
					throw new I18nFastcmsException(USER_AMOUNT_CASHOUT_DAY_COUNT, UserAmountUtils.getCashOutAmountDayMaxTimeValue().toString());
				}
			}

			UserAmount userAmount = userAmountService.getOne(Wrappers.<UserAmount>lambdaQuery().eq(UserAmount::getCreateUserId, userId));
			if (userAmount == null) {
				throw new I18nFastcmsException(USER_AMOUNT_CASHOUT_IS_NULL);
			}

			if (userAmount.getAmount().compareTo(amount) < 0) {
				throw new I18nFastcmsException(USER_AMOUNT_IS_NOT_ENOUGH);
			}

			if (UserAmountUtils.getCashOutAmountDayBalanceMaxValue().compareTo(BigDecimal.ZERO) == 1
				&& userAmount.getAmount().compareTo(UserAmountUtils.getCashOutAmountDayBalanceMaxValue()) < 1) {
				//余额满多少金额才允许提现
				throw new I18nFastcmsException(USER_AMOUNT_CASHOUT_MONEY_LIMIT, UserAmountUtils.getCashOutAmountDayBalanceMaxValue().toString());
			}

			//获取用户未审核提现金额之和
			BigDecimal userUnAuditAmountPayout = getBaseMapper().getUserUnAuditAmountPayout(userId);
			//提现金额 + 待审核金额不能超过用户当前余额
			if (userUnAuditAmountPayout.add(amount).compareTo(userAmount.getAmount()) == 1) {
				throw new I18nFastcmsException(USER_AMOUNT_MONEY_APPROVAL_IS_NOT, userUnAuditAmountPayout.toString(), userAmount.getAmount().subtract(userUnAuditAmountPayout).toString());
			}

			UserAmountPayout userAmountPayout = new UserAmountPayout();
			userAmountPayout.setAmount(amount);
			userAmountPayout.setStatus(UserAmountPayout.AMOUNT_STATUS_AUDIT);
			userAmountPayout.setPayTo(userService.getUserOpenId(userAmount.getCreateUserId(), UserOpenid.TYPE_WECHAT_MINI));
			userAmountPayout.setAuditType(UserAmountUtils.isEnableAmountCashOutAudit() ? 1 : 0);
			save(userAmountPayout);

			if (UserAmountUtils.isEnableAmountCashOutAudit()) {
				return I18nUtils.getMessage(USER_AMOUNT_MONEY_APPROVAL_IS_ING);
			} else {
				//无需审核的情况下
				doCashOut(userAmount, userAmountPayout);
				return I18nUtils.getMessage(USER_AMOUNT_CASHOUT_SUCCESS);
			}

		} finally {
			amountLock.unlock();
		}

	}

	@Override
	public void auditCashOut(Long payoutId) throws FastcmsException {

		try {
			payoutLock.lock();

			UserAmountPayout amountPayout = getById(payoutId);
			if (amountPayout == null) {
				throw new I18nFastcmsException(USER_AMOUNT_CASHOUT_VERIFY_NOT_EXIST);
			} else if (amountPayout.getStatus() == UserAmountPayout.AMOUNT_STATUS_PASS) {
				throw new I18nFastcmsException(USER_AMOUNT_CASHOUT_VERIFY_IS_PASS);
			} else if (amountPayout.getStatus() == UserAmountPayout.AMOUNT_STATUS_REFUSE) {
				throw new I18nFastcmsException(USER_AMOUNT_CASHOUT_VERIFY_IS_NOT_PASS);
			}

			if (UserAmountUtils.getCashOutAmountDayMaxTimeValue() > 0) {
				Long todayPayoutCount = getBaseMapper().getUserTodayPayoutCount(amountPayout.getCreateUserId());
				if (todayPayoutCount.intValue() > UserAmountUtils.getCashOutAmountDayMaxTimeValue()) {
					throw new I18nFastcmsException(USER_AMOUNT_CASHOUT_DAY_COUNT, UserAmountUtils.getCashOutAmountDayMaxTimeValue().toString());
				}
			}

			UserAmount userAmount = userAmountService.getOne(Wrappers.<UserAmount>lambdaQuery().eq(UserAmount::getCreateUserId, amountPayout.getCreateUserId()));
			if (userAmount == null) {
				throw new I18nFastcmsException(USER_AMOUNT_CASHOUT_IS_NULL);
			}

			if (userAmount.getAmount().compareTo(amountPayout.getAmount()) <0) {
				throw new I18nFastcmsException(USER_AMOUNT_IS_NOT_ENOUGH);
			}

			if (UserAmountUtils.getCashOutAmountDayBalanceMaxValue().compareTo(BigDecimal.ZERO) > 1
					&& userAmount.getAmount().compareTo(UserAmountUtils.getCashOutAmountDayBalanceMaxValue()) < 1) {
				//余额满多少金额才允许提现
				throw new I18nFastcmsException(USER_AMOUNT_CASHOUT_MONEY_LIMIT, UserAmountUtils.getCashOutAmountDayBalanceMaxValue().toString());
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
				.eq(UserAmountStatement::getCreateUserId, cashOutDetail.getUserId())
				.eq(UserAmountStatement::getAction, UserAmountStatement.AMOUNT_ACTION_ADD)
				.last("limit 0, 15")
				.orderByDesc(UserAmountStatement::getCreated)
		);
		cashOutDetail.setUserAmountStatementList(list);
		return cashOutDetail;
	}

	@Override
	public List<CashOutListVo> getUserCashOutList(QueryWrapper queryWrapper) {
		return getBaseMapper().getUserCashOutList(queryWrapper);
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
			throw new I18nFastcmsException(USER_AMOUNT_PAYMENT_PLATFORM_NOT_EXIST);
		}

		final String userOpenId = userService.getUserOpenId(userAmount.getCreateUserId(), UserOpenid.TYPE_WECHAT_MINI);
		if (StrUtils.isBlank(userOpenId)) {
			throw new I18nFastcmsException(USER_AMOUNT_OPENID_EMPTY);
		}

		FastcmsTransferService fastcmsTransferService = fastcmsTransferServiceManager.getFastcmsTransferService(userAmountPayout.getTransferClassName());
		if (fastcmsTransferService == null) {
			throw new I18nFastcmsException(USER_AMOUNT_TRANSFER_SERVICE_CLASS_NOT_EXIST);
		}

		Boolean transferResult = fastcmsTransferService.transfer(userOpenId, userAmountPayout.getAmount(), userAmountPayout.getRemarks());

		if (!transferResult) {
			throw new I18nFastcmsException(USER_AMOUNT_TRANSFER_THIRD_FAIL);
		}

		//转账成功
		//插入流水变动记录
		UserAmountStatement userAmountStatement = new UserAmountStatement();
		userAmountStatement.setAction(UserAmountStatement.AMOUNT_ACTION_DEL);
		userAmountStatement.setCreateUserId(userAmount.getCreateUserId());
		userAmountStatement.setActionType(UserAmountStatement.AMOUNT_ACTION_TYPE_CASHOUT);
		userAmountStatement.setActionDesc(I18nUtils.getMessage(USER_AMOUNT_CASHOUT));
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

	}

}
