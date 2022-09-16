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

/**
 * @author： wjun_java@163.com
 * @date： 2021/6/4
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public abstract class ImageUtils {

	private final static String[] imgExts = new String[] {"jpg", "jpeg", "png", "bmp"};

	public static String getExtName(String fileName) {
		int index = fileName.lastIndexOf('.');
		if (index != -1 && (index + 1) < fileName.length()) {
			return fileName.substring(index + 1);
		} else {
			return null;
		}
	}


	/**
	 * 过文件扩展名，判断是否为支持的图像文件
	 *
	 * @param fileName
	 * @return 是图片则返回 true，否则返回 false
	 */
	public static boolean isImageExtName(String fileName) {
		if (StringUtils.isBlank(fileName)) {
			return false;
		}
		fileName = fileName.trim().toLowerCase();
		String ext = getExtName(fileName);
		if (ext != null) {
			for (String s : imgExts) {
				if (s.equals(ext)) {
					return true;
				}
			}
		}
		return false;
	}

	public static final boolean notImageExtName(String fileName) {
		return !isImageExtName(fileName);
	}

}
