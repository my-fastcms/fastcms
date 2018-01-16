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
package com.dbumama.market.web.core.plugin.shiro;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;

/**
 * 访客访问控制处理器
 * @author wangjun
 * 2017年7月21日
 */
class GuestAuthzHandler extends AbstractAuthzHandler {
	private static GuestAuthzHandler gah = new GuestAuthzHandler();

	private GuestAuthzHandler(){}

	public static  GuestAuthzHandler me(){
		return gah;
	}

	public void assertAuthorized() throws AuthorizationException {
		 if (getSubject().getPrincipal() != null) {
	            throw new UnauthenticatedException("Attempting to perform a guest-only operation.  The current Subject is " +
	                    "not a guest (they have been authenticated or remembered from a previous login).  Access " +
	                    "denied.");
	        }
	}

}
