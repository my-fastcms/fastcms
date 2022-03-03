package com.fastcms.common.utils;

/**
 * @author： wjun_java@163.com
 * @date： 2021/2/17
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public abstract class DirUtils {

    /**
     * 附件上传目录 目录以 "/" 结尾
     */
    static String uploadDir;

    /**
     * 插件目录 目录以 "/" 结尾
     */
    static String pluginDir;

    /**
     * 模板目录
     */
    static String templateDir;

    /**
     * lucene目录
     */
    static String luceneDir;

    public static void injectUploadDir(String uploadDir) {
        DirUtils.uploadDir = uploadDir;
    }

    public static void injectPluginDir(String pluginDir) {
        DirUtils.pluginDir = pluginDir;
    }

    public static void injectTemplateDir(String templateDir) {
        DirUtils.templateDir = templateDir;
    }

    public static void injectLuceneDir(String luceneDir) {
        DirUtils.luceneDir = luceneDir;
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

    public static String getLuceneDir() {
        return luceneDir;
    }

}
