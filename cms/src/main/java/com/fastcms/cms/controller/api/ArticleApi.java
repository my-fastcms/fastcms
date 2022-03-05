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
import com.fastcms.cms.entity.Article;
import com.fastcms.cms.search.FastcmsSearcherManager;
import com.fastcms.cms.service.IArticleService;
import com.fastcms.cms.utils.ArticleUtils;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.exception.FastcmsException;
import com.fastcms.common.model.RestResult;
import com.fastcms.common.model.RestResultUtils;
import com.fastcms.common.utils.DirUtils;
import com.fastcms.common.utils.FileUtils;
import com.fastcms.common.utils.StrUtils;
import com.fastcms.core.auth.AuthUtils;
import com.fastcms.core.mybatis.PageModel;
import com.fastcms.entity.Attachment;
import com.fastcms.service.IAttachmentService;
import com.fastcms.service.IPaymentRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Objects;

/**
 * 文章接口
 * @author： wjun_java@163.com
 * @date： 2021/6/7
 * @description：文章接口
 * @modifiedBy：
 * @version: 1.0
 */
@RestController
@RequestMapping(FastcmsConstants.API_MAPPING + "/article")
public class ArticleApi {

	@Autowired
	private IArticleService articleService;

	@Autowired
	private IPaymentRecordService paymentRecordService;

	@Autowired
	private IAttachmentService attachmentService;

	@Autowired
	private FastcmsSearcherManager fastcmsSearcherManager;

	/**
	 * 保存文章
	 * @param article
	 * @return
	 */
	@PostMapping("save")
	public Object save(@Validated Article article) {
		try {
			if(article.getId() == null) {
				article.setStatus(Article.STATUS_AUDIT);
			} else {
				if(!Objects.equals(article.getUserId(), AuthUtils.getUserId())) {
					return RestResultUtils.failed("只能修改自己的文章");
				}
			}

			articleService.saveArticle(article);
			return RestResultUtils.success();
		} catch (Exception e) {
			return RestResultUtils.failed(e.getMessage());
		}
	}

	/**
	 * 删除文章
	 * @param articleId
	 * @return
	 */
	@PostMapping("delete/{articleId}")
	public Object delete(@PathVariable("articleId") Long articleId) {
		try {
			Article article = articleService.getById(articleId);
			if(article == null) {
				return RestResultUtils.failed("文章不存在");
			}

			if(!Objects.equals(AuthUtils.getUserId(), article.getUserId())) {
				return RestResultUtils.failed("只能删除自己的文章");
			}

			article.setStatus(Article.STATUS_DELETE);
			articleService.updateById(article);
			return RestResultUtils.success();
		} catch (Exception e) {
			return RestResultUtils.failed(e.getMessage());
		}
	}

	/**
	 * 文章搜索
	 * @param page
	 * @param keyword
	 * @return
	 */
	@RequestMapping("search")
	public RestResult<Page<Article>> list(PageModel page, @RequestParam(name = "keyword", required = false) String keyword) {
		return RestResultUtils.success(fastcmsSearcherManager.getSearcher().search(keyword, page.getPageNum().intValue(), page.getPageSize().intValue()));
	}

	/**
	 * 下载附件
	 * @param articleId
	 * @param response
	 */
	@GetMapping("download/{articleId}")
	public Object download(@PathVariable("articleId") Long articleId, HttpServletResponse response) throws IOException {
		Article article = articleService.getById(articleId);
		if(article == null) {
			return RestResultUtils.failed("数据不存在");
		}

		Attachment attachment = attachmentService.getById(article.getAttachId());
		if(attachment == null) {
			return RestResultUtils.failed("不存在关联的附件");
		}

		if(StrUtils.isBlank(attachment.getFilePath())) {
			return RestResultUtils.failed("附件路径为空");
		}

		String path = DirUtils.getUploadDir() + attachment.getFilePath().substring(1);
		File file = new File(path);
		if(!file.exists()) {
			return RestResultUtils.failed("附件不存在");
		}

		//通过文章付费插件设置价格，以及是否开启付费
		boolean enableNeedToPay = ArticleUtils.isEnableNeedToPay(article);
		if(enableNeedToPay) {
			BigDecimal price = ArticleUtils.getPrice(article);
			if(price != null && price.compareTo(BigDecimal.ZERO) ==1) {
				//检查是否需要支付
				if(paymentRecordService.checkNeedPay(articleId, AuthUtils.getUserId())) {
					return RestResultUtils.failed(FastcmsException.ARTICLE_NEED_TO_PAY_CODE, "需要先支付");
				}
			}
		}

		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(path);
			response.reset();
			response.setContentType("application/octet-stream");
			String filename = StrUtils.uuid() + FileUtils.getSuffix(path);
			response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
			ServletOutputStream outputStream = response.getOutputStream();
			byte[] b = new byte[1024];
			int len;
			while ((len = inputStream.read(b)) > 0) {
				outputStream.write(b, 0, len);
			}
		} finally {
			if(inputStream != null) {
				inputStream.close();
			}
		}

		return null;
	}

}
