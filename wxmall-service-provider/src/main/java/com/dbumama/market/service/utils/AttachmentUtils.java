/**
 * Copyright (c) 广州点步信息科技有限公司 2016-2017, wjun_java@163.com.
 *
 * Licensed under the GNU Lesser General Public License (LGPL) ,Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.gnu.org/licenses/lgpl-3.0.txt
 *	    http://www.dbumama.com
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dbumama.market.service.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.kit.PathKit;

public class AttachmentUtils {

	static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	static Object lockObj = new Object();

	/**
	 * @param uploadFile
	 * @return new file relative path
	 */
	public static String moveFile(File file) {
		if (file == null)
			return null;

		if (!file.exists()) {
			return null;
		}

		String webRoot = PathKit.getWebRootPath();
		String uuid= "";
		synchronized (lockObj) {
			uuid = String.valueOf(new Date().getTime());
		}

		StringBuilder newFileName = new StringBuilder(webRoot).append(File.separator).append("upload/image")
				.append(File.separator).append(dateFormat.format(new Date())).append(File.separator).append(uuid)
				.append(FileUtils.getSuffix(file.getName()));

		File newfile = new File(newFileName.toString());

		if (!newfile.getParentFile().exists()) {
			newfile.getParentFile().mkdirs();
		}

		file.renameTo(newfile);

		return FileUtils.removePrefix(newfile.getAbsolutePath(), webRoot);
	}

	static List<String> imageSuffix = new ArrayList<String>();

	static {
		imageSuffix.add(".jpg");
		imageSuffix.add(".jpeg");
		imageSuffix.add(".png");
		imageSuffix.add(".bmp");
		imageSuffix.add(".gif");
	}

	public static boolean isImage(String path) {
		String sufffix = FileUtils.getSuffix(path);
		if (StringUtils.isNotBlank(sufffix))
			return imageSuffix.contains(sufffix.toLowerCase());
		return false;
	}

	public static void main(String[] args) {
		System.out.println(FileUtils.getSuffix("xxx.jpg"));
	}

}
