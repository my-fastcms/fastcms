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

import com.fastcms.common.auth.model.Permission;
import com.fastcms.common.auth.model.User;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.entity.Role;
import com.fastcms.service.IResourceService;
import com.fastcms.service.IRoleService;
import com.fastcms.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @author： wjun_java@163.com
 * @date： 2021/4/10
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component
public class AuthPermissionService {

    @Autowired
    private IRoleService roleService;
    
    @Autowired
    private IResourceService resourceService;

    /**
     * 判断用户是否有资源访问权限
     * @param user
     * @param permission
     * @return
     */
    public Boolean hasPermission(User user, Permission permission) {

        //超级用户拥有最大权限
        if (Objects.equals(user.getUserId(), FastcmsConstants.ADMIN_USER_ID)) {
            return true;
        }

        if (AuthUtils.isAdmin()) {
            return true;
        }

        //加载用户角色权限
        List<Role> userRoleList = roleService.getUserRoleList(user.getUserId());

        //访问系统admin资源需要管理员角色
        if(permission.getResource().contains(FastcmsConstants.ADMIN_MAPPING) && CollectionUtils.isEmpty(userRoleList)) {
            return false;
        }

        if(CollectionUtils.isNotEmpty(userRoleList)) {

            //需要检查角色接口权限
            List<String> userResourceList = resourceService.getUserResourceList(user.getUserId());
            for (String res : userResourceList) {
                if (Objects.equals(res, permission.getResource())) {
                    return true;
                }
            }

            return false;

        }

        return false;
    }

}
