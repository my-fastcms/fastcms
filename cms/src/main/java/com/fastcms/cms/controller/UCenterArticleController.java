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
import com.fastcms.cms.entity.Article;
import com.fastcms.cms.service.IArticleCategoryService;
import com.fastcms.cms.service.IArticleCommentService;
import com.fastcms.cms.service.IArticleService;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.model.RestResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author： wjun_java@163.com
 * @date： 2021/6/4
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@RestController
@RequestMapping(FastcmsConstants.UCENTER_MAPPING + "/article")
public class UCenterArticleController {

	@Autowired
	private IArticleService articleService;

	@Autowired
	private IArticleCategoryService articleCategoryService;

	@Autowired
	private IArticleCommentService articleCommentService;

	@RequestMapping("list")
	public String list(@RequestParam(name = "page", required = false, defaultValue = "1") Long page,
					   @RequestParam(name = "pageSize", required = false, defaultValue = "10") Long pageSize,
					   @RequestParam(name = "title", required = false) String title,
					   @RequestParam(name = "status", required = false) String status) {
		QueryWrapper queryWrapper = new QueryWrapper();
//		queryWrapper.eq(USER_ID, getLoginUser().getId());
//		Page<Article> pageData = articleService.page(new Page<>(page, pageSize), queryWrapper);
//		model.addAttribute(PAGE_DATA_ATTR, pageData);
		return "ucenter/article/list";
	}

	@PostMapping("doSave")
	public Object doSave(@Validated Article article) {
		try {
			if(article.getId() == null) {
				article.setStatus(Article.STATUS_AUDIT);
			}
			articleService.saveArticle(article);
			return RestResultUtils.success();
		} catch (Exception e) {
			return RestResultUtils.failed(e.getMessage());
		}
	}

	@PostMapping("doSaveComment")
	public Object doSaveComment(Long articleId, Long commentId, String context, String captcha) {
		try {
			articleCommentService.saveArticleComment(articleId, commentId, context);
			return RestResultUtils.success();
		} catch (Exception e) {
			return RestResultUtils.failed(e.getMessage());
		}

	}

}
