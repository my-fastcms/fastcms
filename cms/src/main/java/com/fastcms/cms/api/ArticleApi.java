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
package com.fastcms.cms.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fastcms.cms.entity.Article;
import com.fastcms.cms.service.IArticleService;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.core.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author： wjun_java@163.com
 * @date： 2021/6/7
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Controller
@RequestMapping(FastcmsConstants.API_MAPPING + "/article")
public class ArticleApi {

	@Autowired
	private IArticleService articleService;

	@RequestMapping("/list")
	public ResponseEntity list(@RequestParam(name = "page", required = false, defaultValue = "1") Long page,
							   @RequestParam(name = "pageSize", required = false, defaultValue = "10") Long pageSize,
							   @RequestParam(name = "categoryId", required = false) Long categoryId) {

		QueryWrapper queryWrapper = new QueryWrapper();
		if(categoryId != null) {
			queryWrapper.eq("acr.category_id", categoryId);
		}
		queryWrapper.eq("a.status", Article.STATUS_PUBLISH);

		Page<IArticleService.ArticleVo> articleVoPage = articleService.pageArticle(new Page(page, pageSize), queryWrapper);

		return Response.success(articleVoPage);
	}

	@RequestMapping("/detail")
	public ResponseEntity detail(Long articleId) {
		IArticleService.ArticleInfoVo articleInfo = articleService.getArticleById(articleId);
		return Response.success(articleInfo);
	}

}
