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
import org.springframework.security.web.FilterChainProxy;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * wjun_java@163.com
 * 注册插件controller
 */
public class ControllerRegister extends AbstractPluginRegister {

    RequestMappingHandlerMapping requestMappingHandlerMapping;
    Method getMappingForMethod;
//    List<SecurityFilterChain> securityFilterChains;

    public ControllerRegister(FastcmsPluginManager pluginManger) {
        super(pluginManger);
        requestMappingHandlerMapping = pluginManger.getApplicationContext().getBean(RequestMappingHandlerMapping.class);
        getMappingForMethod = ReflectionUtils.findMethod(RequestMappingHandlerMapping.class, "getMappingForMethod", Method.class, Class.class);
        getMappingForMethod.setAccessible(true);

//        Field filterChains = ReflectionUtils.findField(FilterChainProxy.class, "filterChains");
//        securityFilterChains = (List<SecurityFilterChain>) ReflectionUtils.getField(filterChains, pluginManger.getApplicationContext().getBean(FilterChainProxy.class));
    }

    @Override
    public void registry(String pluginId) throws Exception {

        for (Class<?> aClass : getPluginClasses(pluginId)) {
            Controller annotation = aClass.getAnnotation(Controller.class);
            RestController restAnnotation = aClass.getAnnotation(RestController.class);
            if(annotation != null || restAnnotation != null) {
                Object bean = pluginManger.getExtensionFactory().create(aClass);
                Method[] methods = aClass.getMethods();
                for (Method method : methods) {
                    if (method.getAnnotation(RequestMapping.class) != null
                            || method.getAnnotation(GetMapping.class) != null
                            || method.getAnnotation(PostMapping.class) != null) {
                        RequestMappingInfo requestMappingInfo = (RequestMappingInfo) getMappingForMethod.invoke(requestMappingHandlerMapping, method, aClass);
                        requestMappingHandlerMapping.registerMapping(requestMappingInfo, bean, method);

//                        if (method.getAnnotation(PassFastcms.class) != null) {
//                            Set<PathPattern> patterns = requestMappingInfo.getPathPatternsCondition().getPatterns();
//                            if (CollectionUtils.isNotEmpty(patterns)) {
//                                String url = patterns.toArray()[0].toString();
//                                securityFilterChains.add(new DefaultSecurityFilterChain(new AntPathRequestMatcher(url), new Filter[0]));
//                            }
//                        }

                    }
                }
            }
        }

    }

    @Override
    public void unRegistry(String pluginId) throws Exception {
        for (RequestMappingInfo requestMappingInfo : getRequestMappingInfo(pluginId)) {
            requestMappingHandlerMapping.unregisterMapping(requestMappingInfo);
        }
    }

    List<RequestMappingInfo> getRequestMappingInfo(String pluginId) throws Exception {
        List<RequestMappingInfo> requestMappingInfoList = new ArrayList<>();
        for (Class<?> aClass : getPluginClasses(pluginId)) {
            Controller annotation = aClass.getAnnotation(Controller.class);
            RestController restAnnotation = aClass.getAnnotation(RestController.class);
            if (annotation != null || restAnnotation != null) {
                Method[] methods = aClass.getMethods();
                for (Method method : methods) {
                    RequestMappingInfo requestMappingInfo = (RequestMappingInfo) getMappingForMethod.invoke(requestMappingHandlerMapping, method, aClass);
                    requestMappingInfoList.add(requestMappingInfo);
                }
                destroyBean(aClass);
            }
        }
        return requestMappingInfoList;
    }

}
