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

import com.fastcms.common.constants.FastcmsConstants;
import org.apache.commons.lang.StringUtils;

import java.nio.charset.Charset;

/**
 * @author： wjun_java@163.com
 * @date： 2022/3/21
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class ByteUtils {

	public static final byte[] EMPTY = new byte[0];

	/**
	 * String to byte array.
	 *
	 * @param input input string
	 * @return byte array of string
	 */
	public static byte[] toBytes(String input) {
		if (input == null) {
			return EMPTY;
		}
		return input.getBytes(Charset.forName(FastcmsConstants.ENCODE));
	}

	/**
	 * Object to byte array.
	 *
	 * @param obj input obj
	 * @return byte array of object
	 */
	public static byte[] toBytes(Object obj) {
		if (obj == null) {
			return EMPTY;
		}
		return toBytes(String.valueOf(obj));
	}

	/**
	 * Byte array to string.
	 *
	 * @param bytes byte array
	 * @return string
	 */
	public static String toString(byte[] bytes) {
		if (bytes == null) {
			return StringUtils.EMPTY;
		}
		return new String(bytes, Charset.forName(FastcmsConstants.ENCODE));
	}

	public static boolean isEmpty(byte[] data) {
		return data == null || data.length == 0;
	}

	public static boolean isNotEmpty(byte[] data) {
		return !isEmpty(data);
	}

}
