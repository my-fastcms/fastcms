package com.fastcms.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.stream.Collectors;

public class ReflectUtils {

    /**
     * 获得一个类中所有字段列表，直接反射获取，无缓存
     * @param beanClass
     * @return
     * @throws SecurityException
     */
    public static Field[] getFields(Class<?> beanClass) throws SecurityException {
        return getFieldsDirectly(beanClass, true);
    }

    /**
     * 获得一个类中所有字段列表，直接反射获取，无缓存
     *
     * @param beanClass           类
     * @param withSuperClassFieds 是否包括父类的字段列表
     * @return 字段列表
     * @throws SecurityException 安全检查异常
     */
    public static Field[] getFieldsDirectly(Class<?> beanClass, boolean withSuperClassFieds) throws SecurityException {

        Field[] allFields = null;
        Class<?> searchType = beanClass;
        Field[] declaredFields;
        while (searchType != null) {
            declaredFields = searchType.getDeclaredFields();
            if (null == allFields) {
                allFields = declaredFields;
            } else {
                allFields = ArrayUtils.append(allFields, declaredFields);
            }
            searchType = withSuperClassFieds ? searchType.getSuperclass() : null;
        }

        return allFields;
    }

    /**
     * 获取字段值
     * @param field
     * @return
     */
    public static String getFieldValue(Object o, Field field) {
        field.setAccessible(true);
        Object obj;
        try {
            obj = field.get(o);
            if(obj != null) {
                int mod = field.getModifiers();
                if(!"serialVersionUID".equals(field.getName()) && !Modifier.isTransient(mod)) {
                    if (obj instanceof List == true) {
                        List v = (List) obj;
                        obj = v.stream().collect(Collectors.joining(","));
                    }
                    return String.valueOf(obj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
