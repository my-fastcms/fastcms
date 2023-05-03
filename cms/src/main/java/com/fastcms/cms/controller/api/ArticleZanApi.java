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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fastcms.cms.entity.ArticleZan;
import com.fastcms.cms.service.IArticleZanService;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.exception.FastcmsException;
import com.fastcms.common.model.RestResult;
import com.fastcms.common.model.RestResultUtils;
import com.fastcms.core.auth.AuthUtils;
import com.fastcms.core.mybatis.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 文章点赞
 * @author： wjun_java@163.com
 * @date： 2021/6/7
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@RestController
@RequestMapping(FastcmsConstants.API_MAPPING + "/article/zan")
public class ArticleZanApi {

	@Autowired
	IArticleZanService articleZanService;

	/**
	 * 文章点赞列表
	 * @param page
	 * @return
	 */
	@GetMapping("list")
	public RestResult<Page> getZanList(PageModel page, @RequestParam("articleId") Long articleId) {
		return RestResultUtils.success(articleZanService.pageArticleZan(page.toPage(), articleId));
	}

	/**
	 * 用户点赞
	 * @param articleId
	 * @return
	 */
	@PostMapping("save")
	public RestResult<ArticleZan> saveZan(@RequestParam("articleId") Long articleId) throws FastcmsException {
		return RestResultUtils.success(articleZanService.saveZan(AuthUtils.getUserId(), articleId));
	}

	/**
	 * 用户取消点赞
	 * @param articleId
	 * @return
	 * @throws FastcmsException
	 */
	@PostMapping("cancel")
	public RestResult<Boolean> cancelZan(@RequestParam("articleId") Long articleId) throws FastcmsException {
		return RestResultUtils.success(articleZanService.cancelZan(AuthUtils.getUserId(), articleId));
	}

	/**
	 * 用户是否点过赞
	 * @param articleId
	 * @return
	 * @throws FastcmsException
	 */
	@GetMapping("user/had/zan")
	public RestResult<Boolean> userHadZan(@RequestParam("articleId") Long articleId) throws FastcmsException {
		return RestResultUtils.success(articleZanService.isUserHadZan(AuthUtils.getUserId(), articleId));
	}

}
