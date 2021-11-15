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
package com.fastcms.cms.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fastcms.cms.entity.SinglePage;
import com.fastcms.cms.entity.SinglePageComment;
import com.fastcms.cms.service.ISinglePageCommentService;
import com.fastcms.cms.service.ISinglePageService;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.model.RestResult;
import com.fastcms.common.model.RestResultUtils;
import com.fastcms.core.mybatis.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 页面管理
 * @author： wjun_java@163.com
 * @date： 2021/5/17
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@RestController
@RequestMapping(FastcmsConstants.ADMIN_MAPPING + "/page")
public class PageController {

	@Autowired
	ISinglePageService singlePageService;

	@Autowired
	ISinglePageCommentService singlePageCommentService;

	/**
	 * 页面列表
	 * @param page
	 * @return
	 */
	@GetMapping("list")
	public RestResult<Page<ISinglePageService.SinglePageVo>> list(PageModel page) {
		QueryWrapper<SinglePage> queryWrapper = new QueryWrapper();
		return RestResultUtils.success(singlePageService.pageSinglePage(page.toPage(), queryWrapper));
	}

	/**
	 * 保存页面
	 * @param singlePage
	 * @return
	 */
	@PostMapping("save")
	public Object save(@Validated SinglePage singlePage) {
		try {
			singlePageService.saveOrUpdate(singlePage);
			return RestResultUtils.success();
		} catch (Exception e) {
			return RestResultUtils.failed(e.getMessage());
		}
	}

	/**
	 * 保存评论
	 * @param singlePageComment
	 * @return
	 */
	@PostMapping("comment/save")
	public Object saveComment(@Validated SinglePageComment singlePageComment) {
		singlePageCommentService.saveOrUpdate(singlePageComment);
		return RestResultUtils.success();
	}

	/**
	 * 删除评论
	 * @param commentId
	 * @return
	 */
	@PostMapping("comment/delete/{commentId}")
	public RestResult<Boolean> deleteComment(@PathVariable("commentId") Long commentId) {
		return RestResultUtils.success(singlePageCommentService.removeById(commentId));
	}

}
