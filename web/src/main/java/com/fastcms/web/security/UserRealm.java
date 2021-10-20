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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fastcms.entity.Permission;
import com.fastcms.entity.Role;
import com.fastcms.entity.User;
import com.fastcms.mapper.PermissionMapper;
import com.fastcms.mapper.RoleMapper;
import com.fastcms.mapper.UserMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author： wjun_java@163.com
 * @date： 2021/2/14
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User user = (User) principals.getPrimaryPrincipal();

        //查找用户权限列表
        List<Permission> permissionList = permissionMapper.getPermissionByUserId(user.getId());

        Set<String> permsSet = new HashSet<>();
        permsSet.addAll(permissionList.stream().map(Permission::getPermInfo).filter(item -> StringUtils.isNotBlank(item)).collect(Collectors.toList()));

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);

        List<Role> roleList = roleMapper.getUserRoleList(user.getId());
        Set<String> rolesSet = new HashSet<>();
        rolesSet.addAll(roleList.stream().map(Role::getRoleName).collect(Collectors.toList()));
        info.setRoles(rolesSet);

        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String loginAccount = usernamePasswordToken.getUsername();
        if(StringUtils.isBlank(loginAccount)) throw new AccountException("账号不存在");

        LambdaQueryWrapper<User> queryWrapper = new QueryWrapper().lambda();
        queryWrapper.eq(User::getLoginAccount, loginAccount);
        User user = userMapper.selectOne(queryWrapper);
        if(user == null) throw new UnknownAccountException("账号不存在");
        if(user.getStatus() == 0) throw new UnknownAccountException("账号已被禁止登录");

        user.setRoleList(roleMapper.getUserRoleList(user.getId()));

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
            user,
            user.getPassword().toCharArray(),
            ByteSource.Util.bytes(user.getSalt()),
            getName()
        );

        return authenticationInfo;
    }

}
