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
package com.fastcms.core.directive;

import com.fastcms.core.template.FastcmsTemplateFreeMarkerConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author： wjun_java@163.com
 * @date： 2021/2/26
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Configuration
public class FreemarkerConfig implements ApplicationContextAware, InitializingBean {

    private ApplicationContext applicationContext;

    @Autowired
    private freemarker.template.Configuration configuration;
    @Autowired
    private FastcmsTemplateFreeMarkerConfig fastcmsTemplateFreeMarkerConfig;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //注册freemarker自定义标签
        Map<String, BaseDirective> matchingBeans =
                BeanFactoryUtils.beansOfTypeIncludingAncestors(this.applicationContext, BaseDirective.class, true, false);
        matchingBeans.keySet().forEach(item -> {
            configuration.setSharedVariable(item, matchingBeans.get(item));
            fastcmsTemplateFreeMarkerConfig.getConfiguration().setSharedVariable(item, matchingBeans.get(item));
        });
    }

}
