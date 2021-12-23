package com.fastcms.plugin.register;

import com.fastcms.plugin.FastcmsPluginManager;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

/**
 * wjun_java@163.com
 * 注册插件controller
 */
public class ControllerRegister extends AbstractPluginRegister {

    RequestMappingHandlerMapping requestMappingHandlerMapping;
    Method getMappingForMethod;

    public ControllerRegister(FastcmsPluginManager pluginManger) {
        super(pluginManger);
        requestMappingHandlerMapping = pluginManger.getApplicationContext().getBean(RequestMappingHandlerMapping.class);
        getMappingForMethod = ReflectionUtils.findMethod(RequestMappingHandlerMapping.class, "getMappingForMethod", Method.class, Class.class);
        getMappingForMethod.setAccessible(true);
    }

    @Override
    public void registry(String pluginId) throws Exception {

        for (Class<?> aClass : getPluginClasses(pluginId)) {
            Controller annotation = aClass.getAnnotation(Controller.class);
            if(annotation != null) {
                Object bean = pluginManger.getExtensionFactory().create(aClass);
                Method[] methods = aClass.getMethods();
                for (Method method : methods) {
                    if (method.getAnnotation(RequestMapping.class) != null
                            || method.getAnnotation(GetMapping.class) != null
                            || method.getAnnotation(PostMapping.class) != null) {
                        RequestMappingInfo requestMappingInfo = (RequestMappingInfo) getMappingForMethod.invoke(requestMappingHandlerMapping, method, aClass);
                        requestMappingHandlerMapping.registerMapping(requestMappingInfo, bean, method);
                    }
                }
            }
        }

    }

    @Override
    public void unRegistry(String pluginId) throws Exception {

    }
}
