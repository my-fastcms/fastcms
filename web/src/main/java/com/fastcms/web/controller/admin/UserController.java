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
import com.fastcms.common.model.RestResult;
import com.fastcms.common.model.RestResultUtils;
import com.fastcms.common.utils.StrUtils;
import com.fastcms.entity.User;
import com.fastcms.entity.UserTag;
import com.fastcms.service.IPermissionService;
import com.fastcms.service.IRoleService;
import com.fastcms.service.IUserService;
import com.fastcms.service.IUserTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
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
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("list")
    public Object list(@RequestParam(name = "page", required = false, defaultValue = "1") Long page,
                       @RequestParam(name = "pageSize", required = false, defaultValue = "10") Long pageSize,
                       @RequestParam(name = "phone", required = false) String phone,
                       @RequestParam(name = "status", required = false, defaultValue = "1") Integer status) {
        LambdaQueryWrapper<User> queryWrapper = new QueryWrapper().lambda();
        queryWrapper.eq(User::getStatus, status);
        if(StrUtils.isNotBlank(phone)) {
            queryWrapper.eq(User::getMobile, phone);
        }
        queryWrapper.orderByDesc(User::getCreated);
        Page pageParam = new Page<>(page, pageSize);
        Page<User> pageData = userService.page(pageParam, queryWrapper);
        return RestResultUtils.success(pageData);
    }

    @GetMapping("getMenus")
    public Object getMenus() {
        List<IPermissionService.PermissionNode> menuNodes = new ArrayList<>();

        IPermissionService.PermissionNode home = new IPermissionService.PermissionNode(null, null, "home", "/home", "home/index", false, 1,
                "message.router.home", "iconfont icon-shouye", false, true, true, false, null, null);

        IPermissionService.PermissionNode permission = new IPermissionService.PermissionNode(null, null, "system", "/system", "layout/routerView/parent", false, 2,
                "message.router.system", "iconfont icon-xitongshezhi", false, true, false, false, Arrays.asList("admin"), null);
        List<IPermissionService.PermissionNode> permissionChildren = new ArrayList<>();
        IPermissionService.PermissionNode menu = new IPermissionService.PermissionNode(null, null, "systemMenu", "/system/menu", "system/menu/index", false, 2,
                "message.router.systemMenu", "iconfont icon-caidan", false, true, false, false, Arrays.asList("admin"), null);

        IPermissionService.PermissionNode role = new IPermissionService.PermissionNode(null, null, "systemRole", "/system/role", "system/role/index", false, 2,
                "message.router.systemRole", "iconfont icon-shuxingtu", false, true, false, false, Arrays.asList("admin"), null);

        IPermissionService.PermissionNode user = new IPermissionService.PermissionNode(null, null, "systemUser", "/system/user", "system/user/index", false, 2,
                "message.router.systemUser", "iconfont icon-icon-", false, true, false, false, Arrays.asList("admin"), null);
        permissionChildren.add(menu);
        permissionChildren.add(role);
        permissionChildren.add(user);
        permission.setChildren(permissionChildren);

        menuNodes.add(home);
        menuNodes.add(permission);
        return RestResultUtils.success(permissionService.getPermissions());
    }

    @PostMapping("doSave")
    public Object doSave(@Validated User user) {

        User userInDb = userService.getOne(new QueryWrapper<User>().lambda().eq(User::getUserName, user.getUserName()));
        if(userInDb != null) {
            return RestResultUtils.failed("登录账号不可重复");
        }

        final String salt = System.currentTimeMillis() + "";
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setSalt(salt);
        userService.saveOrUpdate(user);
        return RestResultUtils.success();
    }

    @PostMapping("doEdit")
    public Object doEdit(User user) {
        userService.saveOrUpdate(user);
        return RestResultUtils.success();
    }

    @PostMapping("doEditRole")
    public RestResult doEditRole(@RequestParam("userId") Long userId, @RequestParam("roleIds[]") List<Long> roleIds) {
        if(userId != null && Objects.equals(userId, FastcmsConstants.ADMIN_USER_ID)) {
            return RestResultUtils.failed("admin不允许修改权限");
        }
        roleService.saveUserRole(userId, roleIds);
        return RestResultUtils.success();
    }

    @PostMapping("tag/doSave")
    public Object doSaveUserTag(@Validated UserTag userTag) {
        userTagService.saveOrUpdate(userTag);
        return RestResultUtils.success();
    }

    @PostMapping("doEditTag")
    public Object doEditTag(@RequestParam("userId") Long userId, @RequestParam("tagIds[]") List<Long> tagIds) {
        userTagService.saveUserTagRelation(userId, tagIds);
        return RestResultUtils.success();
    }

}
