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
package com.fastcms.cms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fastcms.cms.entity.SinglePage;
import com.fastcms.cms.entity.SinglePageComment;
import com.fastcms.cms.service.ISinglePageCommentService;
import com.fastcms.cms.service.ISinglePageService;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.core.permission.AdminMenu;
import com.fastcms.core.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author： wjun_java@163.com
 * @date： 2021/5/17
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Slf4j
@RestController
@RequestMapping(FastcmsConstants.ADMIN_MAPPING + "/page")
@AdminMenu(name = "页面", icon = "<i class=\"nav-icon fas fa-copy\"></i>", sort = 2)
public class PageController {

	@Autowired
	ISinglePageService singlePageService;

	@Autowired
	ISinglePageCommentService singlePageCommentService;

	@AdminMenu(name = "页面管理", sort = 1)
	@GetMapping("list")
	public String list(@RequestParam(name = "page", required = false, defaultValue = "1") Long page,
					   @RequestParam(name = "pageSize", required = false, defaultValue = "10") Long pageSize,
					   Model model) {
		QueryWrapper<SinglePage> queryWrapper = new QueryWrapper();
//		model.addAttribute(PAGE_DATA_ATTR, singlePageService.pageSinglePage(new Page<>(page, pageSize), queryWrapper));
		return "admin/page/list";
	}

	@AdminMenu(name = "新建", sort = 2)
	@RequestMapping("create")
	public String create(@RequestParam(name = "id", required = false) Long id, Model model) {
		model.addAttribute("singlePage", singlePageService.getById(id));
		return "admin/page/edit";
	}

	@PostMapping("doSave")
	public ResponseEntity doSave(@Validated SinglePage singlePage) {
		try {
			singlePageService.saveOrUpdate(singlePage);
			return Response.success();
		} catch (Exception e) {
			return Response.fail(e.getMessage());
		}
	}

	@AdminMenu(name = "评论", sort = 3)
	@GetMapping("comment/list")
	public String listCommnet(@RequestParam(name = "page", required = false, defaultValue = "1") Long page,
					   @RequestParam(name = "pageSize", required = false, defaultValue = "10") Long pageSize,
					   Model model) {
		QueryWrapper queryWrapper = new QueryWrapper();
//		model.addAttribute(PAGE_DATA_ATTR, singlePageCommentService.pageSinglePageComment(new Page<>(page, pageSize), queryWrapper));
		return "admin/page/comment_list";
	}

	@RequestMapping("comment/edit")
	public String editComment(@RequestParam(name = "id", required = false) Long id, Model model) {
		model.addAttribute("pageComment", singlePageCommentService.getById(id));
		return "admin/page/comment_edit";
	}

	@PostMapping("comment/doSave")
	public ResponseEntity doSaveComment(@Validated SinglePageComment singlePageComment) {
		singlePageCommentService.saveOrUpdate(singlePageComment);
		return Response.success();
	}

	@PostMapping("comment/doDelete")
	public ResponseEntity doDeleteComment(@RequestParam(name = "id") Long id) {
		singlePageCommentService.removeById(id);
		return Response.success();
	}

}
