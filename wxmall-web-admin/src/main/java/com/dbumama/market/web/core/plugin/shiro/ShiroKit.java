/**
 * Copyright (c) 2011-2013, dafei 李飞 (myaniu AT gmail DOT com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dbumama.market.web.core.plugin.shiro;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.AntPathMatcher;
import org.apache.shiro.web.util.WebUtils;

import com.dbumama.market.web.core.config.JFWebConfig;

import cn.dreampie.encription.EncriptionKit;


/**
 * ShiroKit. (Singleton, ThreadSafe)
 *
 * @author dafei
 */
public class ShiroKit {
	/**
	 * 登录成功时所用的页面。
	 */
	private static String successUrl = "/";

	/**
	 * 登录成功时所用的页面。
	 */
	private static String loginUrl = "/login";


	/**
	 * 登录成功时所用的页面。
	 */
	private static String unauthorizedUrl ="/401.jsp";


	/**
	 * Session中保存的请求的Key值
	 */
	private static String SAVED_REQUEST_KEY = "jfinalShiroSavedRequest";


	/**
	 * 用来记录那个action或者actionpath中是否有shiro认证注解。
	 */
	private static ConcurrentMap<String, AuthzHandler> authzMaps = null;

	/**
	 * 禁止初始化
	 */
	private ShiroKit() {}

	static void init(ConcurrentMap<String, AuthzHandler> maps) {
		authzMaps = maps;
	}

	static AuthzHandler getAuthzHandler(String actionKey){
		/*
		if(authzMaps.containsKey(controllerClassName)){
			return true;
		}*/
		return authzMaps!=null && authzMaps.size()>0 ? authzMaps.get(actionKey) : null;
	}

	public static final String getSuccessUrl() {
		return successUrl;
	}

	public static final void setSuccessUrl(String successUrl) {
		ShiroKit.successUrl = successUrl;
	}

	public static final String getLoginUrl() {
		return loginUrl;
	}

	public static final void setLoginUrl(String loginUrl) {
		ShiroKit.loginUrl = loginUrl;
	}

	public static final String getUnauthorizedUrl() {
		return unauthorizedUrl;
	}

	public static final void setUnauthorizedUrl(String unauthorizedUrl) {
		ShiroKit.unauthorizedUrl = unauthorizedUrl;
	}
	/**
	 * Session中保存的请求的Key值
	 * @return
	 */
	public static final String getSavedRequestKey(){
		return SAVED_REQUEST_KEY;
	}
	
	public static boolean doCaptcha(String captchaName, String captchaToken) {
	    Session session = getSession();
	    if (session.getAttribute(captchaName) != null) {
	      String captcha = session.getAttribute(captchaName).toString();
	      if (captchaToken != null &&
	          captcha.equalsIgnoreCase(EncriptionKit.encrypt(captchaToken))) {
	        return true;
	      }
	    }
	    return false;
	  }
	
	public static Session getSession() {
	    Subject subject = SecurityUtils.getSubject();
	    Session session = subject.getSession();
	    if (session == null) {
	      throw new UnknownSessionException("Unable found required Session");
	    } else {
	      return session;
	    }
	}
	
	private static Map<String, AuthzHandler> authzJdbcMaps = null;

	public static AntPathMatcher antPathMatcher = new AntPathMatcher();

	/**
	 * jdbc的权限加载器
	 */
	private static JdbcAuthzService jdbcAuthzService;

	private static boolean and = false;

	static void init(JdbcAuthzService jdbcAuthzSrc, ConcurrentMap<String, AuthzHandler> amaps, boolean isAnd) {
		jdbcAuthzService = jdbcAuthzSrc;
		authzMaps = amaps;
		and = isAnd;
		// 加载数据库权限
		loadJdbcAuthz();
	}

	/*static AuthzHandler getAuthzHandler(String actionKey) {
		
		if(authzMaps.containsKey(controllerClassName)){ return true; }
		 
		return authzMaps.get(actionKey);
	}*/

	static List<AuthzHandler> getJdbcAuthzHandler(HttpServletRequest request) {
		/*
		 * if(authzMaps.containsKey(controllerClassName)){ return true; }
		 */
		List<AuthzHandler> result = new ArrayList<AuthzHandler>();
		String url = WebUtils.getPathWithinApplication(request);
		if(authzJdbcMaps != null){
			for (String key : authzJdbcMaps.keySet()) {
				if (antPathMatcher.match(key, url)) {
					result.add(authzJdbcMaps.get(key));
					if (!and)
						break;
				}
			}
		}
		return result;
	}

	static List<AuthzHandler> getAuthzHandler(HttpServletRequest request,
			String actionKey) {
		List<AuthzHandler> result = getJdbcAuthzHandler(request);
		AuthzHandler ah = getAuthzHandler(actionKey);
		if (ah != null) {
			result.add(ah);
		}
		return result;
	}

	/**
	 * 判断是否已经存在一个相同的路径
	 *
	 * @param url
	 *            url
	 * @return boolean
	 */
	public static boolean hasJdbcAuthz(String url) {
		return authzJdbcMaps.containsKey(url);
	}

	/**
	 * jdbc 权限
	 *
	 * @param url
	 *            权限url规则
	 * @param value
	 *            权限标识
	 */
	public static void addJdbcAuthz(String url, String value) {
		authzJdbcMaps.put(url, new JdbcPermissionAuthzHandler(value));
	}

	/**
	 * jdbc 取消某个权限
	 *
	 * @param url
	 *            权限url规则
	 */
	public static void removeJdbcAuthz(String url) {
		authzJdbcMaps.remove(url);
	}

	/**
	 * load jdbc 权限
	 */
	public static void loadJdbcAuthz() {
		loadJdbcAuthz(false);
	}

	/**
	 * @param clear
	 *            清除原来的权限
	 */
	public static void loadJdbcAuthz(boolean clear) {
		// 加载数据库的url配置
		// 加载jdbc权限
		if (jdbcAuthzService != null) {
			if (clear && authzJdbcMaps != null) {
				authzJdbcMaps.clear();
			}
			authzJdbcMaps = jdbcAuthzService.getJdbcAuthz();
			if(authzJdbcMaps.size()<=0){
				logger.debug("authzJdbcService not found!can't load database url premission");
			}
		} 
		else{
			logger.debug("authzJdbcService not found!can't load database url premission");
		}
	}
	private static Logger logger = Logger.getLogger(JFWebConfig.class);
}
