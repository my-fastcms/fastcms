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

package com.fastcms.plugin.register;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.plugin.FastcmsPluginManager;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.util.ClassUtils;

import java.util.Set;

/**
 * 给插件中Extensions加上代理
 * @author： wjun_java@163.com
 * @date： 2021/4/21
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class ProcessExtensionsRegister extends ExtensionsRegister {

    AnnotationAwareAspectJAutoProxyCreator annotationAwareAspectJAutoProxyCreator;

    public ProcessExtensionsRegister(FastcmsPluginManager pluginManger) {
        super(pluginManger);
        annotationAwareAspectJAutoProxyCreator = (AnnotationAwareAspectJAutoProxyCreator) getBean(AnnotationAwareAspectJAutoProxyCreator.class);
    }

    @Override
    public void registry(String pluginId) throws Exception {

        Set<String> extensionClassNames = getExtensionClassNames(pluginId);
        extensionClassNames.forEach(item -> {
            try {
                Class<?> pluginExtensionClass = getPluginExtensionClass(pluginId, item);
                if (IService.class.isAssignableFrom(pluginExtensionClass)) {
                    annotationAwareAspectJAutoProxyCreator.setBeanClassLoader(getPlugin(pluginId).getPluginClassLoader());
                    String simpleName = pluginExtensionClass.getName();
                    Object o = annotationAwareAspectJAutoProxyCreator.postProcessAfterInitialization(getBean(pluginExtensionClass), simpleName);
                    getBeanFactory().destroySingleton(simpleName);
                    getBeanFactory().registerSingleton(simpleName, o);
                }

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e.getMessage());
            } finally {
                annotationAwareAspectJAutoProxyCreator.setBeanClassLoader(ClassUtils.getDefaultClassLoader());
            }
        });

    }

    @Override
    public void unRegistry(String pluginId) throws Exception {

    }

}
