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
package com.fastcms.cms.search;

import com.fastcms.cms.entity.Article;
import com.fastcms.common.executor.NameThreadFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 搜索器切面
 * @author： wjun_java@163.com
 * @date： 2022/03/04
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Aspect
@Component
public class SearchIndexAspect {

    @Autowired
    private FastcmsSearcherManager fastcmsSearcherManager;

    @Around("execution(* com.fastcms.cms.service.IArticleService.saveArticle(..))")
    public Boolean addIndex(ProceedingJoinPoint joinPoint) {
        try {
            final Object[] args = joinPoint.getArgs();
            if(args != null && args.length ==1) {
                if ((Boolean) joinPoint.proceed()) {
                    Article article = (Article) args[0];
                    if(Article.STATUS_PUBLISH.equals(article.getStatus())) {
                        new NameThreadFactory("createSearchIndexThread").newThread(() -> fastcmsSearcherManager.getSearcher().updateIndex((Article) args[0])).start();
                    }
                }
            }
            return true;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return false;
        }
    }

}
