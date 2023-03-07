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
package com.fastcms.core.auth;

import com.fastcms.common.constants.FastcmsConstants;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author： wjun_java@163.com
 * @date： 2021/10/23
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class FastcmsUserDetails extends User implements FastcmsAuthUserInfo {

	private com.fastcms.entity.User user;

	public FastcmsUserDetails(com.fastcms.entity.User user, Collection<? extends GrantedAuthority> authorities) {
		super(user.getUserName(), user.getPassword() == null ? "" : user.getPassword(), authorities);
		this.user = user;
	}

	public FastcmsUserDetails(com.fastcms.entity.User user) {
		this(user, new ArrayList<>());
	}

	@Override
	public Boolean isAdmin() {
		List<GrantedAuthority> collect = getAuthorities().stream().filter(item -> Objects.equals(Long.valueOf(item.getAuthority()), FastcmsConstants.ADMIN_ROLE_ID)).collect(Collectors.toList());
		return FastcmsConstants.ADMIN_USER_ID == this.user.getId() || (collect != null && !collect.isEmpty());
	}

	@Override
	public Boolean hasRole() {
		return FastcmsConstants.ADMIN_USER_ID == this.user.getId() || getAuthorities().size() > 0;
	}

	@Override
	public com.fastcms.entity.User getUser() {
		return user;
	}

}
