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
package com.fastcms.web.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author： wjun_java@163.com
 * @date： 2021/10/23
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component
public class JwtTokenManager {

	private static final String AUTHORITIES_KEY = "auth";

	@Autowired
	private AuthConfigs authConfigs;

	/**
	 * Create token.
	 *
	 * @param authentication auth info
	 * @return token
	 */
	public String createToken(Authentication authentication) {
		return createToken(authentication.getName());
	}

	/**
	 * Create token.
	 *
	 * @param userName auth info
	 * @return token
	 */
	public String createToken(String userName) {

		long now = System.currentTimeMillis();

		Date validity;
		validity = new Date(now + authConfigs.getTokenValidityInSeconds() * 1000L);

		Claims claims = Jwts.claims().setSubject(userName);
		return Jwts.builder().setClaims(claims).setExpiration(validity)
				.signWith(Keys.hmacShaKeyFor(authConfigs.getSecretKeyBytes()), SignatureAlgorithm.HS256).compact();
	}

	/**
	 * Get auth Info.
	 *
	 * @param token token
	 * @return auth info
	 */
	public Authentication getAuthentication(String token) {
		Claims claims = Jwts.parserBuilder().setSigningKey(authConfigs.getSecretKeyBytes()).build()
				.parseClaimsJws(token).getBody();

		List<GrantedAuthority> authorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList((String) claims.get(AUTHORITIES_KEY));

		User principal = new User(claims.getSubject(), "", authorities);
		return new UsernamePasswordAuthenticationToken(principal, "", authorities);
	}

	/**
	 * validate token.
	 *
	 * @param token token
	 */
	public void validateToken(String token) {
		Jwts.parserBuilder().setSigningKey(authConfigs.getSecretKeyBytes()).build().parseClaimsJws(token);
	}

}
