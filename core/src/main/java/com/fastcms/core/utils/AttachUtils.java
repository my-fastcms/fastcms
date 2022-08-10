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
package com.fastcms.core.utils;

import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.model.RestResultUtils;
import com.fastcms.common.utils.DirUtils;
import com.fastcms.common.utils.FileUtils;
import com.fastcms.core.attach.FileServerManager;
import com.fastcms.entity.Attachment;
import com.fastcms.service.IAttachmentService;
import com.fastcms.utils.ConfigUtils;
import com.fastcms.utils.PluginUtils;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author： wjun_java@163.com
 * @date： 2021/2/17
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public final class AttachUtils {

    private AttachUtils() {

    }

    static List<String> allowFileTypeList = new ArrayList<>();

    static {
        allowFileTypeList.add("image");
        allowFileTypeList.add("audio");
        allowFileTypeList.add("video");
        allowFileTypeList.add("zip");
        allowFileTypeList.add("office");
    }

    /**
     * 附件域名
     */
    public static final String ATTACH_FILE_DOMAIN = "file_domain";

    /**
     * 最大图片上传大小
     */
    public static final String ATTACH_IMAGE_MAXSIZE = "imageMaxSize";

    /**
     * 其他文件最大上传大小
     */
    public static final String ATTACH_OTHER_MAXSIZE = "otherMaxSize";

    /**
     * 是否开启图片水印
     */
    public static final String ATTACH_ENABLE_WATERMARK = "enableWatermark";

    /**
     * 水印位置
     */
    public static final String ATTACH_WATERMARK_POS = "waterMarkPos";

    /**
     * 水印透明度
     */
    public static final String ATTACH_DIAPHANEITY = "diaphaneity";

    /**
     * 水印图片
     */
    public static final String ATTACH_WATERMARK_FILE = "waterMarkFile";

    /**
     * 水印文字
     */
    public static final String ATTACH_WATERMARK_TXT = "waterMarkTxt";

    public static Boolean isAllowFileType(String fileType) {
        return allowFileTypeList.contains(fileType);
    }

    public static String getAttachFileDomain() {
        String config = ConfigUtils.getConfig(ATTACH_FILE_DOMAIN);
        return StringUtils.isBlank(config) ? ConfigUtils.getConfig(FastcmsConstants.WEBSITE_DOMAIN) : config;
    }

    public static Integer getImageMaxSize() {
        String config = ConfigUtils.getConfig(ATTACH_IMAGE_MAXSIZE);
        try {
            return Integer.parseInt(config);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static Integer getOtherMaxSize() {
        String config = ConfigUtils.getConfig(ATTACH_OTHER_MAXSIZE);
        try {
            return Integer.parseInt(config);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static Boolean enableWaterMark() {
        String enableWaterMark = ConfigUtils.getConfig(ATTACH_ENABLE_WATERMARK);
        try {
            return Boolean.parseBoolean(enableWaterMark);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 获取水印透明度
     * @return
     */
    public static Integer getDiaphaneity() {
        String config = ConfigUtils.getConfig(ATTACH_DIAPHANEITY);
        try {
            return Integer.parseInt(config);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * 获取服务器ip地址
     * @return
     */
    public static String getInternetIp() {
        try{
            final String INTRANET_IP = InetAddress.getLocalHost().getHostAddress();
            Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
            InetAddress ip;
            Enumeration<InetAddress> addrs;
            while (networks.hasMoreElements()) {
                addrs = networks.nextElement().getInetAddresses();
                while (addrs.hasMoreElements()) {
                    ip = addrs.nextElement();
                    if (ip != null
                            && ip instanceof Inet4Address
                            && ip.isSiteLocalAddress()
                            && !ip.getHostAddress().equals(INTRANET_IP)) {
                        return ip.getHostAddress();
                    }
                }
            }
            // 如果没有外网IP，就返回内网IP
            return INTRANET_IP;
        } catch(Exception e) {
            return "127.0.0.1";
        }
    }

    /**
     * 给图片添加水印图片
     * @param file
     * @param waterFile
     * @return
     * @throws IOException
     */
    public static final String addFileWaterMark(File file, File waterFile) throws IOException {
        return addWaterMark(file, ImageIO.read(waterFile));
    }

    /**
     * 给图片添加文字水印
     * @param file
     * @param text
     * @return
     * @throws IOException
     */
    public static final String addTextWaterMark(File file, String text) throws IOException {
        return addWaterMark(file, handleTextWaterMark(text));
    }

    /**
     * 添加水印
     * @param file
     * @param waterImage
     * @return
     * @throws IOException
     */
    public static final String addWaterMark(File file, BufferedImage waterImage) throws IOException {

        String config = ConfigUtils.getConfig(ATTACH_WATERMARK_POS);
        Positions positions = Positions.BOTTOM_RIGHT;
        switch (config) {
            case "leftup":
                positions = Positions.TOP_LEFT;
                break;
            case "rightup":
                positions = Positions.TOP_RIGHT;
                break;
            case "middle":
                positions = Positions.CENTER;
                break;
            case "leftdown":
                positions = Positions.BOTTOM_LEFT;
                break;
            case "rightdown":
                positions = Positions.BOTTOM_RIGHT;
                break;
        }
        Thumbnails.Builder<File> fileBuilder = Thumbnails.of(file)
                .scale(1)
                .watermark(positions, waterImage, (float) getDiaphaneity()/100).outputQuality(0.8f);
        String filePath = FileUtils.newFileName(file.getName());
        fileBuilder.toFile(DirUtils.getUploadDir() + filePath);
        return filePath.replace("\\", "/");
    }

    /**
     * 转换文字为图片流
     * @param waterText
     * @return
     */
    private static BufferedImage handleTextWaterMark(String waterText) {

        final Font font = new Font("宋体",Font.PLAIN,20);

        BufferedImage image = new BufferedImage(100, 150, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        image = g.getDeviceConfiguration().createCompatibleImage(100, 150, Transparency.TRANSLUCENT);

        int y = 0;
        int divider30 = 30;

        g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.red);
        g.setFont(font);

        if (StringUtils.isNotBlank(waterText)) {
            g.drawString(waterText, 5, y += divider30);
        }

        g.dispose();
        return image;
    }

    /**
     * 上传附件
     * @param files
     * @param attachmentService
     * @return
     */
    public static Object upload(MultipartFile[] files, IAttachmentService attachmentService) {
        if(files == null || files.length <= 0) {
            return RestResultUtils.failed("请选择上传文件");
        }

        List<String> errorFiles = new ArrayList<>();

        List<Attachment> attachmentList = new ArrayList<>();
        for(MultipartFile file : files) {
            String newFilePath = FileUtils.newFileName(file.getOriginalFilename());
            File uploadFile = new File(DirUtils.getUploadDir(), newFilePath);

            if (!isAllowFileType(Attachment.AttachType.getValue(FileUtils.getSuffix(file.getOriginalFilename())))) {
                errorFiles.add("[" +file.getOriginalFilename() + "]文件类型不允许");
                continue;
            }

            if(AttachUtils.getImageMaxSize() > 0) {
                long fileSize = uploadFile.length(); //文件大小超过限制大小不上传
                if(fileSize > 1024 * 1024 * AttachUtils.getImageMaxSize()) {
                    uploadFile.delete();
                    errorFiles.add(file.getOriginalFilename());
                    continue;
                }
            }

            try {
                if (!uploadFile.getParentFile().exists()) {
                    uploadFile.getParentFile().mkdirs();
                }
                file.transferTo(uploadFile);
                Attachment attachment = new Attachment();
                attachment.setFileName(file.getOriginalFilename());
                attachment.setFilePath(newFilePath.replace("\\", "/"));
                attachment.setFileType(Attachment.AttachType.getValue(FileUtils.getSuffix(file.getOriginalFilename())));
                attachmentList.add(attachment);
            } catch (IOException e) {
                e.printStackTrace();
                if(uploadFile != null) {
                    uploadFile.delete();
                }
                errorFiles.add(file.getOriginalFilename());
            }
        }

        if(!attachmentList.isEmpty()) {
            attachmentService.saveBatch(attachmentList);
        }
        Map<String, String> result = new HashMap<>();
        result.put("urls", attachmentList.stream().map(Attachment::getPath).collect(Collectors.joining()));

        if(!attachmentList.isEmpty()) {
            List<FileServerManager> extensions = PluginUtils.getExtensions(FileServerManager.class);
            attachmentList.forEach(item -> {
                for (FileServerManager extension : extensions) {
                    try {
                        extension.uploadFile(item);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        return errorFiles.isEmpty() ?
                RestResultUtils.success(result) :
                RestResultUtils.failed(errorFiles.stream().collect(Collectors.joining(",")).concat("\n"));
    }

    public static Object deleteAttachment(Attachment attachment, IAttachmentService attachmentService) {
        if(attachmentService.removeById(attachment.getId())) {
            //删除文件
            File file = new File(DirUtils.getUploadDir() + attachment.getFilePath());
            if(file.exists() && file.isFile()) {
                file.delete();

                List<FileServerManager> extensions = PluginUtils.getExtensions(FileServerManager.class);
                for (FileServerManager extension : extensions) {
                    try {
                        extension.deleteFile(attachment);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return RestResultUtils.success();
    }

}
