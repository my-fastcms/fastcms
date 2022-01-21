package com.fastcms.utils;

import java.lang.reflect.Field;

/**
 * wjun_java@163.com
 * 给实体类动态添加属性
 */
public class ReflectUtil {

    /**
     * get filed value of  obj.
     *
     * @param obj       obj.
     * @param fieldName file name to get value.
     * @return field value.
     */
    public static Object getFieldValue(Object obj, String fieldName) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * get filed value of  obj.
     *
     * @param obj       obj.
     * @param fieldName file name to get value.
     * @return field value.
     */
    public static Object getFieldValue(Object obj, String fieldName, Object defaultValue) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            return defaultValue;
        }
    }

}
