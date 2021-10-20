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
package com.fastcms.core.jwt;

import com.alibaba.fastjson.JSON;
import com.fastcms.common.constants.FastcmsConstants;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author： wjun_java@163.com
 * @date： 2021/6/7
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public abstract class JwtUtils {

	public static String createJwtToken(String secret, Long userId, String openId) {

		SecretKey secretKey = generalKey(secret);

		Map map = new HashMap();
		map.put(FastcmsConstants.USER_ID, userId);
		map.put(FastcmsConstants.OPEN_ID, openId);
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		map.put("isuuedAt", nowMillis);
		String subject = JSON.toJSONString(map);


		JwtBuilder builder = Jwts.builder()
				.setIssuedAt(now)
				.setSubject(subject)
				.signWith(signatureAlgorithm, secretKey);

//		builder.setExpiration(now);

		return builder.compact();
	}

	private static SecretKey generalKey(String jwtSecret) {
		byte[] encodedKey = DatatypeConverter.parseBase64Binary(jwtSecret);
		SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
		return key;
	}

}
