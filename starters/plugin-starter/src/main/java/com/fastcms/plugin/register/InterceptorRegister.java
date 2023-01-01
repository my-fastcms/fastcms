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

import com.fastcms.plugin.FastcmsPluginManager;
import com.fastcms.plugin.InterceptPath;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.MappedInterceptor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

/**
 * 注册插件拦截器
 * @author： wjun_java@163.com
 * @date： 2021/10/24
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class InterceptorRegister extends AbstractPluginRegister {

    RequestMappingHandlerMapping handlerMapping;
    List<HandlerInterceptor> handlerInterceptorList;

    public InterceptorRegister(FastcmsPluginManager pluginManger) {
        super(pluginManger);
        handlerMapping = pluginManger.getApplicationContext().getBean(RequestMappingHandlerMapping.class);
        Field adaptedInterceptorsField = ReflectionUtils.findField(handlerMapping.getClass(), "adaptedInterceptors", List.class);
        adaptedInterceptorsField.setAccessible(true);
        try {
            handlerInterceptorList = (List<HandlerInterceptor>) adaptedInterceptorsField.get(handlerMapping);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void registry(String pluginId) throws Exception {

        for (Class<?> aClass : getPluginClasses(pluginId)) {
            InterceptPath interceptPaths = aClass.getAnnotation(InterceptPath.class);
            if(interceptPaths != null && interceptPaths.value() != null) {
                MappedInterceptor mappedInterceptor = new MappedInterceptor(interceptPaths.value(), (HandlerInterceptor) pluginManger.getExtensionFactory().create(aClass));
                handlerInterceptorList.add(mappedInterceptor);
            }
        }

    }

    @Override
    public void unRegistry(String pluginId) throws Exception {

        for (Class<?> aClass : getPluginClasses(pluginId)) {
            InterceptPath interceptPaths = aClass.getAnnotation(InterceptPath.class);
            if(interceptPaths != null && interceptPaths.value() != null) {
                for (HandlerInterceptor handlerInterceptor : handlerInterceptorList) {
                    if(handlerInterceptor instanceof MappedInterceptor) {
                        MappedInterceptor mappedInterceptor = (MappedInterceptor) handlerInterceptor;
                        if(Objects.equals(pluginManger.getExtensionFactory().create(aClass), mappedInterceptor.getInterceptor())) {
                            handlerInterceptorList.remove(handlerInterceptor);
                            destroyBean(aClass);
                            break;
                        }
                    }
                }
            }
        }

    }

}
