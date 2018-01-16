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

import com.jfinal.plugin.activerecord.Model;

/**
 * @author wangjun
 * 2017年7月20日
 */
public class PrincipalTag extends WxmShiroTag{

	/* (non-Javadoc)
	 * @see com.dbumama.market.web.core.freemarker.tag.WxmShiroTag#onRender()
	 */
	@Override
	public void onRender() {
		super.onRender();
	    if (getSubject() != null) {
	    	Object principal = getSubject().getPrincipal();
	    	if (principal != null && principal instanceof Model) {
	    		Model<?> user = (Model<?>) principal;
	            renderText(user.get("phone").toString());
	    	}
	    }
	}

}
