package com.fastcms.core.utils;

import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.utils.FileUtils;
import com.fastcms.utils.ConfigUtils;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.commons.lang.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @author： wjun_java@163.com
 * @date： 2021/2/17
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public abstract class AttachUtils {

    public static Integer getImageMaxSize() {
        String config = ConfigUtils.getConfig(FastcmsConstants.ATTACH_IMAGE_MAXSIZE);
        try {
            return Integer.parseInt(config);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static Integer getOtherMaxSize() {
        String config = ConfigUtils.getConfig(FastcmsConstants.ATTACH_OTHER_MAXSIZE);
        try {
            return Integer.parseInt(config);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static Boolean enableWaterMark() {
        String enableWaterMark = ConfigUtils.getConfig(FastcmsConstants.ATTACH_ENABLE_WATERMARK);
        try {
            return Boolean.parseBoolean(enableWaterMark);
        } catch (NumberFormatException e) {
            return false;
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
        Thumbnails.Builder<File> fileBuilder = Thumbnails.of(file)
                .scale(1)
                .watermark(Positions.BOTTOM_RIGHT, waterImage, 0.25f).outputQuality(0.8f);
        String filePath = DirUtils.getUploadDir() +  FileUtils.newFileName(file.getName());
        fileBuilder.toFile(filePath);
        return filePath;
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

}
