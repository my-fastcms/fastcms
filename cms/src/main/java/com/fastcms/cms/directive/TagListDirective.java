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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fastcms.cms.service.IArticleTagService;
import com.fastcms.common.utils.StrUtils;
import com.fastcms.core.directive.BaseDirective;
import freemarker.core.Environment;
import freemarker.template.TemplateModelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 获取标签列表
 *
 * <@tagList type="" orderBy="" count="10">
 *     <#if data??>
 *         <#list data as item>
 *             ${(item.id)!}
 *             ${(item.tagName)!}
 *             ${(item.url)!}
 *      		...
 *         </#list>
 *     </#if>
 * </@tagList>
 *
 * @author： wjun_java@163.com
 * @date： 2022/11/22
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component("tagList")
public class TagListDirective extends BaseDirective {

    static final String PARAM_TYPE = "type";

    @Autowired
    private IArticleTagService articleTagService;

    @Override
    public Object doExecute(Environment env, Map params) throws TemplateModelException {
        final String type = getStr(PARAM_TYPE, params, "");
        final Integer count = getInt(PARAM_COUNT, params, 10);
        final String orderBy = getStr(PARAM_ORDER_BY, params, "created");

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(StrUtils.isNotBlank(type),"type", type);
        queryWrapper.last(count > 0,"limit 0," + count);
        queryWrapper.orderByDesc(orderBy);
        return articleTagService.list(queryWrapper);
    }

}
