package com.fastcms.core.utils;

/**
 * @author： wjun_java@163.com
 * @date： 2021/2/17
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public abstract class DirUtils {

    static String uploadDir;
    static String pluginDir;
    static String templateDir;

    public static void injectUploadDir(String uploadDir) {
        DirUtils.uploadDir = uploadDir;
    }

    public static void injectPluginDir(String pluginDir) {
        DirUtils.pluginDir = pluginDir;
    }

    public static void injectTemplateDir(String templateDir) {
        DirUtils.templateDir = pluginDir;
    }

    public static String getUploadDir() {
        return uploadDir;
    }

    public static String getPluginDir() {
        return pluginDir;
    }

    public static String getTemplateDir() {
        return templateDir;
    }

}
