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
package com.fastcms.web.controller.admin;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fastcms.common.auth.ActionTypes;
import com.fastcms.common.auth.Secured;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.model.RestResult;
import com.fastcms.common.model.RestResultUtils;
import com.fastcms.common.utils.StrUtils;
import com.fastcms.core.mybatis.PageModel;
import com.fastcms.entity.Role;
import com.fastcms.service.IRoleService;
import com.fastcms.utils.I18nUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static com.fastcms.service.IResourceService.ResourceI18n.*;
import static com.fastcms.service.IRoleService.RoleI18n.*;

/**
 * 角色管理
 * @author： wjun_java@163.com
 * @date： 2021/2/14
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@RestController
@RequestMapping(FastcmsConstants.ADMIN_MAPPING + "/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    /**
     * 角色列表
     * @param page
     * @param roleName 角色名称
     * @return
     */
    @GetMapping("list")
    @Secured(name = RESOURCE_NAME_ROLE_LIST, resource = "roles:list", action = ActionTypes.READ)
    public RestResult<Page<Role>> list(PageModel page,
                                       @RequestParam(name = "roleName", required = false) String roleName) {
        Page<Role> pageData = roleService.page(page.toPage(), Wrappers.<Role>lambdaQuery().like(StrUtils.isNotBlank(roleName), Role::getRoleName, roleName));
        return RestResultUtils.success(pageData);
    }

    /**
     * 保存角色
     * @param role
     * @return
     */
    @PostMapping("save")
    @Secured(name = RESOURCE_NAME_ROLE_SAVE, resource = "roles:save", action = ActionTypes.WRITE)
    public Object save(@Validated Role role) {

        if(role.getId() != null && Objects.equals(role.getId(), FastcmsConstants.ADMIN_ROLE_ID)) {
            return RestResultUtils.failed(I18nUtils.getMessage(ROLE_NOT_ALLOW_MODIFY));
        }

        roleService.saveOrUpdate(role);
        return RestResultUtils.success();
    }

    /**
     * 删除角色
     * @param roleId
     * @return
     */
    @PostMapping("delete/{roleId}")
    @Secured(name = RESOURCE_NAME_ROLE_DELETE, resource = "roles:delete", action = ActionTypes.WRITE)
    public RestResult<Object> del(@PathVariable("roleId") Long roleId) {
        if(roleId != null && Objects.equals(roleId, FastcmsConstants.ADMIN_ROLE_ID)) {
            return RestResultUtils.failed(I18nUtils.getMessage(ROLE_NOT_ALLOW_DELETE));
        }
        return RestResultUtils.success(roleService.removeById(roleId));
    }

    /**
     * 角色列表
     * @return
     */
    @GetMapping("list/select")
    public RestResult<List<Role>> getRoleList() {
        return RestResultUtils.success(roleService.list(Wrappers.<Role>lambdaQuery().eq(Role::getActive, 1).select(Role::getId, Role::getRoleName)));
    }

    /**
     * 角色权限列表
     * @param roleId    角色id
     * @return
     */
    @GetMapping("{roleId}/permissions")
    @Secured(name = RESOURCE_NAME_ROLE_PERMISSION_LIST, resource = "roles:permissions/list", action = ActionTypes.READ)
    public RestResult<IRoleService.RolePermissions> getPermissionList(@PathVariable("roleId") Long roleId) {
        return RestResultUtils.success(roleService.getRolePermission(roleId));
    }

    /**
     * 保存角色权限
     * @param roleId                角色id
     * @param permissionIdList      权限id集合
     * @return
     */
    @PostMapping("{roleId}/permissions/save")
    @Secured(name = RESOURCE_NAME_ROLE_PERMISSION_SAVE, resource = "roles:permissions/save", action = ActionTypes.WRITE)
    public Object saveRolePermission(@PathVariable("roleId") Long roleId,
                                     @RequestParam(value = "permissionIdList", required = false) List<Long> permissionIdList,
                                     @RequestParam(value = "resourcePathList", required = false) List<String> resourcePathList) {
        if(roleId != null && Objects.equals(roleId, FastcmsConstants.ADMIN_ROLE_ID)) {
            return RestResultUtils.failed(I18nUtils.getMessage(ROLE_NOT_ALLOW_MODIFY_AUTH));
        }
        roleService.saveRolePermission(roleId, permissionIdList, resourcePathList);
        return RestResultUtils.success();
    }

}
