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
package com.dbumama.market.web.core.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;

import com.jfinal.core.JFinal;
import com.jfinal.handler.Handler;
import com.jfinal.kit.HandlerKit;

/**
 * @author wangjun
 * 2017年7月20日
 */
public class ShiroHandler extends Handler{

	/* (non-Javadoc)
	 * @see com.jfinal.handler.Handler#handle(java.lang.String, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, boolean[])
	 */
	@Override
	public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {

		if("/register".equals(target) 
				|| "/doRegister".equals(target)
				|| "/sendCode".equals(target)
				|| "/auth".equals(target)
				|| "/captcha".equals(target)
				|| target.contains("/wx/message")
				|| target.contains("/install")
				|| target.contains("/upload/image/")
				|| target.contains("/resources/")
				|| target.contains(".txt")
				){
			next.handle(target, request, response, isHandled);
			return;
		}	
		
		if(SecurityUtils.getSubject() == null 
				|| SecurityUtils.getSubject().getPrincipal() == null
				|| !SecurityUtils.getSubject().isAuthenticated()){
			if (!target.equals("/login")) {
				processNotLogin(request, response, isHandled);
				return;				
			}
		}
		next.handle(target, request, response, isHandled);
	}
	
	private void processNotLogin(HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
		HandlerKit.redirect(JFinal.me().getContextPath() + "/login", request, response, isHandled);
	}

}
