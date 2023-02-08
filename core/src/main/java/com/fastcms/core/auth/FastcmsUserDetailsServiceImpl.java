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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fastcms.entity.Role;
import com.fastcms.entity.User;
import com.fastcms.service.IRoleService;
import com.fastcms.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author： wjun_java@163.com
 * @date： 2021/10/23
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Service
public class FastcmsUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private IUserService userService;

	@Autowired
	private IRoleService roleService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUserName, username));
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		if (StringUtils.isBlank(user.getPassword())) {
			throw new BadCredentialsException(username);
		}
		List<Role> userRoleList = roleService.getUserRoleList(user.getId());
		return new FastcmsUserDetails(user, userRoleList.stream().map(item -> new SimpleGrantedAuthority(String.valueOf(item.getId()))).collect(Collectors.toList()));
	}

}
