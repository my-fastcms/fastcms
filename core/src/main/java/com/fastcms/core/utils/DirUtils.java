package com.fastcms.core.utils;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author： wjun_java@163.com
 * @date： 2021/2/17
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public abstract class DirUtils {

    final static String UPLOAD_DIR = "upload/";
    final static String PLUGIN_DIR = "plugins/";
    final static String TEMPLATE_DIR = "htmls/";

    static String uploadDir;
    static String pluginDir;
    static String templateDir;

    static {
        File path;
        try {
            path = new File(ResourceUtils.getURL(ResourceUtils.CLASSPATH_URL_PREFIX).getPath());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        if(!path.exists()) {
            path = new File(".");
        }

        File upload = new File(path.getAbsolutePath(), UPLOAD_DIR);
        if(!upload.exists()) upload.mkdirs();

        File plugins = new File(path.getAbsolutePath(), PLUGIN_DIR);
        if(!plugins.exists()) plugins.mkdirs();

        File htmls = new File(path.getAbsolutePath(), TEMPLATE_DIR);
        if(!htmls.exists()) htmls.mkdirs();

        uploadDir = path.getAbsolutePath() + File.separator + UPLOAD_DIR;
        pluginDir = path.getAbsolutePath() + File.separator + PLUGIN_DIR;
        templateDir = path.getAbsolutePath() + File.separator + TEMPLATE_DIR;
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
