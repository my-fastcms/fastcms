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
import com.fastcms.core.permission.AdminMenu;
import com.fastcms.core.permission.PermissionManager;
import com.fastcms.core.response.Response;
import com.fastcms.entity.Permission;
import com.fastcms.entity.Role;
import com.fastcms.service.IPermissionService;
import com.fastcms.service.IRoleService;
import com.fastcms.web.Fastcms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

/**
 * @author： wjun_java@163.com
 * @date： 2021/2/14
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Controller
@AdminMenu(name = "角色", icon = "<i class=\"nav-icon fas fa-sitemap\"></i>", sort = 2)
@RequestMapping(FastcmsConstants.ADMIN_MAPPING + "/role")
public class RoleController extends AdminBaseController {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IPermissionService permissionService;

    @Autowired
    private PermissionManager permissionManager;

    @AdminMenu(name = "角色管理", sort = 1)
    @GetMapping("list")
//    @RequiresPermissions("role:list")
    public String list(@RequestParam(name = "page", required = false, defaultValue = "1") Long page,
                       @RequestParam(name = "pageSize", required = false, defaultValue = "10") Long pageSize,
                       @RequestParam(name = "roleName", required = false) String roleName,
                       Model model) {
        LambdaQueryWrapper<Role> queryWrapper = new QueryWrapper().lambda();
        queryWrapper.like(roleName != null, Role::getRoleName, roleName);
        Page pageParam = new Page<>(page, pageSize);
        Page<Role> pageData = roleService.page(pageParam, queryWrapper);
        model.addAttribute(PAGE_DATA_ATTR, pageData);
        return "admin/role/list";
    }

    @RequestMapping("edit")
    public String edit(@RequestParam(name = "id", required = false) Long id, Model model) {
        model.addAttribute("role", roleService.getById(id));
        return "admin/role/edit";
    }

    @PostMapping("doSave")
    public ResponseEntity doSave(@Validated Role role) {

        if(role.getId() != null && Objects.equals(role.getId(), FastcmsConstants.ADMIN_ROLE_ID)) {
            return Response.fail("超级管理员角色不可修改");
        }

        roleService.saveOrUpdate(role);
        return Response.success();
    }

    @GetMapping("perm")
    public String perm(@RequestParam(name = "roleId") Long roleId, Model model) {
        model.addAttribute("role", roleService.getById(roleId));
        return "admin/role/permission";
    }

    @GetMapping("getPermissionList")
    public ResponseEntity getPermissionList(@RequestParam(name = "roleId") Long roleId) {
        return ResponseEntity.ok(permissionService.getPermissionByRoleId(roleId));
    }

    @PostMapping("doSavePermission")
    public ResponseEntity doSavePermission(@RequestParam("roleId") Long roleId, @RequestParam("permissionIdList[]") List<Long> permissionIdList) {
        if(roleId != null && Objects.equals(roleId, FastcmsConstants.ADMIN_ROLE_ID)) {
            return Response.fail("超级管理员不可修改权限");
        }
        roleService.saveRolePermission(roleId, permissionIdList);
        return Response.success();
    }

    @AdminMenu(name = "权限", sort = 2)
    @GetMapping("permission/list")
    public String permList(@RequestParam(name = "page", required = false, defaultValue = "1") Long page,
                           @RequestParam(name = "pageSize", required = false, defaultValue = "10") Long pageSize,
                           Model model) {
        LambdaQueryWrapper<Permission> queryWrapper = new QueryWrapper().lambda();
        Page pageParam = new Page<>(page, pageSize);
        Page<Permission> pageData = permissionService.page(pageParam, queryWrapper);
        model.addAttribute(PAGE_DATA_ATTR, pageData);
        return "admin/role/permission_list";
    }

    @PostMapping("doSyncPermission")
    public ResponseEntity doSyncPermission() {
        try {
            permissionManager.refreshSystemPermissions(Fastcms.class);
            return Response.success();
        } catch (Exception e) {
            return Response.fail(e.getMessage());
        }
    }

}
