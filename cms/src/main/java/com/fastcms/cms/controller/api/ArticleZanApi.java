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
package com.fastcms.cms.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fastcms.cms.service.IArticleCommentService;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.exception.FastcmsException;
import com.fastcms.common.model.RestResult;
import com.fastcms.common.model.RestResultUtils;
import com.fastcms.core.auth.AuthUtils;
import com.fastcms.core.mybatis.PageModel;
import com.fastcms.plugin.PassFastcms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 文章评论
 * @author： wjun_java@163.com
 * @date： 2021/6/7
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@RestController
@RequestMapping(FastcmsConstants.API_MAPPING + "/article/comment")
public class ArticleCommentApi {

	@Autowired
	IArticleCommentService articleCommentService;

	/**
	 * 用户评论列表
	 * @param page
	 * @param author
	 * @param content
	 * @return
	 */
	@GetMapping("user/list")
	public Object getCommentList(PageModel page, String author, String content, Boolean isParent) {
		QueryWrapper queryWrapper = Wrappers.query().eq(StringUtils.isNotBlank(author), "u.user_name", author)
				.eq(isParent != null && isParent == true, "ac.parentId", 0)
				.likeLeft(StringUtils.isNotBlank(content), "ac.content", content)
				.orderByDesc("ac.created");
		return RestResultUtils.success(articleCommentService.pageArticleComment(page.toPage(), queryWrapper));
	}

	/**
	 * 文章评论列表
	 * @param page
	 * @param articleId
	 * @return
	 */
	@GetMapping("list/{articleId}")
	@PassFastcms
	public RestResult<Page<IArticleCommentService.ArticleCommentVo>> list(PageModel page, @PathVariable("articleId") Long articleId) {
		return RestResultUtils.success(articleCommentService.pageArticleCommentByArticleId(page.toPage(), articleId, AuthUtils.getUserId()));
	}

	/**
	 * 保存评论
	 * @param articleId
	 * @param commentId
	 * @param context
	 * @return
	 */
	@PostMapping("save")
	public Object saveComment(@RequestParam("articleId") Long articleId,
							  @RequestParam("commentId") Long commentId,
							  @RequestParam("context") String context) throws FastcmsException {
		articleCommentService.saveArticleComment(articleId, commentId, context);
		return RestResultUtils.success();
	}

}
