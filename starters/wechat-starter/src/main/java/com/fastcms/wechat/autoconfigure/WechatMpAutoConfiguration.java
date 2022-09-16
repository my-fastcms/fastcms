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
package com.fastcms.wechat.autoconfigure;

import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author： wjun_java@163.com
 * @date： 2022/03/02
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Configuration
@ConditionalOnClass({WxMpService.class})
public class WechatMpAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(WxMpService.class)
    public WxMpService wxMpService() {
        WxMpService wxMpService =  new WxMpServiceImpl();
        wxMpService.setMaxRetryTimes(3);
        return wxMpService;
    }

    /**
     * 公众号消息路由规则配置
     * @param wxMpService
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(WxMpMessageRouter.class)
    public WxMpMessageRouter mpMessageRouter(WxMpService wxMpService) {
        final WxMpMessageRouter fastcmsRouter = new WxMpMessageRouter(wxMpService);
        return fastcmsRouter;
    }

}
