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
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.exception.FastcmsException;
import com.fastcms.common.model.RestResult;
import com.fastcms.common.model.RestResultUtils;
import com.fastcms.core.mybatis.PageModel;
import com.fastcms.entity.Role;
import com.fastcms.entity.User;
import com.fastcms.service.IPermissionService;
import com.fastcms.service.IRoleService;
import com.fastcms.service.IUserService;
import com.fastcms.service.IUserTagService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 用户管理
 * @author： wjun_java@163.com
 * @date： 2021/2/14
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@RestController
@RequestMapping(FastcmsConstants.ADMIN_MAPPING + "/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IPermissionService permissionService;

    @Autowired
    private IUserTagService userTagService;

    /**
     * 用户列表
     * @param page
     * @param phone     手机号码
     * @param status    状态
     * @return
     */
    @GetMapping("list")
    public RestResult<Page<User>> list(PageModel page,
                                       @RequestParam(name = "username", required = false) String username,
                                       @RequestParam(name = "phone", required = false) String phone,
                                       @RequestParam(name = "status", required = false) Integer status) {
        Page<User> pageData = userService.page(page.toPage(), Wrappers.<User>lambdaQuery()
                .eq(StringUtils.isNotBlank(username), User::getUserName, username)
                .eq(StringUtils.isNoneBlank(phone), User::getMobile, phone)
                .eq(status != null, User::getStatus, status)
                .select(User::getId, User::getUserName, User::getCreated, User::getSource, User::getEmail)
                .orderByDesc(User::getCreated));
        return RestResultUtils.success(pageData);
    }

    /**
     * 获取用户菜单
     * @return
     */
    @GetMapping("menus")
    public RestResult<List<IPermissionService.PermissionNode>> getMenus() {
        return RestResultUtils.success(permissionService.getPermissions());
    }

    /**
     * 保存用户信息
     * @param user
     * @return
     */
    @PostMapping("save")
    public RestResult<Boolean> save(@Validated User user) {
        try {
            return RestResultUtils.success(userService.saveUser(user));
        } catch (FastcmsException e) {
            return RestResultUtils.failed(e.getMessage());
        }
    }

    /**
     * 获取用户详细信息
     * @param userId
     * @return
     */
    @GetMapping("{userId}/get")
    public RestResult<User> getUserInfo(@PathVariable("userId") Long userId) {
        User user = userService.getById(userId);
        user.setRoleList(roleService.getUserRoleList(userId).stream().map(Role::getId).collect(Collectors.toList()));
        return RestResultUtils.success(user);
    }

    /**
     * 编辑用户信息
     * @param userId
     * @return
     */
    @PostMapping("delete/{userId}")
    public RestResult<Boolean> del(@PathVariable("userId") Long userId) {
        if(FastcmsConstants.ADMIN_USER_ID == userId) {
            return RestResultUtils.failed("超级管理员不可删除");
        }
        return RestResultUtils.success(userService.deleteUser(userId));
    }

    /**
     * 分配角色
     * @param userId    用户id
     * @param roleIds   角色集合
     * @return
     */
    @PostMapping("{userId}/roles/save/")
    public Object saveUserRoles(@PathVariable("userId") Long userId, @RequestParam("roleIds[]") List<Long> roleIds) {
        if(userId != null && Objects.equals(userId, FastcmsConstants.ADMIN_USER_ID)) {
            return RestResultUtils.failed("admin不允许修改权限");
        }
        roleService.saveUserRole(userId, roleIds);
        return RestResultUtils.success();
    }

    /**
     * 分配标签
     * @param userId    用户id
     * @param tagIds    标签集合
     * @return
     */
    @PostMapping("{userId}/tags/save")
    public Object saveUserTags(@PathVariable("userId") Long userId, @RequestParam("tagIds[]") List<Long> tagIds) {
        userTagService.saveUserTagRelation(userId, tagIds);
        return RestResultUtils.success();
    }

}
