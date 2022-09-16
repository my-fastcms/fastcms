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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fastcms.cms.entity.Article;
import org.springframework.core.Ordered;

/**
 * fastcms站点搜索器
 * @author： wjun_java@163.com
 * @date： 2022/03/04
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public interface FastcmsSearcher extends Ordered {

    /**
     * 添加搜索索引
     * @param article
     */
    void addIndex(Article article);

    /**
     * 删除搜索索引
     * @param id
     */
    void deleteIndex(Object id);

    /**
     * 更新搜索索引
     * @param article
     */
    void updateIndex(Article article);

    /**
     * 文章搜索
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<Article> search(String keyword, int pageNum, int pageSize);

    /**
     * 是否启用
     * @return
     */
    boolean isEnable();

}
