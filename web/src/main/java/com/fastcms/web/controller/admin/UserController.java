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
import com.fastcms.common.utils.StrUtils;
import com.fastcms.core.mybatis.DataPermission;
import com.fastcms.core.permission.AdminMenu;
import com.fastcms.core.response.Response;
import com.fastcms.entity.PaymentRecord;
import com.fastcms.entity.User;
import com.fastcms.entity.UserTag;
import com.fastcms.service.IPaymentRecordService;
import com.fastcms.service.IRoleService;
import com.fastcms.service.IUserService;
import com.fastcms.service.IUserTagService;
import com.fastcms.utils.PasswordUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
@RequestMapping(FastcmsConstants.ADMIN_MAPPING + "/user")
@AdminMenu(name = "用户", icon = "<i class=\"nav-icon far fas fa-user\"></i>", sort = 1)
public class UserController extends AdminBaseController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IUserTagService userTagService;
    @Autowired
    private IPaymentRecordService paymentRecordService;

    @AdminMenu(name = "用户管理", sort = 1)
    @GetMapping("list")
    @RequiresPermissions("user:list")
    public String list(@RequestParam(name = "page", required = false, defaultValue = "1") Long page,
                       @RequestParam(name = "pageSize", required = false, defaultValue = "10") Long pageSize,
                       @RequestParam(name = "loginAccount", required = false) String loginAccount,
                       @RequestParam(name = "phone", required = false) String phone,
                       @RequestParam(name = "status", required = false, defaultValue = "1") Integer status,
                       Model model) {
        LambdaQueryWrapper<User> queryWrapper = new QueryWrapper().lambda();
        if(StrUtils.isNotBlank(loginAccount)) {
            queryWrapper.like(loginAccount != null, User::getLoginAccount, loginAccount);
        }
        queryWrapper.eq(User::getStatus, status);
        if(StrUtils.isNotBlank(phone)) {
            queryWrapper.eq(User::getMobile, phone);
        }
        queryWrapper.orderByDesc(User::getCreated);
        Page pageParam = new Page<>(page, pageSize);
        Page<User> pageData = userService.page(pageParam, queryWrapper);
        model.addAttribute(PAGE_DATA_ATTR, pageData);
        return "admin/user/list";
    }

    @RequestMapping("add")
    public String add() {
        return "admin/user/add";
    }

    @AdminMenu(name = "保存", type = FastcmsConstants.PERMISSION_OPTION)
    @RequiresPermissions("user:save")
    @PostMapping("doSave")
    public ResponseEntity doSave(@Validated User user) {

        User userInDb = userService.getOne(new QueryWrapper<User>().lambda().eq(User::getLoginAccount, user.getLoginAccount()));
        if(userInDb != null) {
            return Response.fail("登录账号不可重复");
        }

        final String salt = System.currentTimeMillis() + "";
        final String password = PasswordUtils.getMd5Password(salt, user.getPassword());
        user.setPassword(password);
        user.setSalt(salt);
        userService.saveOrUpdate(user);
        return Response.success();
    }

    @RequestMapping("edit")
    public String edit(@RequestParam(name = "id") Long id, Model model) {
        model.addAttribute("user", userService.getById(id));
        return "admin/user/edit";
    }

    @PostMapping("doEdit")
    public ResponseEntity doEdit(User user) {
        userService.saveOrUpdate(user);
        return Response.success();
    }

    @RequestMapping("pwd")
    public String editPassword(@RequestParam(name = "id") Long id, Model model) {
        model.addAttribute("user", userService.getById(id));
        return "admin/user/edit_pwd";
    }

    @PostMapping("doEditPwd")
    public ResponseEntity doEditPwd(@Validated User user) {
        try {
            userService.updateUserPassword(user);
            return Response.success();
        } catch (Exception e) {
            return Response.fail(e.getMessage());
        }
    }

    @RequestMapping("role")
    public String editRole(@RequestParam(name = "id") Long id, Model model) {
        model.addAttribute("user", userService.getById(id));
        model.addAttribute("roleList", roleService.getRoleListByUserId(id));
        return "admin/user/edit_role";
    }

    @PostMapping("doEditRole")
    public ResponseEntity doEditRole(@RequestParam("userId") Long userId, @RequestParam("roleIds[]") List<Long> roleIds) {
        if(userId != null && Objects.equals(userId, FastcmsConstants.ADMIN_USER_ID)) {
            return Response.fail("admin不允许修改权限");
        }
        roleService.saveUserRole(userId, roleIds);
        return Response.success();
    }

    @AdminMenu(name = "标签", sort = 2)
    @GetMapping("tag/list")
    public String userTaglist(@RequestParam(name = "page", required = false, defaultValue = "1") Long page,
                       @RequestParam(name = "pageSize", required = false, defaultValue = "10") Long pageSize,
                       Model model) {
        LambdaQueryWrapper<UserTag> queryWrapper = new QueryWrapper().lambda();
        queryWrapper.orderByDesc(UserTag::getCreated);
        Page pageParam = new Page<>(page, pageSize);
        Page<UserTag> pageData = userTagService.page(pageParam, queryWrapper);
        model.addAttribute(PAGE_DATA_ATTR, pageData);
        return "admin/user/tag_list";
    }

    @RequestMapping("tag/edit")
    public String editTag(@RequestParam(name = "id", required = false) Long id, Model model) {
        model.addAttribute("userTag", userTagService.getById(id));
        return "admin/user/tag_edit";
    }

    @PostMapping("tag/doSave")
    public ResponseEntity doSaveUserTag(@Validated UserTag userTag) {
        userTagService.saveOrUpdate(userTag);
        return Response.success();
    }

    @RequestMapping("tag")
    public String editUserTag(@RequestParam(name = "id") Long id, Model model) {
        model.addAttribute("user", userService.getById(id));
        model.addAttribute("tagList", userTagService.getTagListByUserId(id));
        return "admin/user/edit_tag";
    }

    @PostMapping("doEditTag")
    public ResponseEntity doEditTag(@RequestParam("userId") Long userId, @RequestParam("tagIds[]") List<Long> tagIds) {
        userTagService.saveUserTagRelation(userId, tagIds);
        return Response.success();
    }

    @DataPermission("p")
    @AdminMenu(value = "支付记录", sort = 99)
    @RequestMapping("payment")
    public String payment(@RequestParam(name = "page", required = false, defaultValue = "1") Long page,
                          @RequestParam(name = "pageSize", required = false, defaultValue = "10") Long pageSize,
                          Model model) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", getLoginUser().getId());
        Page<PaymentRecord> paymentRecordPage = paymentRecordService.page(new Page<>(page, pageSize), queryWrapper);
        model.addAttribute(PAGE_DATA_ATTR, paymentRecordPage);
        return "admin/user/payment_list";
    }

}
