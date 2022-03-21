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

import java.lang.reflect.Type;

import static com.fastcms.common.constants.FastcmsConstants.Exception.DESERIALIZE_ERROR_CODE;

/**
 * @author： wjun_java@163.com
 * @date： 2022/3/21
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class FastcmsDeserializationException extends FastcmsRuntimeException {

	private static final long serialVersionUID = -2742350751684273728L;

	private static final String DEFAULT_MSG = "Fastcms deserialize failed. ";

	private static final String MSG_FOR_SPECIFIED_CLASS = "Fastcms deserialize for class [%s] failed. ";

	private Class<?> targetClass;

	public FastcmsDeserializationException() {
		super(DESERIALIZE_ERROR_CODE);
	}

	public FastcmsDeserializationException(Class<?> targetClass) {
		super(DESERIALIZE_ERROR_CODE, String.format(MSG_FOR_SPECIFIED_CLASS, targetClass.getName()));
		this.targetClass = targetClass;
	}

	public FastcmsDeserializationException(Type targetType) {
		super(DESERIALIZE_ERROR_CODE, String.format(MSG_FOR_SPECIFIED_CLASS, targetType.toString()));
	}

	public FastcmsDeserializationException(Throwable throwable) {
		super(DESERIALIZE_ERROR_CODE, DEFAULT_MSG, throwable);
	}

	public FastcmsDeserializationException(Class<?> targetClass, Throwable throwable) {
		super(DESERIALIZE_ERROR_CODE, String.format(MSG_FOR_SPECIFIED_CLASS, targetClass.getName()), throwable);
		this.targetClass = targetClass;
	}

	public FastcmsDeserializationException(Type targetType, Throwable throwable) {
		super(DESERIALIZE_ERROR_CODE, String.format(MSG_FOR_SPECIFIED_CLASS, targetType.toString()), throwable);
	}

	public Class<?> getTargetClass() {
		return targetClass;
	}

}
