package com.fastcms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户余额流水情况
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2022-03-28
 */
@TableName("user_amount_statement")
public class UserAmountStatement implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户
     */
    private Long userId;

    /**
     * 金额变动方向 add, del
     */
    private String action;

    /**
     * 金额变动名称 增加，减少
     */
    private String actionName;

    /**
     * 金额变动描述
     */
    private String actionDesc;

    /**
     * 相关的订单ID
     */
    private Integer actionOrderId;

    /**
     * 相关的支付ID
     */
    private Integer actionPaymentId;

    /**
     * 用户之前的余额
     */
    private BigDecimal oldAmount;

    /**
     * 变动金额
     */
    private BigDecimal changeAmount;

    /**
     * 变动之后的余额
     */
    private BigDecimal newAmount;

    /**
     * 时间
     */
    private LocalDateTime created;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }
    public String getActionDesc() {
        return actionDesc;
    }

    public void setActionDesc(String actionDesc) {
        this.actionDesc = actionDesc;
    }
    public Integer getActionOrderId() {
        return actionOrderId;
    }

    public void setActionOrderId(Integer actionOrderId) {
        this.actionOrderId = actionOrderId;
    }
    public Integer getActionPaymentId() {
        return actionPaymentId;
    }

    public void setActionPaymentId(Integer actionPaymentId) {
        this.actionPaymentId = actionPaymentId;
    }
    public BigDecimal getOldAmount() {
        return oldAmount;
    }

    public void setOldAmount(BigDecimal oldAmount) {
        this.oldAmount = oldAmount;
    }
    public BigDecimal getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(BigDecimal changeAmount) {
        this.changeAmount = changeAmount;
    }
    public BigDecimal getNewAmount() {
        return newAmount;
    }

    public void setNewAmount(BigDecimal newAmount) {
        this.newAmount = newAmount;
    }
    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "UserAmountStatement{" +
            "id=" + id +
            ", userId=" + userId +
            ", action=" + action +
            ", actionName=" + actionName +
            ", actionDesc=" + actionDesc +
            ", actionOrderId=" + actionOrderId +
            ", actionPaymentId=" + actionPaymentId +
            ", oldAmount=" + oldAmount +
            ", changeAmount=" + changeAmount +
            ", newAmount=" + newAmount +
            ", created=" + created +
        "}";
    }
}
