package com.fastcms.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 提现申请表
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2022-04-09
 */
@TableName("user_amount_payout")
public class UserAmountPayout implements Serializable {

    private static final long serialVersionUID = 1L;

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

    public enum PayOutStatus {
        AUDIT(AMOUNT_STATUS_AUDIT, "待审核"),
        REFUSE(AMOUNT_STATUS_REFUSE, "审核拒绝"),
        PASS(AMOUNT_STATUS_PASS, "审核通过");

        PayOutStatus(Integer key, String value) {
            this.key = key;
            this.value = value;
        }

        private final Integer key;
        private final String value;

        public static String getValue(Integer key) {
            for (PayOutStatus s: values()) {
                if (s.key.equals(key)) {
                    return s.value;
                }
            }
            return "";
        }
    }

    public static final Integer PAY_OUT_AUDIT_TYPE = 1;

    public static final Integer PAY_OUT_PASS_TYPE = 0;

    public enum AuditType {
        AUDIT(PAY_OUT_AUDIT_TYPE, "人工审核"),
        PASS(PAY_OUT_PASS_TYPE, "无审核");

        AuditType(Integer key, String value) {
            this.key = key;
            this.value = value;
        }

        private final Integer key;
        private final String value;

        public static String getValue(Integer key) {
            for (AuditType s: values()) {
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
     * 申请提现用户
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUserId;

    /**
     * 用户的真实名字
     */
    private String userRealName;

    /**
     * 用户的身份证号码
     */
    private String userIdcard;

    /**
     * 提现金额
     */
    private BigDecimal amount;

    /**
     * 提现类型
     */
    private Integer payType;

    /**
     * 提现账号：可能是微信的openId，可能是支付宝账号，可能是银行账号
     */
    private String payTo;

    /**
     * 提现成功证明，一般是转账截图
     */
    private String paySuccessProof;

    /**
     * 提现手续费
     */
    private BigDecimal fee;

    /**
     * 申请提现成功后会生成一个扣款记录
     */
    private Long statementId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 用户备注
     */
    private String remarks = "提现";

    /**
     * 回绝提现时给出原因
     */
    private String feedback;

    private String options;

    /**
     * 审核类型
     */
    private Integer auditType;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime created;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updated;

    /**
     * 默认使用微信转账到零钱
     */
    @TableField(exist = false)
    private String transferClassName = "com.fastcms.plugin.wechat.pay.WechatTransferService";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }
    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }
    public String getUserIdcard() {
        return userIdcard;
    }

    public void setUserIdcard(String userIdcard) {
        this.userIdcard = userIdcard;
    }
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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
    public String getPaySuccessProof() {
        return paySuccessProof;
    }

    public void setPaySuccessProof(String paySuccessProof) {
        this.paySuccessProof = paySuccessProof;
    }
    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }
    public Long getStatementId() {
        return statementId;
    }

    public void setStatementId(Long statementId) {
        this.statementId = statementId;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }
    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public Integer getAuditType() {
        return auditType;
    }

    public void setAuditType(Integer auditType) {
        this.auditType = auditType;
    }

    public String getTransferClassName() {
        return transferClassName;
    }

    public void setTransferClassName(String transferClassName) {
        this.transferClassName = transferClassName;
    }

    @Override
    public String toString() {
        return "UserAmountPayout{" +
            "id=" + id +
            ", createUserId=" + createUserId +
            ", userRealName=" + userRealName +
            ", userIdcard=" + userIdcard +
            ", amount=" + amount +
            ", payType=" + payType +
            ", payTo=" + payTo +
            ", paySuccessProof=" + paySuccessProof +
            ", fee=" + fee +
            ", statementId=" + statementId +
            ", status=" + status +
            ", remarks=" + remarks +
            ", feedback=" + feedback +
            ", options=" + options +
            ", created=" + created +
            ", updated=" + updated +
        "}";
    }
}
