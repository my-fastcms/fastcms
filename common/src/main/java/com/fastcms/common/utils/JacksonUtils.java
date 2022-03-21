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

import com.fastcms.common.exception.FastcmsDeserializationException;
import com.fastcms.common.exception.FastcmsSerializationException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * @author： wjun_java@163.com
 * @date： 2022/3/21
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class JacksonUtils {

	static ObjectMapper mapper = new ObjectMapper();

	static {
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	}

	/**
	 * Object to json string.
	 *
	 * @param obj obj
	 * @return json string
	 * @throws FastcmsSerializationException if transfer failed
	 */
	public static String toJson(Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new FastcmsSerializationException(obj.getClass(), e);
		}
	}

	/**
	 * Object to json string byte array.
	 *
	 * @param obj obj
	 * @return json string byte array
	 * @throws FastcmsSerializationException if transfer failed
	 */
	public static byte[] toJsonBytes(Object obj) {
		try {
			return ByteUtils.toBytes(mapper.writeValueAsString(obj));
		} catch (JsonProcessingException e) {
			throw new FastcmsSerializationException(obj.getClass(), e);
		}
	}

	/**
	 * Json string deserialize to Object.
	 *
	 * @param json json string
	 * @param cls  class of object
	 * @param <T>  General type
	 * @return object
	 * @throws FastcmsSerializationException if deserialize failed
	 */
	public static <T> T toObj(byte[] json, Class<T> cls) {
		try {
			return toObj(StrUtils.newStringForUtf8(json), cls);
		} catch (Exception e) {
			throw new FastcmsDeserializationException(cls, e);
		}
	}

	/**
	 * Json string deserialize to Object.
	 *
	 * @param json json string
	 * @param cls  {@link Type} of object
	 * @param <T>  General type
	 * @return object
	 * @throws FastcmsDeserializationException if deserialize failed
	 */
	public static <T> T toObj(byte[] json, Type cls) {
		try {
			return toObj(StrUtils.newStringForUtf8(json), cls);
		} catch (Exception e) {
			throw new FastcmsDeserializationException(e);
		}
	}

	/**
	 * Json string deserialize to Object.
	 *
	 * @param inputStream json string input stream
	 * @param cls         class of object
	 * @param <T>         General type
	 * @return object
	 * @throws FastcmsDeserializationException if deserialize failed
	 */
	public static <T> T toObj(InputStream inputStream, Class<T> cls) {
		try {
			return mapper.readValue(inputStream, cls);
		} catch (IOException e) {
			throw new FastcmsDeserializationException(e);
		}
	}

	/**
	 * Json string deserialize to Object.
	 *
	 * @param json          json string byte array
	 * @param typeReference {@link TypeReference} of object
	 * @param <T>           General type
	 * @return object
	 * @throws FastcmsDeserializationException if deserialize failed
	 */
	public static <T> T toObj(byte[] json, TypeReference<T> typeReference) {
		try {
			return toObj(StrUtils.newStringForUtf8(json), typeReference);
		} catch (Exception e) {
			throw new FastcmsDeserializationException(e);
		}
	}

	/**
	 * Json string deserialize to Object.
	 *
	 * @param json json string
	 * @param cls  class of object
	 * @param <T>  General type
	 * @return object
	 * @throws FastcmsDeserializationException if deserialize failed
	 */
	public static <T> T toObj(String json, Class<T> cls) {
		try {
			return mapper.readValue(json, cls);
		} catch (IOException e) {
			throw new FastcmsDeserializationException(cls, e);
		}
	}

	/**
	 * Json string deserialize to Object.
	 *
	 * @param json json string
	 * @param type {@link Type} of object
	 * @param <T>  General type
	 * @return object
	 * @throws FastcmsDeserializationException if deserialize failed
	 */
	public static <T> T toObj(String json, Type type) {
		try {
			return mapper.readValue(json, mapper.constructType(type));
		} catch (IOException e) {
			throw new FastcmsDeserializationException(e);
		}
	}

	/**
	 * Json string deserialize to Object.
	 *
	 * @param json          json string
	 * @param typeReference {@link TypeReference} of object
	 * @param <T>           General type
	 * @return object
	 * @throws FastcmsDeserializationException if deserialize failed
	 */
	public static <T> T toObj(String json, TypeReference<T> typeReference) {
		try {
			return mapper.readValue(json, typeReference);
		} catch (IOException e) {
			throw new FastcmsDeserializationException(typeReference.getClass(), e);
		}
	}

	/**
	 * Json string deserialize to Object.
	 *
	 * @param inputStream json string input stream
	 * @param type        {@link Type} of object
	 * @param <T>         General type
	 * @return object
	 * @throws FastcmsDeserializationException if deserialize failed
	 */
	public static <T> T toObj(InputStream inputStream, Type type) {
		try {
			return mapper.readValue(inputStream, mapper.constructType(type));
		} catch (IOException e) {
			throw new FastcmsDeserializationException(type, e);
		}
	}

	/**
	 * Json string deserialize to Jackson {@link JsonNode}.
	 *
	 * @param json json string
	 * @return {@link JsonNode}
	 * @throws FastcmsDeserializationException if deserialize failed
	 */
	public static JsonNode toObj(String json) {
		try {
			return mapper.readTree(json);
		} catch (IOException e) {
			throw new FastcmsDeserializationException(e);
		}
	}

	/**
	 * Register sub type for child class.
	 *
	 * @param clz  child class
	 * @param type type name of child class
	 */
	public static void registerSubtype(Class<?> clz, String type) {
		mapper.registerSubtypes(new NamedType(clz, type));
	}

	/**
	 * Create a new empty Jackson {@link ObjectNode}.
	 *
	 * @return {@link ObjectNode}
	 */
	public static ObjectNode createEmptyJsonNode() {
		return new ObjectNode(mapper.getNodeFactory());
	}

	/**
	 * Create a new empty Jackson {@link ArrayNode}.
	 *
	 * @return {@link ArrayNode}
	 */
	public static ArrayNode createEmptyArrayNode() {
		return new ArrayNode(mapper.getNodeFactory());
	}

	/**
	 * Parse object to Jackson {@link JsonNode}.
	 *
	 * @param obj object
	 * @return {@link JsonNode}
	 */
	public static JsonNode transferToJsonNode(Object obj) {
		return mapper.valueToTree(obj);
	}

	/**
	 * construct java type -> Jackson Java Type.
	 *
	 * @param type java type
	 * @return JavaType {@link JavaType}
	 */
	public static JavaType constructJavaType(Type type) {
		return mapper.constructType(type);
	}

}
