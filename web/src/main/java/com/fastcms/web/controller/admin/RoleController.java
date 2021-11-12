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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.model.RestResultUtils;
import com.fastcms.entity.Role;
import com.fastcms.service.IPermissionService;
import com.fastcms.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

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

    @Autowired
    private IPermissionService permissionService;

    /**
     * 角色列表
     * @param page
     * @param pageSize
     * @param roleName
     * @return
     */
    @GetMapping("list")
    public Object list(@RequestParam(name = "page", required = false, defaultValue = "1") Long page,
                       @RequestParam(name = "pageSize", required = false, defaultValue = "10") Long pageSize,
                       @RequestParam(name = "roleName", required = false) String roleName) {
        LambdaQueryWrapper<Role> queryWrapper = new QueryWrapper().lambda();
        queryWrapper.like(roleName != null, Role::getRoleName, roleName);
        Page pageParam = new Page<>(page, pageSize);
        Page<Role> pageData = roleService.page(pageParam, queryWrapper);
        return RestResultUtils.success(pageData);
    }

    /**
     * 保存角色
     * @param role
     * @return
     */
    @PostMapping("save")
    public Object save(@Validated Role role) {

        if(role.getId() != null && Objects.equals(role.getId(), FastcmsConstants.ADMIN_ROLE_ID)) {
            return RestResultUtils.failed("超级管理员角色不可修改");
        }

        roleService.saveOrUpdate(role);
        return RestResultUtils.success();
    }

    /**
     * 获取角色权限列表
     * @param roleId    角色id
     * @return
     */
    @GetMapping("getPermissionList")
    public Object getPermissionList(@RequestParam(name = "roleId") Long roleId) {
        return RestResultUtils.success(permissionService.getPermissionByRoleId(roleId));
    }

    /**
     * 保存角色权限
     * @param roleId                角色id
     * @param permissionIdList      权限id集合
     * @return
     */
    @PostMapping("saveRolePermission")
    public Object saveRolePermission(@RequestParam("roleId") Long roleId, @RequestParam("permissionIdList[]") List<Long> permissionIdList) {
        if(roleId != null && Objects.equals(roleId, FastcmsConstants.ADMIN_ROLE_ID)) {
            return RestResultUtils.failed("超级管理员不可修改权限");
        }
        roleService.saveRolePermission(roleId, permissionIdList);
        return RestResultUtils.success();
    }

}
