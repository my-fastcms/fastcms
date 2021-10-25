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
import com.fastcms.cms.entity.ArticleCategory;
import com.fastcms.cms.service.IArticleCategoryService;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.core.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author： wjun_java@163.com
 * @date： 2021/6/7
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@RestController
@RequestMapping(FastcmsConstants.API_MAPPING + "/article/category")
public class ArticleCategoryApi {

	@Autowired
	private IArticleCategoryService articleCategoryService;

	@RequestMapping("list")
	public ResponseEntity list() {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("type", ArticleCategory.CATEGORY_TYPE);
		return Response.success(articleCategoryService.list(queryWrapper));
	}

}
