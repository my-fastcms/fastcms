/**
 * Copyright (c) 广州点步信息科技有限公司 2016-2017, wjun_java@163.com.
 *
 * Licensed under the GNU Lesser General Public License (LGPL) ,Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.gnu.org/licenses/lgpl-3.0.txt
 *	    http://www.dbumama.com
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dbumama.market.web.core.freemarker.tag;

import com.dbumama.market.model.AuthUserConfig;
import com.dbumama.market.model.SellerUser;

import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateModelException;

/**
 * @author wangjun
 * 2017年7月20日
 */
public class AuthUserTag extends WxmShiroTag{

	private static final String USED_AUTH_CACHENAME = "used_auth_user_cache";
	private static final String USED_AUTH_CACHENAME_KEY = "used_key";
	private static final AuthUserConfig authConfigDao = new AuthUserConfig().dao();
	
	/* (non-Javadoc)
	 * @see com.dbumama.market.web.core.freemarker.tag.WxmTag#onRender()
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void onRender() {
		if (getSubject() != null) {
			Object principal = getSubject().getPrincipal();
			if(principal != null && principal instanceof SellerUser){
				AuthUserConfig authUser = authConfigDao.findFirstByCache(USED_AUTH_CACHENAME, USED_AUTH_CACHENAME_KEY, "select * from " + AuthUserConfig.table);
				try {
					setVariable("authUser", ObjectWrapper.DEFAULT_WRAPPER.wrap(authUser));
					renderBody();
				} catch (TemplateModelException e) {
					renderText(e.getMessage());
				}
			}
		}
	}

}
