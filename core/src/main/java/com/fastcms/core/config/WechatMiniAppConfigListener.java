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

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import com.fastcms.utils.ConfigUtils;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.fastcms.common.constants.FastcmsConstants.*;

/**
 * 微信小程序配置改变事件
 * @author： wjun_java@163.com
 * @date： 2022/03/03
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component
public class WechatMiniAppConfigListener extends AbstractConfigListener implements ApplicationListener<ApplicationStartedEvent> {

    @Autowired
    private WxMaService wxMaService;

    @Override
    protected String getMatchKey() {
        return "wechat_miniapp_";
    }

    @Override
    protected void doChange(Map<String, String> datasMap) {
        WxMaDefaultConfigImpl configStorage = new WxMaDefaultConfigImpl();
        configStorage.setAppid(getAppId());
        configStorage.setSecret(getAppSecret());
        configStorage.setToken(getAppToken());
        configStorage.setAesKey(getAppAesKey());
        Map<String, WxMaConfig> configStorages = Maps.newHashMap();
        configStorages.put(configStorage.getAppid(), configStorage);

        wxMaService.setMultiConfigs(configStorages, configStorage.getAppid());
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        if (StringUtils.isNotBlank(getAppId()) && StringUtils.isNotBlank(getAppSecret())) {
            WxMaDefaultConfigImpl configStorage = new WxMaDefaultConfigImpl();
            configStorage.setAppid(getAppId());
            configStorage.setSecret(getAppSecret());
            configStorage.setToken(getAppToken());
            configStorage.setAesKey(getAppAesKey());
            wxMaService.addConfig(configStorage.getAppid(), configStorage);
        }
    }

    String getAppId() {
        return ConfigUtils.getConfig(WECHAT_MINIAPP_APP_ID);
    }

    String getAppSecret() {
        return ConfigUtils.getConfig(WECHAT_MINIAPP_APP_SECRET);
    }

    String getAppToken() {
        return ConfigUtils.getConfig(WECHAT_MINIAPP_APP_TOKEN);
    }

    String getAppAesKey() {
        return ConfigUtils.getConfig(WECHAT_MINIAPP_APP_AESKEY);
    }

}
