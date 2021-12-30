package com.fastcms.core.attach;

import com.fastcms.common.exception.FastcmsException;
import org.pf4j.ExtensionPoint;

import java.io.File;

/**
 * wjun_java@163.com
 * 文件服务器管理接口
 */
public interface FileServerManager extends ExtensionPoint {

    /**
     * 上传文件到文件服务器
     * @param file
     */
    void uploadFile(File file) throws FastcmsException;

    /**
     * 删除文件服务器上面的文件
     * @param filePath  文件相对路径
     */
    void deleteFile(String filePath) throws FastcmsException;

    /**
     * 从文件服务器下载文件
     * @param filePath
     * @return
     */
    File downloadFile(String filePath) throws FastcmsException;

    /**
     * 是否启用文件服务器
     * @return
     */
    boolean isEnable();

}
