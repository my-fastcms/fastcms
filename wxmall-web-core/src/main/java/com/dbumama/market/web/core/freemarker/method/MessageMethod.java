package com.dbumama.market.web.core.freemarker.method;

import com.jfinal.i18n.I18n;
import com.jfinal.i18n.Res;

import freemarker.template.SimpleScalar;

/**
 * 模板方法 - 多语言
 * 
 * 
 * 
 */
public class MessageMethod extends WxmMethod {

	/* (non-Javadoc)
	 * @see com.dbumama.market.web.core.freemarker.method.WxmMethod#onExec()
	 */
	@Override
	public Object onExec() {
		Res resZh = I18n.use();
		String message = null;
		String code = get(0).toString();
		if (size() > 1) {
			Object[] args = argList.subList(1, argList.size()).toArray();
			message = resZh.format(code, args);
		} else {
			message = resZh.get(code);
		}
		return new SimpleScalar(message);
	}

}