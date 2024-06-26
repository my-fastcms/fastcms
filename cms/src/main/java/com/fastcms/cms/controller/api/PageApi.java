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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fastcms.cms.entity.SinglePage;
import com.fastcms.cms.service.ISinglePageService;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.model.RestResult;
import com.fastcms.common.model.RestResultUtils;
import com.fastcms.core.mybatis.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author： wjun_java@163.com
 * @date： 2023/6/22
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@RestController
@RequestMapping(FastcmsConstants.API_MAPPING + "/page")
public class PageApi {

	@Autowired
	private ISinglePageService singlePageService;

	/**
	 * 页面列表
	 * @param page
	 * @return
	 */
	@GetMapping("list")
	public RestResult<Page<SinglePage>> list(PageModel page) {
		LambdaQueryWrapper<SinglePage> queryWrapper = Wrappers.<SinglePage>lambdaQuery().orderByDesc(SinglePage::getCreated);

		Page<SinglePage> singlePagePage = singlePageService.page(page.toPage(), queryWrapper);
		return RestResultUtils.success(singlePagePage);
	}

	/**
	 * 页面详情
	 * @param pageId 页面id
	 * @return
	 */
	@GetMapping("get/{pageId}")
	public RestResult<SinglePage> getArticle(@PathVariable("pageId") Long pageId) {
		return RestResultUtils.success(singlePageService.getById(pageId));
	}

}
