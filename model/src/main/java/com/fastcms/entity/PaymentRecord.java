package com.fastcms.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 支付记录表
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2021-06-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PaymentRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 业务类型
     */
    public static final String TRX_TYPE_RECHARGE = "recharge";      //用户充值
    public static final String TRX_TYPE_ORDER = "order";            //订单支付
    public static final String TRX_TYPE_MEMBER = "member";          //购买会员
    public static final String TRX_TYPE_ARTICLE = "article";        //购买文章

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 相关产品ID
     */
    private Long productRelativeId;

    /**
     * 支付流水号
     */
    private String trxNo;

    /**
     * 交易业务类型  ：消费、充值等
     */
    private String trxType;

    /**
     * 签名随机字符串，一般是用来防止重放攻击
     */
    private String trxNonceStr;

    /**
     * 付款人编号
     */
    private Long userId;

    /**
     * 付款方手续费
     */
    private BigDecimal payerFee;

    /**
     * 下单ip(客户端ip,从网关中获取)
     */
    private String orderIp;

    /**
     * 订单来源
     */
    private String orderFrom;

    /**
     * 支付状态：1生成订单未支付（预支付）、 2支付失败
     */
    private Integer payStatus;

    /**
     * 支付类型编号
     */
    private String payType;

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

    private String thirdpartyTransactionId;

    private String thirdpartyUserOpenid;

    /**
     * 备注
     */
    private String remark;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime updated;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime created;


}
