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

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fastcms.common.auth.ActionTypes;
import com.fastcms.common.auth.Secured;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.exception.FastcmsException;
import com.fastcms.common.model.RestResult;
import com.fastcms.common.model.RestResultUtils;
import com.fastcms.core.auth.AuthUtils;
import com.fastcms.core.mybatis.PageModel;
import com.fastcms.entity.Department;
import com.fastcms.entity.Role;
import com.fastcms.entity.User;
import com.fastcms.entity.UserTag;
import com.fastcms.service.IDepartmentService;
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
    private IUserTagService userTagService;

    @Autowired
    private IDepartmentService departmentService;

    /**
     * 用户列表
     * @param page
     * @param phone     手机号码
     * @param status    状态
     * @return
     */
    @GetMapping("list")
    @Secured(name = "用户列表", resource = "users:list", action = ActionTypes.READ)
    public RestResult<Page<User>> list(PageModel page,
                                       @RequestParam(name = "type") Integer type,
                                       @RequestParam(name = "username", required = false) String username,
                                       @RequestParam(name = "phone", required = false) String phone,
                                       @RequestParam(name = "deptId", required = false) Long deptId,
                                       @RequestParam(name = "status", required = false) Integer status) {

        List<Long> departmentUserIdList = null;
        if (deptId != null) {
            departmentUserIdList = departmentService.getDepartmentUserIdList(deptId);
            if (CollectionUtils.isEmpty(departmentUserIdList)) {
                return null;
            }
        }

        Page<User> pageData = userService.page(page.toPage(), Wrappers.<User>lambdaQuery()
                .eq(User::getUserType, type)
                .like(StringUtils.isNotBlank(username), User::getUserName, username)
                .eq(StringUtils.isNoneBlank(phone), User::getMobile, phone)
                .eq(status != null, User::getStatus, status)
                .in(departmentUserIdList != null && departmentUserIdList.size() > 0, User::getId, departmentUserIdList)
                .select(User::getId, User::getUserName, User::getNickName, User::getCreated, User::getSource, User::getEmail, User::getStatus)
                .orderByDesc(User::getCreated));
        return RestResultUtils.success(pageData);
    }

    /**
     * 新增用户
     * @param user
     * @return
     */
    @PostMapping("save")
    @Secured(name = "用户保存", resource = "users:save", action = ActionTypes.WRITE)
    public RestResult<Long> save(@Validated User user) throws FastcmsException {
        return RestResultUtils.success(userService.saveUser(user));
    }

    /**
     * 用户信息
     * @param userId
     * @return
     */
    @GetMapping("{userId}/get")
    @Secured(name = "用户详情", resource = "users:get", action = ActionTypes.READ)
    public RestResult<User> getUserInfo(@PathVariable("userId") Long userId) {
        User user = userService.getById(userId);
        user.setRoleList(roleService.getUserRoleList(userId).stream().map(Role::getId).collect(Collectors.toList()));
        user.setTagList(userTagService.getTagListByUserId(userId).stream().map(UserTag::getTitle).collect(Collectors.toList()));
        user.setDeptList(departmentService.getUserDepartment(userId).stream().map(Department::getId).collect(Collectors.toList()));
        return RestResultUtils.success(user);
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    @PostMapping("delete/{userId}")
    @Secured(name = "用户删除", resource = "users:delete", action = ActionTypes.WRITE)
    public RestResult<Boolean> del(@PathVariable("userId") Long userId) throws FastcmsException {
        return RestResultUtils.success(userService.deleteUser(userId));
    }

    /**
     * 分配角色
     * @param userId    用户id
     * @param roleIds   角色集合
     * @return
     */
    @PostMapping("{userId}/roles/save")
    @Secured(name = "用户角色分配", resource = "users:roles/save", action = ActionTypes.WRITE)
    public Object saveUserRoles(@PathVariable("userId") Long userId, @RequestParam("roleIds") List<Long> roleIds) {
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
    @Secured(name = "用户标签分配", resource = "users:tags/save", action = ActionTypes.WRITE)
    public Object saveUserTags(@PathVariable("userId") Long userId, @RequestParam("tagIds") List<Long> tagIds) {
        userTagService.saveUserTagRelation(userId, tagIds);
        return RestResultUtils.success();
    }

    /**
     * 标签列表
     * @return
     */
    @GetMapping("tag/list")
    public RestResult<List<UserTag>> getTagList() {
        return RestResultUtils.success(userTagService.list());
    }

    /**
     * 修改密码
     * @param updatePasswordParam
     * @return
     */
    @PostMapping("password/update")
    @Secured(name = "用户密码修改", resource = "users:password/update", action = ActionTypes.WRITE)
    public Object updatePassword(@Validated IUserService.UpdatePasswordParam updatePasswordParam) throws FastcmsException {
        updatePasswordParam.setId(AuthUtils.getUserId());
        userService.updateUserPassword(updatePasswordParam);
        return RestResultUtils.success();
    }

    /**
     * 管理员重置用户密码
     * @param userId
     * @return
     * @throws FastcmsException
     */
    @PostMapping("/reset/password")
    @Secured(name = "用户密码修改", resource = "users:resetPassword", action = ActionTypes.WRITE)
    public Object resetPassword(@RequestParam("userId") Long userId) throws FastcmsException {
        userService.resetPassword(userId);
        return RestResultUtils.success();
    }

    /**
     * 修改用户类型
     * @param userId
     * @param userType
     * @return
     * @throws FastcmsException
     */
    @PostMapping("changUserType")
    @Secured(name = "用户类型修改", resource = "users:changeUserType", action = ActionTypes.WRITE)
    public Object changeUserType(@RequestParam("userId") Long userId, @RequestParam("userType") Integer userType) throws FastcmsException {
        userService.changeUserType(userId, userType);
        return RestResultUtils.success();
    }

}
