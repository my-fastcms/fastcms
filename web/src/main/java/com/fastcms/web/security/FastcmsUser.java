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

import com.fastcms.common.utils.VersionUtils;

import java.io.Serializable;

/**
 * @author： wjun_java@163.com
 * @date： 2021/11/13
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class FastcmsUser implements Serializable {

	/**
	 * token 令牌
	 */
	private String token;

	/**
	 * token 时效
	 */
	private long tokenTtl;

	/**
	 * 是否超级管理员
	 */
	private boolean superAdmin;

	/**
	 * 账号
	 */
	private String username;

	/**
	 * 昵称
	 */
	private String nickname;

	/**
	 * 头像
	 */
	private String headImg;

	/**
	 * 是否拥有角色权限
	 * 有角色权限登录默认进入后台管理
	 * 否则进入个人中心
	 */
	private boolean hasRole;

	/**
	 * 系统版本号
	 */
	private String version;

	/**
	 * 用户类型
	 */
	private Integer userType;

	public FastcmsUser(String username, String token, long tokenTtl, boolean superAdmin, boolean hasRole, Integer userType) {
		this.username = username;
		this.token = token;
		this.tokenTtl = tokenTtl;
		this.superAdmin = superAdmin;
		this.hasRole = hasRole;
		this.userType = userType;
		this.version = VersionUtils.getFullClientVersion();
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public long getTokenTtl() {
		return tokenTtl;
	}

	public void setTokenTtl(long tokenTtl) {
		this.tokenTtl = tokenTtl;
	}

	public boolean isSuperAdmin() {
		return superAdmin;
	}

	public void setSuperAdmin(boolean superAdmin) {
		this.superAdmin = superAdmin;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public boolean isHasRole() {
		return hasRole;
	}

	public void setHasRole(boolean hasRole) {
		this.hasRole = hasRole;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

}
