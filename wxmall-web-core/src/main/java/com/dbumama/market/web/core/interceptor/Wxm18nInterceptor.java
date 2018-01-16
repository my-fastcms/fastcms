package com.dbumama.market.web.core.interceptor;

import com.jfinal.aop.Invocation;
import com.jfinal.core.Const;
import com.jfinal.core.Controller;
import com.jfinal.i18n.I18n;
import com.jfinal.i18n.I18nInterceptor;
import com.jfinal.i18n.Res;
import com.jfinal.kit.StrKit;

public class Wxm18nInterceptor extends I18nInterceptor {

	@Override
	public void intercept(Invocation inv) {

		Controller c = inv.getController();
		String localeParaName = getLocaleParaName();
		String locale = c.getPara(localeParaName);

		if (StrKit.notBlank(locale)) {
			// change locale, write cookie
			c.setCookie(localeParaName, locale, Const.DEFAULT_I18N_MAX_AGE_OF_COOKIE);
		} else {
			// get locale from cookie and use the default locale if it is null
			locale = c.getCookie(localeParaName);
			if (StrKit.isBlank(locale))
				locale = I18n.toLocale(c.getRequest().getLocale());
		}

		Res res = I18n.use(getBaseName(), locale);
		c.setAttr(getResName(), res);

		inv.invoke();
	}

	@Override
	protected String getResName() {
		return "i18n";
	}

}
