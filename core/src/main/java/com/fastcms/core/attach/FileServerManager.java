package com.fastcms.core.attach;

import org.pf4j.ExtensionPoint;

import java.io.File;

/**
 * wjun_java@163.com
 * 文件服务器管理接口
 * 阿里oss，Minio等文件服务器扩展接口
 */
public interface FileServerManager extends ExtensionPoint {

    /**
     * 上传文件到文件服务器
     * @param file
     */
    void uploadFile(File file) throws Exception;

    /**
     * 删除文件服务器上面的文件
     * @param filePath  文件相对路径
     */
    void deleteFile(String filePath) throws Exception;

    /**
     * 从文件服务器下载文件
     * @param filePath
     * @return
     */
    File downloadFile(String filePath) throws Exception;

    /**
     * 是否启用文件服务器
     * @return
     */
    boolean isEnable();

}
