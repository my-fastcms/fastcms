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
package com.dbumama.market.web.common;

import com.dbumama.market.web.core.config.WxmFreekConfig;
import com.dbumama.market.web.core.handler.ShiroHandler;
import com.dbumama.market.web.core.handler.WxmHandler;
import com.dbumama.market.web.core.plugin.shiro.ShiroInterceptor;
import com.dbumama.market.web.core.plugin.shiro.ShiroPlugin;
import com.dbumama.market.web.core.route.AdminRoutes;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;

/**
 * @author wangjun
 * 2017年7月10日
 */
public class WebConfig extends WxmFreekConfig {

	private Routes routes;
	
	@Override
	protected void configWebRoute(Routes me) {
		this.routes = new AdminRoutes();
		me.add(this.routes);
	}

	@Override
	protected void configWebInterceptor(Interceptors me) {
		me.add(new ShiroInterceptor());
	}

	@Override
	public void configWebPlugin(Plugins me) {
	    me.add(new ShiroPlugin(this.routes));
	}

	/* (non-Javadoc)
	 * @see com.dbumama.market.web.core.config.WxmWebConfigAdapter#configWebHandler(com.jfinal.config.Handlers)
	 */
	@Override
	protected void configWebHandler(Handlers me) {
		super.configWebHandler(me);
		me.add(new ShiroHandler());
		me.add(new WxmHandler());
	}

	@Override
	protected void onWxMallStart() {
		configFreemarker();
		logger.info(" dbu admin server is ready!!");		
	}

	@Override
	protected void onWxMallStop() {
	}

}
