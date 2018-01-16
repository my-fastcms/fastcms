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
package com.dbumama.market.web.core.config;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.dbumama.market.model._MappingKit;
import com.dbumama.market.web.core.install.InstallController;
import com.dbumama.market.web.core.interceptor.Wxm18nInterceptor;
import com.dbumama.market.web.core.plugin.message.MessagePlugin;
import com.dbumama.market.web.core.plugin.spring.SpringPlugin;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.druid.DruidStatViewHandler;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;
import com.weixin.sdk.kit.WxSdkPropKit;

/**
 * @author wangjun
 * 2017年7月10日
 */
public abstract class WxmWebConfig extends JFinalConfig{

	protected Logger logger = Logger.getLogger(WxmWebConfig.class);

	private WallFilter wallFilter;
	
	@Override
	public void configConstant(Constants me) {
		PropKit.use("wxmall.properties");
		
		me.setDevMode(PropKit.getBoolean("dev_mode", false));
		me.setViewType(ViewType.FREE_MARKER);
		me.setBaseUploadPath("upload/image");
		me.setEncoding("UTF-8");
		me.setI18nDefaultBaseName("i18n");
		me.setMaxPostSize(1024 * 1024 * 5);
		me.setI18nDefaultLocale(PropKit.get("locale"));
		me.setError401View("/404.html");
		me.setError403View("/404.html");
		me.setError404View("/404.html");
		me.setError500View("/500.html");
	}

	@Override
	public void configRoute(Routes me) {
		configWebRoute(me);
		
		me.add("/install", InstallController.class);
	}

	@Override
	public void configPlugin(Plugins me) {
		//spring plugin
		SpringPlugin springPlugin = new SpringPlugin();
		me.add(springPlugin);
		//缓存插件
		me.add(new EhCachePlugin());
		me.add(new MessagePlugin());
		
		if(!Wxmall.isInstalled()){
			logger.info("======== wxmall wait to install ...");
		}else{
			DruidPlugin druidPlugin = createDruidPlugin();
			me.add(druidPlugin);
			//添加自动绑定model与表插件
			ActiveRecordPlugin activeRecordPlugin = new ActiveRecordPlugin(druidPlugin);
			activeRecordPlugin.setShowSql(JFinal.me().getConstants().getDevMode());
			activeRecordPlugin.setContainerFactory(new CaseInsensitiveContainerFactory(true));
			_MappingKit.mapping(activeRecordPlugin);
			me.add(activeRecordPlugin);
		}
		
		configWebPlugin(me);
	}
	
	@Override
	public void configEngine(Engine me) {
		configWebEngine(me);
	}

	@Override
	public void configInterceptor(Interceptors me) {
		me.add(new Wxm18nInterceptor());
		configWebInterceptor(me);
	}

	@Override
	public void configHandler(Handlers me) {
		me.add(new DruidStatViewHandler("/durid"));
		configWebHandler(me);
	}

	@Override
	public void afterJFinalStart() {
		if(Wxmall.isConfiged()){
			WxSdkPropKit.use(new File(Wxmall.BASE_DIRECTORY + "wx.sdk.properties"));
			logger.info("========config wx_app_id:" + WxSdkPropKit.get("wx_app_id"));
		}else{
			logger.info("========not config wxapp");
		}
		/*if(wallFilter != null)
			wallFilter.getConfig().setSelectUnionCheck(false);*/
		onWxMallStart();
		super.afterJFinalStart();
	}
	
	@Override
	public void beforeJFinalStop() {
		/*Enumeration<java.sql.Driver> drivers = DriverManager.getDrivers();
		if (drivers != null) {
			while (drivers.hasMoreElements()) {
				try {
					Driver driver = (Driver) drivers.nextElement();
					DriverManager.deregisterDriver(driver);						
				} catch (Exception e) {
					logger.error("deregisterDriver error in beforeJFinalStop() method.", e);
				}
			}
		}*/
		onWxMallStop();
		super.beforeJFinalStop();
	}
	
	public DruidPlugin createDruidPlugin() {
		Prop dbProp = new Prop(new File(Wxmall.BASE_DIRECTORY + "jdbc.properties"));
		String db_host = dbProp.get("db_host").trim();

		String db_host_port = dbProp.get("db_host_port");
		db_host_port = StringUtils.isNotBlank(db_host_port) ? db_host_port.trim() : "3306";

		String db_name = dbProp.get("db_name").trim();
		String db_user = dbProp.get("db_user").trim();
		String db_password = dbProp.get("db_password").trim();

		String jdbc_url = "jdbc:mysql://" + db_host + ":" + db_host_port + "/" + db_name + "?" + "useUnicode=true&"
				+ "characterEncoding=utf8&" + "zeroDateTimeBehavior=convertToNull";

		DruidPlugin druidPlugin = new DruidPlugin(jdbc_url, db_user, db_password);
		druidPlugin.addFilter(new StatFilter());
		wallFilter = new WallFilter();              // 加强数据库安全
	    wallFilter.setDbType("mysql");
	    druidPlugin.addFilter(wallFilter);
		return druidPlugin;
	}
	
	protected abstract void configWebRoute(Routes me);
	protected abstract void configWebPlugin(Plugins me);
	protected abstract void configWebHandler(Handlers me);
	protected abstract void configWebInterceptor(Interceptors me);
	protected abstract void configWebEngine(Engine me);
	protected abstract void onWxMallStart();
	protected abstract void onWxMallStop();
	
}
