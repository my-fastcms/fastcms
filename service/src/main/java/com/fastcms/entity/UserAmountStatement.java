package com.fastcms.entity;

import com.baomidou.mybatisplus.annotation.*;

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

    /**
     * 增加余额
     */
    public static final String AMOUNT_ACTION_ADD = "add";
    /**
     * 减去余额
     */
    public static final String AMOUNT_ACTION_DEL = "del";

    /**
     * 用户提现
     */
    public static final Integer AMOUNT_ACTION_TYPE_CASHOUT = 1;

    /**
     * 余额支付
     */
    public static final Integer AMOUNT_ACTION_TYPE_PAY = 2;

    /**
     * 待审核
     */
    public static final Integer AMOUNT_STATUS_AUDIT = 0;

    /**
     * 审核通过
     */
    public static final Integer AMOUNT_STATUS_PASS = 1;

    /**
     * 审核拒绝
     */
    public static final Integer AMOUNT_STATUS_REFUSE = 2;

    public enum AmountAction {
        ADD(AMOUNT_ACTION_ADD, "增加"),
        DEL(AMOUNT_ACTION_DEL, "减少");

        AmountAction(String key, String value) {
            this.key = key;
            this.value = value;
        }

        private final String key;
        private final String value;

        public static String getValue(String key) {
            for (AmountAction s: values()) {
                if (s.key.equals(key)) {
                    return s.value;
                }
            }
            return "";
        }
    }

    public enum AmountActionType {
        CASHOUT(AMOUNT_ACTION_TYPE_CASHOUT, "用户提现"),
        PAY(AMOUNT_ACTION_TYPE_PAY, "余额支付");

        AmountActionType(Integer key, String value) {
            this.key = key;
            this.value = value;
        }

        private final Integer key;
        private final String value;

        public static String getValue(Integer key) {
            for (AmountActionType s: values()) {
                if (s.key.equals(key)) {
                    return s.value;
                }
            }
            return "";
        }

    }

    public enum ActionStatus {
        AUDIT(AMOUNT_STATUS_AUDIT, "待审核"),
        REFUSE(AMOUNT_STATUS_REFUSE, "审核拒绝"),
        PASS(AMOUNT_STATUS_PASS, "审核通过");

        ActionStatus(Integer key, String value) {
            this.key = key;
            this.value = value;
        }

        private final Integer key;
        private final String value;

        public static String getValue(Integer key) {
            for (ActionStatus s: values()) {
                if (s.key.equals(key)) {
                    return s.value;
                }
            }
            return "";
        }
    }

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
     * 余额变动业务类型
     */
    private Integer actionType;

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
     * 提现状态
     */
    private Integer status;

    /**
     * 时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime created;

    /**
     * 金额变动名称 增加，减少
     */
    @TableField(exist = false)
    private String actionName;

    /**
     * 余额变动业务类型名称
     */
    @TableField(exist = false)
    private String actionTypeName;

    @TableField(exist = false)
    private String statusName;

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
    public Integer getActionType() {
        return actionType;
    }

    public void setActionType(Integer actionType) {
        this.actionType = actionType;
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

    public String getActionName() {
        return AmountAction.getValue(getAction());
    }

    public String getActionTypeName() {
        return AmountActionType.getValue(getActionType());
    }

    public String getStatusName() {
        return ActionStatus.getValue(getStatus());
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
