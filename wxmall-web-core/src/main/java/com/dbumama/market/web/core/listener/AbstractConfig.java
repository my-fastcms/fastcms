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
package com.dbumama.market.web.core.listener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.dbumama.market.model.AuthUserConfig;
import com.dbumama.market.model.Weapp;
import com.dbumama.market.web.core.config.Wxmall;
import com.weixin.sdk.kit.WxSdkPropKit;

/**
 * @author wangjun
 * 2017年7月27日
 */
public abstract class AbstractConfig {
	
	protected void saveWxsdkProp(AuthUserConfig authConfig, Weapp weapp){
		Properties p = new Properties();
		
		if(authConfig != null){
			p.put("wx_app_name", authConfig.getAppName());
			p.put("wx_app_id", authConfig.getAppId());
			p.put("wx_app_secret", authConfig.getAppSecret());
			p.put("admin_domain", authConfig.getAdminDomain());
			p.put("wx_domain", authConfig.getWxDomain());
			p.put("img_domain", authConfig.getImgDomain());
			p.put("token", authConfig.getMsgToken());
			p.put("encryptMessage", "true");
			p.put("encodingAesKey", authConfig.getMsgAesKey());
			p.put("wx_mch_id", authConfig.getPayMchId());
			p.put("wx_secret_key", authConfig.getPaySecretKey());
			p.put("wx_notify_url", authConfig.getPayResultUrl());
			p.put("seller_id", String.valueOf(authConfig.getSellerId()));
		}
		
		if(weapp != null){
			p.put("weapp_id", weapp.getWeappId());
			p.put("weapp_secret", weapp.getWeappSecret());
		}
		
		File pFile = new File(Wxmall.BASE_DIRECTORY, "wx.sdk.properties");
		if(save(p, pFile)){
			WxSdkPropKit.useless("wx.sdk.properties");
			WxSdkPropKit.use(pFile);
		}
	}
	
	private boolean save(Properties p, File pFile) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(pFile);
			p.store(fos, "Auto create by Wxmall");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (fos != null)
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return true;
	}
	
}
