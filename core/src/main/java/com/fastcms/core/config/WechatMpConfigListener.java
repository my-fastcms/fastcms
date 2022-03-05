/**
 * Copyright (c) 广州小橘灯信息科技有限公司 2016-2017, wjun_java@163.com.
 * <p>
 * Licensed under the GNU Lesser General Public License (LGPL) ,Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * http://www.xjd2020.com
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fastcms.core.config;

import com.fastcms.utils.ConfigUtils;
import com.google.common.collect.Maps;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 微信公众号配置改变事件
 * @author： wjun_java@163.com
 * @date： 2022/03/03
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component
public class WechatMpConfigListener implements ConfigListener, ApplicationListener<ApplicationStartedEvent> {

    static final String WECHAT_MP_APP_ID = "wechate_mp_appid";
    static final String WECHAT_MP_APP_SECRET = "wechat_mp_secret";
    static final String WECHAT_MP_APP_TOKEN = "wechat_mp_token";
    static final String WECHAT_MP_APP_AESKEY = "wechat_mp_aeskey";

    @Autowired
    private WxMpService wxMpService;

    @Override
    public void change(Map<String, String> datasMap) {
        if (isMatch(datasMap)) {
            WxMpDefaultConfigImpl configStorage = new WxMpDefaultConfigImpl();
            configStorage.setAppId(getAppId());
            configStorage.setSecret(getAppSecret());
            configStorage.setToken(getAppToken());
            configStorage.setAesKey(getAppAesKey());
            Map<String, WxMpConfigStorage> configStorages = Maps.newHashMap();
            configStorages.put(configStorage.getAppId(), configStorage);

            wxMpService.setMultiConfigStorages(configStorages, configStorage.getAppId());
        }
    }

    @Override
    public boolean isMatch(Map<String, String> datasMap) {
        for (Map.Entry<String, String> entry : datasMap.entrySet()) {
            String key = entry.getKey().trim();
            if(WECHAT_MP_APP_ID.equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        if (StringUtils.isNotBlank(getAppId()) && StringUtils.isNotBlank(getAppSecret())) {
            WxMpDefaultConfigImpl configStorage = new WxMpDefaultConfigImpl();
            configStorage.setAppId(getAppId());
            configStorage.setSecret(getAppSecret());
            configStorage.setToken(getAppToken());
            configStorage.setAesKey(getAppAesKey());
            wxMpService.addConfigStorage(configStorage.getAppId(), configStorage);
        }
    }

    String getAppId() {
        return ConfigUtils.getConfig(WECHAT_MP_APP_ID);
    }

    String getAppSecret() {
        return ConfigUtils.getConfig(WECHAT_MP_APP_SECRET);
    }

    String getAppToken() {
        return ConfigUtils.getConfig(WECHAT_MP_APP_TOKEN);
    }

    String getAppAesKey() {
        return ConfigUtils.getConfig(WECHAT_MP_APP_AESKEY);
    }

}