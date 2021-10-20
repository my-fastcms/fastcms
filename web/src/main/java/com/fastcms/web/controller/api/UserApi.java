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
package com.fastcms.web.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.core.jwt.ApiToken;
import com.fastcms.core.response.Response;
import com.fastcms.entity.Station;
import com.fastcms.service.IStationService;
import com.fastcms.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author： wjun_java@163.com
 * @date： 2021/7/24
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Slf4j
@Controller
@RequestMapping(FastcmsConstants.API_MAPPING + "/user")
public class UserApi extends ApiBaseController {

	@Autowired
	private IUserService userService;

	@Autowired
	private IStationService stationService;

	@ApiToken
	@GetMapping("tag/getTagList")
	public ResponseEntity getTagList() {
		return Response.success(userService.getUserTagList(getUserId()));
	}

	@ApiToken
	@GetMapping("team/getUserBySearch")
	public ResponseEntity getUserBySearch(@RequestParam(name = "keyword") String keyword) {
		return Response.success(userService.getUserBySearch(getUserId(), keyword));
	}

	@ApiToken
	@PostMapping("team/doSaveUserTeam")
	public ResponseEntity doSaveUserTeam(@Validated @RequestBody IUserService.AddGroupUserParam addGroupUserParam) {
		try {
			userService.saveGroupUser(addGroupUserParam);
			return Response.success();
		} catch (Exception e) {
			return Response.fail(e.getMessage());
		}
	}

	@ApiToken
	@GetMapping("team/getUserTeamList")
	public ResponseEntity getUserTeamList(@RequestParam(name = "page", required = false, defaultValue = "1") Long page,
										  @RequestParam(name = "pageSize", required = false, defaultValue = "10") Long pageSize,
										  @RequestParam(name = "userId", required = false) Long userId,
										  @RequestParam(name = "stationName") String stationName) {
		if(userId == null) {
			userId = getUserId();
		}
		return Response.success(userService.getUserTeamList(new Page(page, pageSize), userId, stationName));
	}

	@ApiToken
	@GetMapping("station/getUserStationList")
	public ResponseEntity getUserStationList() {
		return Response.success(userService.getUserStationList(getUserId()));
	}

	@ApiToken
	@GetMapping("station/getStationList")
	public ResponseEntity getStationList() {
		LambdaQueryWrapper<Station> queryWrapper = new LambdaQueryWrapper();
		queryWrapper.eq(Station::getStatus, 1);
		return Response.success(stationService.list(queryWrapper));
	}


}
