package com.fastcms.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fastcms.common.exception.FastcmsException;
import com.fastcms.entity.UserAmount;
import com.fastcms.entity.UserAmountStatement;
import com.fastcms.mapper.UserAmountMapper;
import com.fastcms.service.IUserAmountService;
import com.fastcms.service.IUserAmountStatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * <p>
 * 用户余额 服务实现类
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2022-03-28
 */
@Service
public class UserAmountServiceImpl extends ServiceImpl<UserAmountMapper, UserAmount> implements IUserAmountService {

    @Autowired
    private IUserAmountStatementService userAmountStatementService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cashOut(Long userId, BigDecimal amount) throws FastcmsException {
        if (amount == null) {
            throw new FastcmsException("提现金额不能为空");
        } else if (amount.compareTo(BigDecimal.ZERO) <=0) {
            throw new FastcmsException("提现金额必须大于0元");
        } else if (amount.compareTo(new BigDecimal(1000))>1) {
            throw new FastcmsException("提现金额必须小于或等于1000");
        }

        UserAmount userAmount = getOne(Wrappers.<UserAmount>lambdaQuery().eq(UserAmount::getUserId, userId));
        if (userAmount == null) {
            throw new FastcmsException("用户余额为空");
        }

        if (userAmount.getAmount().compareTo(amount) <0) {
            throw new FastcmsException("用户余额不足");
        }

        UserAmountStatement userAmountStatement = new UserAmountStatement();
        userAmountStatement.setAction(UserAmountStatement.AMOUNT_ACTION_DEL);
        userAmountStatement.setUserId(userId);
        userAmountStatement.setActionName(UserAmountStatement.AmountAction.getValue(userAmountStatement.getAction()));
        userAmountStatement.setActionDesc("提现");
        userAmountStatement.setOldAmount(userAmount.getAmount());
        userAmountStatement.setChangeAmount(amount);
        userAmountStatement.setNewAmount(userAmount.getAmount().subtract(amount));
        userAmountStatementService.save(userAmountStatement);

        userAmount.setAmount(userAmountStatement.getNewAmount());
        updateById(userAmount);

    }

}
