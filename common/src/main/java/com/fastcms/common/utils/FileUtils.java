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
package com.fastcms.common.utils;

import org.apache.commons.lang.StringUtils;
import org.zeroturnaround.zip.ZipUtil;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author： wjun_java@163.com
 * @date： 2021/4/3
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public abstract class FileUtils {

    static List<String> imageFileSuffix = new ArrayList<>();
    static List<String> notAllowFile = new ArrayList<>();

    static {

        imageFileSuffix.add(".jpg");
        imageFileSuffix.add(".jpeg");
        imageFileSuffix.add(".png");
        imageFileSuffix.add(".bmp");
        imageFileSuffix.add(".gif");
        imageFileSuffix.add(".webp");

        notAllowFile.add(".jsp");
        notAllowFile.add(".jspx");
        notAllowFile.add(".php");
        notAllowFile.add(".exe");
        notAllowFile.add(".sh");
        notAllowFile.add(".bat");
        notAllowFile.add(".jar");
        notAllowFile.add(".war");

    }

    public static boolean isImage(String fileName) {
        String suffix = getSuffix(fileName);
        if (StringUtils.isNotBlank(suffix)) {
            return imageFileSuffix.contains(suffix.toLowerCase());
        }
        return false;
    }

    public static boolean isNotAllowFile(String fileName) {
        String suffix = getSuffix(fileName);
        if(StringUtils.isNotBlank(suffix)) {
            return notAllowFile.contains(suffix.toLowerCase());
        }
        return false;
    }

    public static String getSuffix(String fileName) {
        if (fileName != null && fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf("."));
        }
        return "";
    }

    public static String removePrefix(String src, String prefix) {
        if (src != null && src.startsWith(prefix)) {
            return src.substring(prefix.length());
        }
        return src.startsWith("/") ? src : "/".concat(src);
    }

    public static String newFileName(String fileName) {
        String uuid = StrUtils.uuid();
        StringBuilder newFilePath = new StringBuilder()
                .append(File.separator).append("attachment")
                .append(File.separator).append(new SimpleDateFormat("yyyyMMdd").format(new Date()))
                .append(File.separator).append(uuid)
                .append(fileName.substring(fileName.lastIndexOf(".")));
        return newFilePath.toString();
    }

    private static final String[] htmlChars = {"&", "<", ">", "'", "\""};
    private static final String[] escapeChars = {"&amp;", "&lt;", "&gt;", "&#39;", "&quot;"};

    public static String escapeHtml(String content) {
        return StringUtils.isBlank(content) ? content : StringUtils.replaceEach(unEscapeHtml(content), htmlChars, escapeChars);
    }

    public static String unEscapeHtml(String content) {
        return StringUtils.isBlank(content) ? content : StringUtils.replaceEach(content, escapeChars, htmlChars);
    }

    public static void writeString(File file, String string) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file, false);
            fos.write(string.getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(fos);
        }
    }

    public static void close(Closeable... closeable) {
        if (closeable != null || closeable.length != 0) {
            for (Closeable c : closeable) {
                if (c != null) {
                    try {
                        c.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void unzip(Path path, String destDir) throws IOException {

        try {
            ZipUtil.unpack(path.toFile(), new File(destDir));
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }

    }

    public static void unzip(String zipFileName, String destDir) throws IOException {
        unzip(Paths.get(zipFileName), destDir);
    }

}
