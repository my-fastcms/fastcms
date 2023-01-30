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

import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.http.HttpRestResult;
import com.fastcms.common.utils.HttpUtils;
import com.fastcms.core.sms.AliyunSmsSender;
import com.fastcms.core.sms.SmsMessage;
import com.fastcms.plugin.FastcmsPluginManager;
import com.fastcms.plugin.register.CompoundPluginRegister;
import com.fastcms.utils.ApplicationUtils;
import com.fastcms.utils.ConfigUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.lang.reflect.Method;
import java.util.Date;

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

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @Test
    public void testPasswordEncode() {
        boolean matches = passwordEncoder.matches("1", passwordEncoder.encode("1"));
        logger.info("matches:" + matches);
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
    public void testSendMail() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(ConfigUtils.getConfig(FastcmsConstants.EMAIL_USERNAME));
        simpleMailMessage.setTo("24582711@qq.com");
        simpleMailMessage.setSubject("测试邮件");
        simpleMailMessage.setText("您的fastcms初始密码：888888");
        javaMailSender.send(simpleMailMessage);
    }

    @Test
    public void testSendHtmlMail() throws Exception {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(ConfigUtils.getConfig(FastcmsConstants.EMAIL_USERNAME));
        helper.setTo("24582711@qq.com");
        helper.setSubject("Fastcms测试Html邮件");
        helper.setText("<p>您的fastcms初始账号密码为:888888</p>" +
                "<p>fastcms官网：https://www.xjd2020.com</p>" +
                "<p>fastcms文档：http://doc.xjd2020.com</p>", true);
        helper.setSentDate(new Date());
        javaMailSender.send(mimeMessage);
    }

    @Test
    public void testPlugin() throws Exception {
        final String pluginId = "hello-world-plugin";

        unInstallPlugin(pluginId);

        installPlugin(pluginId);

        Object bean = ApplicationUtils.getBean("com.fastcms.hello.HelloComponent");
        logger.info("bean:" + bean);
        Object bean1 = ApplicationUtils.getBean("com.fastcms.hello.HelloComponent2");
        logger.info("bean:" + bean1);
        for (Method method : bean.getClass().getMethods()) {
            if (method.getName().equals("hello")) {
                Object invoke = method.invoke(bean, null);
                logger.info("bean:" + invoke);
                break;
            }
        }

        Object helloService = ApplicationUtils.getBean("com.fastcms.hello.HelloServiceImpl");
        for (Method method : helloService.getClass().getMethods()) {
            if (method.getName().equals("findByHelloId")) {

                try {
                    Object result = method.invoke(helloService, Long.valueOf(1));
                    logger.info("result:" + result);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            }
        }

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
