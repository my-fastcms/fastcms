package com.fastcms.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fastcms.entity.Order;
import com.fastcms.entity.User;
import com.fastcms.entity.UserAmount;
import com.fastcms.entity.UserAmountStatement;
import com.fastcms.mapper.UserAmountMapper;
import com.fastcms.service.IOrderService;
import com.fastcms.service.IUserAmountService;
import com.fastcms.service.IUserAmountStatementService;
import com.fastcms.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    Logger logger = LoggerFactory.getLogger(UserAmountServiceImpl.class);

    @Autowired
    private IUserAmountStatementService userAmountStatementService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IOrderService orderService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserAmount(Long userId, String action, BigDecimal changeAmount, Long orderId, String desc) {
        if (changeAmount == null || changeAmount.compareTo(BigDecimal.ZERO) <=0) return;

        User user = userService.getById(userId);
        if (user == null) {
            return;
        }

        Order order = orderService.getById(orderId);
        if (order == null) {
            return;
        }

        if (order.getPayStatus() != Order.STATUS_PAY_SUCCESS) {
            return;
        }

        if (order.getStatus() != Order.ORDER_STATUS_NORMAL) {
            return;
        }

        UserAmount userAmount;
        try {
            userAmount = getOne(Wrappers.<UserAmount>lambdaQuery().eq(UserAmount::getCreateUserId, userId));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return;
        }

        if (userAmount == null) {
            userAmount = new UserAmount();
            userAmount.setCreateUserId(userId);
            userAmount.setCreated(LocalDateTime.now());
            userAmount.setAmount(BigDecimal.ZERO);
            userAmount.setVersion(0);
            save(userAmount);
        }

        BigDecimal oldAmount = userAmount.getAmount();

        BigDecimal newAmount;
        if (UserAmountStatement.AMOUNT_ACTION_ADD.equalsIgnoreCase(action)) {
            newAmount = oldAmount.add(changeAmount);
        } else if (UserAmountStatement.AMOUNT_ACTION_DEL.equalsIgnoreCase(action)) {
            if (oldAmount.compareTo(changeAmount) <0) {
                logger.error("用户余额不足");
                return;
            }
            newAmount = oldAmount.subtract(changeAmount);
        } else {
            return;
        }

        UserAmountStatement userAmountStatement = new UserAmountStatement();
        userAmountStatement.setCreateUserId(userId);
        userAmountStatement.setAction(action);
        userAmountStatement.setOldAmount(oldAmount);
        userAmountStatement.setNewAmount(newAmount);
        userAmountStatement.setChangeAmount(changeAmount);
        userAmountStatement.setActionOrderId(orderId);
        userAmountStatement.setActionDesc(desc);
        userAmountStatement.setCreated(LocalDateTime.now());

        userAmountStatementService.save(userAmountStatement);

        userAmount.setAmount(newAmount);
        updateById(userAmount);
    }

    @Override
    public void addUserAmount(Long userId, BigDecimal changeAmount, Long orderId, String desc) {
        updateUserAmount(userId, UserAmountStatement.AMOUNT_ACTION_ADD, changeAmount, orderId, desc);
    }

    @Override
    public void delUserAmount(Long userId, BigDecimal changeAmount, Long orderId, String desc) {
        updateUserAmount(userId, UserAmountStatement.AMOUNT_ACTION_DEL, changeAmount, orderId, desc);
    }

    @Override
    public UserAmountVo getUserAmount() {
        return getBaseMapper().getUserAmount();
    }

}
