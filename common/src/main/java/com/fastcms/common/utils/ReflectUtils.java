package com.fastcms.common.utils;

import java.lang.reflect.Field;

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

}
