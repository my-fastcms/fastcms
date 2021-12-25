package com.fastcms.plugin.register;

import com.fastcms.plugin.FastcmsPluginManager;
import com.fastcms.plugin.InterceptPath;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;
import org.springframework.web.servlet.handler.MappedInterceptor;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

/**
 * wjun_java@163.com
 * 注册插件拦截器
 */
public class InterceptorRegister extends AbstractPluginRegister {

    AbstractHandlerMapping handlerMapping;
    List<HandlerInterceptor> handlerInterceptorList;

    public InterceptorRegister(FastcmsPluginManager pluginManger) {
        super(pluginManger);
        handlerMapping = pluginManger.getApplicationContext().getBean(AbstractHandlerMapping.class);
        Field adaptedInterceptorsField = ReflectionUtils.findField(handlerMapping.getClass(), "adaptedInterceptors", List.class);
        adaptedInterceptorsField.setAccessible(true);
        try {
            handlerInterceptorList = (List<HandlerInterceptor>) adaptedInterceptorsField.get(handlerMapping);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
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
