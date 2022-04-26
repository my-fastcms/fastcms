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
package com.fastcms.web;

import com.fastcms.common.http.HttpRestResult;
import com.fastcms.common.utils.HttpUtils;
import com.fastcms.core.sms.AliyunSmsSender;
import com.fastcms.core.sms.SmsMessage;
import com.fastcms.plugin.FastcmsPluginManager;
import com.fastcms.plugin.register.CompoundPluginRegister;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

/**
 * @author： wjun_java@163.com
 * @date： 2021/2/28
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@SpringBootTest
public class TestCaseService {

    private static final Logger logger = LoggerFactory.getLogger(TestCaseService.class);

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AliyunSmsSender aliyunSmsSender;

    @Autowired
    private FastcmsPluginManager fastcmsPluginManager;

    @Test
    public void testPasswordEncode() {
        logger.info(passwordEncoder.encode("1"));
    }

    @Test
    public void testHttp() throws Exception {
        HttpRestResult<String> objectHttpRestResult = HttpUtils.get("https://www.xjd2020.com");
        logger.info(objectHttpRestResult.getData());
    }

    @Test
    public void testSendSms() {
        SmsMessage smsMessage = new SmsMessage("13533109940", "小橘灯", "SMS_198840162", "1688");
        boolean send = aliyunSmsSender.send(smsMessage);
        logger.info("============>>>send:" + send);
    }

    @Test
    public void testPlugin() throws Exception {
        final String pluginId = "hello-world-plugin";

        unInstallPlugin(pluginId);

//        installPlugin(pluginId);

    }

    private void unInstallPlugin(String pluginId) throws Exception {
        new CompoundPluginRegister(fastcmsPluginManager).unRegistry(pluginId);
        fastcmsPluginManager.stopPlugin(pluginId);
        fastcmsPluginManager.unloadPlugin(pluginId);
    }

    private void installPlugin(String pluginId) throws Exception {
        fastcmsPluginManager.loadPlugins();
        fastcmsPluginManager.startPlugin(pluginId);
        new CompoundPluginRegister(fastcmsPluginManager).registry(pluginId);
    }

}
