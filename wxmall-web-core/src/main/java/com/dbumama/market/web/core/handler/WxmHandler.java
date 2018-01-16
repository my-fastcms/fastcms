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

import com.dbumama.market.service.utils.FileUtils;
import com.dbumama.market.web.core.config.Wxmall;
import com.jfinal.core.JFinal;
import com.jfinal.handler.Handler;
import com.jfinal.kit.HandlerKit;

/**
 * @author wangjun
 * 2017年7月20日
 */
public class WxmHandler extends Handler{

	/* (non-Javadoc)
	 * @see com.jfinal.handler.Handler#handle(java.lang.String, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, boolean[])
	 */
	@Override
	public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
		
		if (target.startsWith("/websocket")) {
			return;
		}
		
		// 程序还没有安装
		if (!Wxmall.isInstalled()) {
			if (target.indexOf('.') != -1) {
				return;
			}

			if (!target.startsWith("/install")) {
				processNotInstall(request, response, isHandled);
				return;
			}
		}
		
		//在用户登陆情况下检查，系统是否配置公众账号
		if(SecurityUtils.getSubject().getPrincipal() != null && SecurityUtils.getSubject().isAuthenticated() && !Wxmall.isConfiged()){
			
			if("/register".equals(target) 
					|| "/doRegister".equals(target)
					|| "/sendCode".equals(target)
					|| "/auth".equals(target)
					|| "/captcha".equals(target)
					|| "/logout".equals(target)
					|| "/setting/save".equals(target)
					|| target.contains("/install")
					|| target.contains("/wx/message")
					|| target.contains("/resources/")
					|| target.contains("/upload/image/")
					){
				next.handle(target, request, response, isHandled);
				return;
			}
			
			if (!target.equals("/setting/config")) {
				processNotConfig(request, response, isHandled);
				return;				
			}
		}
		
		if (isDisableAccess(target)) {
			HandlerKit.renderError404(request, response, isHandled);
			return;
		}
		
		next.handle(target, request, response, isHandled);
		
	}

	private void processNotInstall(HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
		HandlerKit.redirect(JFinal.me().getContextPath() + "/install", request, response, isHandled);
	}
	
	private void processNotConfig(HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
		HandlerKit.redirect(JFinal.me().getContextPath() + "/setting/config", request, response, isHandled);
	}
	
	private static boolean isDisableAccess(String target) {
		// 防止直接访问模板文件
		if (target.endsWith(".html") && target.startsWith("/template")) {
			return true;
		}
		// 防止直接访问jsp文件页面
		if (".jsp".equalsIgnoreCase(FileUtils.getSuffix(target))) {
			return true;
		}

		return false;
	}
	
}
