package com.fastcms.utils;
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

import org.springframework.lang.Nullable;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 给实体类动态添加属性
 * @author： wjun_java@163.com
 * @date： 2022/4/9
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class ReflectUtil {

    private static final Class<?>[] EMPTY_CLASS_ARRAY = new Class<?>[0];

    private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

    /**
     * get filed value of  obj.
     *
     * @param target       target.
     * @param fieldName file name to get value.
     * @return field value.
     */
    public static Object getFieldValue(@Nullable Object target, String fieldName) {
        return getFieldValue(target, fieldName, null);
    }

    /**
     * get filed value of  obj.
     *
     * @param target       obj.
     * @param fieldName file name to get value.
     * @return field value.
     */
    public static Object getFieldValue(@Nullable Object target, String fieldName, Object defaultValue) {
        final Field field = ReflectionUtils.findField(target.getClass(), fieldName);
        field.setAccessible(true);
        try {
            return field.get(target);
        } catch (IllegalAccessException e) {
            return defaultValue;
        }
    }

    /**
     * invoke method
     * 通过反射调用类的protected，private方法
     * @return
     */
    public static Object invokeMethod(@Nullable Object target, String methodName) {
        return invokeMethod(target, methodName, EMPTY_OBJECT_ARRAY);
    }

    /**
     * invoke method
     * 通过反射调用类的protected，private方法
     * @return
     */
    public static Object invokeMethod(@Nullable Object target, String methodName, @Nullable Object... args) {
        Method method = ReflectionUtils.findMethod(target.getClass(), methodName);
        method.setAccessible(true);
        return ReflectionUtils.invokeMethod(method, target, args);
    }

    /**
     * invoke method
     * 通过反射调用类的protected，private方法
     * @return
     */
    public static Object invokeMethod(@Nullable Object target, Method method, @Nullable Object... args) {
        method.setAccessible(true);
        return ReflectionUtils.invokeMethod(method, target, args);
    }

}
