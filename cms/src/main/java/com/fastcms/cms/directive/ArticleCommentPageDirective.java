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
package com.fastcms.cms.directive;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fastcms.cms.service.IArticleCommentService;
import com.fastcms.core.directive.BaseDirective;
import freemarker.core.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 分页获取文章评论列表
 * @author： wjun_java@163.com
 * @date： 2021/6/5
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component("commentPageTag")
public class ArticleCommentPageDirective extends BaseDirective {

	private static final String PARAM_ARTICLE_ID = "articleId";

	@Autowired
	private IArticleCommentService articleCommentService;

	@Override
	public Object doExecute(Environment env, Map params) {
		Long articleId = getLong(PARAM_ARTICLE_ID, params, 0l);
		return articleCommentService.pageArticleCommentByArticleId(new Page(1, 15), articleId);
	}

}
