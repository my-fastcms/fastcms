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
package com.fastcms.common.exception;

import static com.fastcms.common.constants.FastcmsConstants.Exception.SERIALIZE_ERROR_CODE;

/**
 * @author： wjun_java@163.com
 * @date： 2022/3/21
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class FastcmsSerializationException extends FastcmsRuntimeException {

	private static final long serialVersionUID = -4308536346316915612L;

	private static final String DEFAULT_MSG = "Fastcms serialize failed. ";

	private static final String MSG_FOR_SPECIFIED_CLASS = "Fastcms serialize for class [%s] failed. ";

	private Class<?> serializedClass;

	public FastcmsSerializationException() {
		super(SERIALIZE_ERROR_CODE);
	}

	public FastcmsSerializationException(Class<?> serializedClass) {
		super(SERIALIZE_ERROR_CODE, String.format(MSG_FOR_SPECIFIED_CLASS, serializedClass.getName()));
		this.serializedClass = serializedClass;
	}

	public FastcmsSerializationException(Throwable throwable) {
		super(SERIALIZE_ERROR_CODE, DEFAULT_MSG, throwable);
	}

	public FastcmsSerializationException(Class<?> serializedClass, Throwable throwable) {
		super(SERIALIZE_ERROR_CODE, String.format(MSG_FOR_SPECIFIED_CLASS, serializedClass.getName()), throwable);
		this.serializedClass = serializedClass;
	}

	public Class<?> getSerializedClass() {
		return serializedClass;
	}

}
