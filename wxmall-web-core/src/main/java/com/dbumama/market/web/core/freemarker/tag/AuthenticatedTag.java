package com.dbumama.market.web.core.freemarker.tag;

public class AuthenticatedTag extends WxmShiroTag {

	/* (non-Javadoc)
	 * @see com.dbumama.market.web.core.freemarker.tag.WxmShiroTag#onRender()
	 */
	@Override
	public void onRender() {
		super.onRender();
		if (getSubject() != null && (getSubject().isAuthenticated() || getSubject().isRemembered())){
			renderBody();
		}
	}
	
}