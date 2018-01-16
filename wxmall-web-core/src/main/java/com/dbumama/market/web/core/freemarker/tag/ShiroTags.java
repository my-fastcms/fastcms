package com.dbumama.market.web.core.freemarker.tag;

import freemarker.template.SimpleHash;

/**
 * Shortcut for injecting the tags into Freemarker
 * <p/>
 * <p>Usage: cfg.setSharedVeriable("shiro", new ShiroTags());</p>
 */
@SuppressWarnings("serial")
public class ShiroTags extends SimpleHash {
	@SuppressWarnings("deprecation")
  	public ShiroTags() {
		put("authenticated", new AuthenticatedTag());
		put("principal", new PrincipalTag());
		put("authUser", new AuthUserTag());
  	}
}