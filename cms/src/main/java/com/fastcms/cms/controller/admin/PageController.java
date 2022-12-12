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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fastcms.cms.entity.SinglePage;
import com.fastcms.cms.entity.SinglePageComment;
import com.fastcms.cms.service.ISinglePageCommentService;
import com.fastcms.cms.service.ISinglePageService;
import com.fastcms.common.auth.ActionTypes;
import com.fastcms.common.auth.Secured;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.model.RestResult;
import com.fastcms.common.model.RestResultUtils;
import com.fastcms.common.utils.StrUtils;
import com.fastcms.core.mybatis.PageModel;
import com.fastcms.utils.I18nUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.fastcms.cms.service.IArticleService.ArticleI18n.CMS_ARTICLE_CHILDREN_COMMENT_NOT_DELETE;
import static com.fastcms.service.IResourceService.ResourceI18n.*;

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
	@Secured(name = RESOURCE_NAME_PAGE_LIST, resource = "pages:list", action = ActionTypes.READ)
	public RestResult<Page<ISinglePageService.SinglePageVo>> list(PageModel page,
																  @RequestParam(name = "title", required = false) String title,
																  @RequestParam(name = "status", required = false) String status) {
		QueryWrapper queryWrapper = Wrappers.query()
				.like(StrUtils.isNotBlank(title), "p.title", title)
				.eq(StrUtils.isNotBlank(status), "p.status", status)
				.orderByDesc("p.created");
		return RestResultUtils.success(singlePageService.pageSinglePage(page.toPage(), queryWrapper));
	}

	/**
	 * 保存页面
	 * @param singlePage
	 * @return
	 */
	@PostMapping("save")
	@Secured(name = RESOURCE_NAME_PAGE_SAVE, resource = "pages:save", action = ActionTypes.WRITE)
	public Object save(@Validated SinglePage singlePage) {
		try {
			singlePageService.saveOrUpdate(singlePage);
			return RestResultUtils.success(singlePage.getId());
		} catch (Exception e) {
			return RestResultUtils.failed(e.getMessage());
		}
	}

	/**
	 * 单页详情
	 * @param id
	 * @return
	 */
	@GetMapping("get/{id}")
	@Secured(name = RESOURCE_NAME_PAGE_DETAIL, resource = "pages:get", action = ActionTypes.READ)
	public RestResult<SinglePage> getPage(@PathVariable("id") String id) {
		LambdaQueryWrapper<SinglePage> wrapper = Wrappers.<SinglePage>lambdaQuery()
				.select(SinglePage.class, info -> !info.getColumn().equals("created") && !info.getColumn().equals("updated") && !info.getColumn().equals("version"))
				.eq(SinglePage::getId, id);
		return RestResultUtils.success(singlePageService.getOne(wrapper));
	}

	/**
	 * 删除单页
	 * @param id
	 * @return
	 */
	@PostMapping("delete/{id}")
	@Secured(name = RESOURCE_NAME_PAGE_DELETE, resource = "pages:delete", action = ActionTypes.WRITE)
	public Object delPage(@PathVariable("id") String id) {
		SinglePage singlePage = singlePageService.getById(id);
		if(singlePage != null) {
			singlePage.setStatus(SinglePage.STATUS_DELETE);
			singlePageService.updateById(singlePage);
		}
		return RestResultUtils.success();
	}

	/**
	 * 评论列表
	 * @param page
	 * @param author
	 * @param content
	 * @return
	 */
	@GetMapping("comment/list")
	@Secured(name = RESOURCE_NAME_PAGE_COMMENT_LIST, resource = "pages:comment/list", action = ActionTypes.READ)
	public Object getCommentList(PageModel page, String author, String content, Boolean isParent) {
		QueryWrapper<Object> queryWrapper = Wrappers.query().eq(StringUtils.isNotBlank(author), "u.user_name", author)
				.eq(isParent != null && isParent == true, "spc.parentId", 0)
				.likeLeft(StringUtils.isNotBlank(content), "spc.content", content)
				.orderByDesc("spc.created");
		return RestResultUtils.success(singlePageCommentService.pageSinglePageComment(page.toPage(), queryWrapper));
	}

	/**
	 * 保存评论
	 * @param singlePageComment
	 * @return
	 */
	@PostMapping("comment/save")
	@Secured(name = RESOURCE_NAME_PAGE_COMMENT_SAVE, resource = "pages:comment/save", action = ActionTypes.WRITE)
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
	@Secured(name = RESOURCE_NAME_PAGE_COMMENT_DELETE, resource = "pages:comment/delete", action = ActionTypes.WRITE)
	public RestResult<Boolean> deleteComment(@PathVariable("commentId") Long commentId) {
		List<SinglePageComment> articleCommentList = singlePageCommentService.list(Wrappers.<SinglePageComment>lambdaQuery().eq(SinglePageComment::getParentId, commentId));
		if(articleCommentList != null && articleCommentList.size() >0) {
			return RestResultUtils.failed(I18nUtils.getMessage(CMS_ARTICLE_CHILDREN_COMMENT_NOT_DELETE));
		}
		return RestResultUtils.success(singlePageCommentService.removeById(commentId));
	}

}
