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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fastcms.cms.service.IArticleCommentService;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.core.response.Response;
import com.fastcms.core.utils.CaptchaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping(FastcmsConstants.API_MAPPING + "/article/comment")
public class ArticleCommentApi {

	@Autowired
	IArticleCommentService articleCommentService;

	@RequestMapping("list")
	public ResponseEntity list(@RequestParam(name = "page", required = false, defaultValue = "1") Long page,
							   @RequestParam(name = "pageSize", required = false, defaultValue = "10") Long pageSize,
							   Long articleId) {
		return Response.success(articleCommentService.pageArticleCommentByArticleId(new Page(page, pageSize), articleId));
	}

	@PostMapping("doSaveComment")
	public ResponseEntity doSaveComment(Long articleId, Long commentId, String context, String captcha) {
		if(!CaptchaUtils.checkCaptcha(captcha)) {
			return Response.fail("验证码输入错误");
		}

		try {
			articleCommentService.saveArticleComment(articleId, commentId, context);
			return Response.success();
		} catch (Exception e) {
			return Response.fail(e.getMessage());
		}

	}

}
