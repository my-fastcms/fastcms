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
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.model.RestResult;
import com.fastcms.common.model.RestResultUtils;
import com.fastcms.common.model.RouterNode;
import com.fastcms.core.auth.ActionTypes;
import com.fastcms.core.auth.AuthConstants;
import com.fastcms.core.auth.Secured;
import com.fastcms.entity.Permission;
import com.fastcms.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 前端路由管理
 * @author： wjun_java@163.com
 * @date： 2021/10/31
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@RestController
@RequestMapping(FastcmsConstants.ADMIN_MAPPING + "/router")
public class RouterController {

	@Autowired
	private IPermissionService permissionService;

	/**
	 * 路由列表
	 * @return
	 */
	@GetMapping("list")
	@Secured(resource = AuthConstants.ADMIN_RESOURCE_NAME_PREFIX + "routers", action = ActionTypes.READ)
	public RestResult<List<RouterNode>> list() {
		return RestResultUtils.success(permissionService.getPermissions(FastcmsConstants.ADMIN_USER_ID));
	}

	/**
	 * 保存路由
	 * @param permission
	 * @return
	 */
	@PostMapping("save")
	@Secured(resource = AuthConstants.ADMIN_RESOURCE_NAME_PREFIX + "routers", action = ActionTypes.WRITE)
	public RestResult<Boolean> save(@Validated Permission permission) {
		return RestResultUtils.success(permissionService.saveOrUpdate(permission));
	}

	/**
	 * 删除路由
	 * @param routerId
	 * @return
	 */
	@PostMapping("delete/{routerId}")
	@Secured(resource = AuthConstants.ADMIN_RESOURCE_NAME_PREFIX + "routers", action = ActionTypes.WRITE)
	public RestResult<Object> delMenu(@PathVariable("routerId") Long routerId) {
		List<Permission> list = permissionService.list(Wrappers.<Permission>lambdaQuery().eq(Permission::getParentId, routerId));
		if(list != null && list.size()>0) {
			return RestResultUtils.failed("请先删除子菜单");
		}
		return RestResultUtils.success(permissionService.removeById(routerId));
	}

}