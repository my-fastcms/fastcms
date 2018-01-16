package com.dbumama.market.web.core.freemarker.method;

import java.math.BigDecimal;

import com.jfinal.kit.PropKit;

import freemarker.template.SimpleScalar;

/**
 * 模板方法 - 货币格式化
 * 
 * 
 * 
 */
public class CurrencyMethod extends WxmMethod {

	public BigDecimal setScale(BigDecimal amount) {
		if (amount == null) {
			return null;
		}
		int roundingMode;
		if ("roundHalfUp".equals(PropKit.get("priceRoundType"))) {
			roundingMode = BigDecimal.ROUND_UP;
		} else if ("roundUp".equals(PropKit.get("priceRoundType"))) {
			roundingMode = BigDecimal.ROUND_DOWN;
		} else {
			roundingMode = BigDecimal.ROUND_HALF_UP;
		}
		return amount.setScale(2, roundingMode);
	}

	/* (non-Javadoc)
	 * @see com.dbumama.market.web.core.freemarker.method.WxmMethod#onExec()
	 */
	@Override
	public Object onExec() {
		boolean showSign = false;
		boolean showUnit = false;
		if (size() == 2) {
			if (get(1) != null) {
				showSign = Boolean.valueOf(get(1).toString());
			}
		} else if (size() > 2) {
			if (get(1) != null) {
				showSign = Boolean.valueOf(get(1).toString());
			}
			if (get(2) != null) {
				showUnit = Boolean.valueOf(get(2).toString());
			}
		}
		BigDecimal amount = new BigDecimal(get(0).toString());
		String price = setScale(amount).toString();
		if (showSign) {
			price = "￥" + price;
		}
		if (showUnit) {
			price += "元";
		}
		return new SimpleScalar(price);
	}

}