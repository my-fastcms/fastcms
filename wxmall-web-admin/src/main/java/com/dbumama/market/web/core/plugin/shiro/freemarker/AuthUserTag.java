package com.dbumama.market.web.core.plugin.shiro.freemarker;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.dbumama.market.model.AuthUser;
import com.dbumama.market.model.SellerUser;

import freemarker.core.Environment;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;

/**
 * 获取登陆用户授权的公众号列表
 * @author wangjun
 *
 */
public class AuthUserTag extends SecureTag {

	private static final String CACHENAME = "auth_user_cache";
	private static final String CACHENAME_KEY = "key";
	
	@Override
	public void render(Environment env, Map params, TemplateDirectiveBody body) throws IOException, TemplateException {
		if (getSubject() != null) {
			Object principal = getSubject().getPrincipal();
			if(principal != null && principal instanceof SellerUser){
				SellerUser sellerUser = (SellerUser) principal;
				List<AuthUser> authUsers = AuthUser.dao.findByCache(CACHENAME, CACHENAME_KEY + "_" + sellerUser.getId(), "select * from " + AuthUser.table + " where seller_id=? and active=1", sellerUser.getId());
				env.setVariable("authUsers", ObjectWrapper.DEFAULT_WRAPPER.wrap(authUsers));
				body.render(env.getOut());
			}
		}
	}

}
