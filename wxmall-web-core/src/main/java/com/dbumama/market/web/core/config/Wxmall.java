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

import org.apache.log4j.Logger;

/**
 * @author wangjun
 * 2017年7月20日
 */
public final class Wxmall {
	private static final Logger logger = Logger.getLogger(Wxmall.class);
	public static final String SYSTEM_PROPERTY_WXMALL_HOME = "wxmall.home";
	public static final String BASE_DIRECTORY;
	private static boolean isInstalled = false;
	private static boolean isConfiged = false;	//是否配置过公众号信息

	static {
		String baseDirectory = System.getProperty(SYSTEM_PROPERTY_WXMALL_HOME, System.getProperty("user.home") + File.separator + "wxmall" + File.separator);
        BASE_DIRECTORY = baseDirectory;
        logger.info("Using base directory: " + BASE_DIRECTORY);
        //logger.info("root class path：" + PathKit.getRootClassPath());
        File f = new File(baseDirectory);
        if(!f.exists()) f.mkdir();
	}
	
	public static boolean isInstalled() {
		if (!isInstalled) {
			File dbConfig = new File(BASE_DIRECTORY, "jdbc.properties");
			isInstalled = dbConfig.exists();
		}
		return isInstalled;
	}

	public static boolean isConfiged(){
		if (!isConfiged) {
			File dbConfig = new File(BASE_DIRECTORY, "wx.sdk.properties");
			isConfiged = dbConfig.exists();
		}
		return isConfiged;
	}
	
}
