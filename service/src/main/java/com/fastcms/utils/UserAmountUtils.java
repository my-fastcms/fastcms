/**
 * Copyright (c) 广州小橘灯信息科技有限公司 2016-2017, wjun_java@163.com.
 * <p>
 * Licensed under the GNU Lesser General Public License (LGPL) ,Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * http://www.xjd2020.com
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fastcms.utils;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;

/**
 * @author： wjun_java@163.com
 * @date： 2022/4/9
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public final class UserAmountUtils {

	/**
	 * 是否开启提现审核
	 */
	public static final String ENABLE_AMOUNT_CASH_OUT_AUDIT = "enableAmountCashOutAudit";

	/**
	 * 超过设置的提现金额，强制转为需要审核
	 */
	public static final String CASH_OUT_AMOUNT_OVERFLOW_AUDIT_VALUE = "cashOutAmountOverflowAuditValue";

	/**
	 * 单日提现最大次数，默认为3次
	 */
	public static final String CASH_OUT_AMOUNT_DAY_MAX_TIME_VALUE = "cashOutAmountDayMaxTimeValue";

	/**
	 * 单次提现最大金额
	 */
	public static final String CASH_OUT_AMOUNT_DAY_MAX_VALUE = "cashOutAmountDayMaxValue";

	/**
	 * 余额满多少金额允许提现
	 */
	public static final String CASH_OUT_AMOUNT_DAY_BALANCE_MAX_VALUE = "cashOutAmountDayBalanceMaxValue";

	/**
	 * 是否开启了提现审核
	 * 不开启审核，直接通过在线转账，即时到账
	 * @return
	 */
	public static Boolean isEnableAmountCashOutAudit() {
		String config = ConfigUtils.getConfig(ENABLE_AMOUNT_CASH_OUT_AUDIT);
		if (StringUtils.isBlank(config)) return true;
		try {
			return Boolean.valueOf(config);
		} catch (Exception e) {
			return true;
		}
	}

	/**
	 * 获取提现金额超过多少需要强制审核
	 * 系统默认500
	 * @return
	 */
	public static BigDecimal getAuditOverflowAmountValue() {
		return ConfigUtils.getBigDecimal(CASH_OUT_AMOUNT_OVERFLOW_AUDIT_VALUE, new BigDecimal(500));
	}

	/**
	 * 获取当日最大提现次数
	 * @return
	 */
	public static Integer getCashOutAmountDayMaxTimeValue() {
		return ConfigUtils.getInt(CASH_OUT_AMOUNT_DAY_MAX_TIME_VALUE, 3);
	}

	/**
	 * 获取单次最大提现金额
	 * 系统默认1000
	 * @return
	 */
	public static BigDecimal getCashOutAmountDayMaxValue() {
		return ConfigUtils.getBigDecimal(CASH_OUT_AMOUNT_DAY_MAX_VALUE, new BigDecimal(1000));
	}

	/**
	 * 获取余额满多少才允许提现
	 * @return
	 */
	public static BigDecimal getCashOutAmountDayBalanceMaxValue() {
		return ConfigUtils.getBigDecimal(CASH_OUT_AMOUNT_DAY_BALANCE_MAX_VALUE);
	}

}
