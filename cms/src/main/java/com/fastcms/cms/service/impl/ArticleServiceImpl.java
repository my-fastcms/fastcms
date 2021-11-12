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
package com.fastcms.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fastcms.cms.entity.Article;
import com.fastcms.cms.entity.ArticleCategory;
import com.fastcms.cms.mapper.ArticleMapper;
import com.fastcms.cms.service.IArticleCategoryService;
import com.fastcms.cms.service.IArticleService;
import com.fastcms.cms.task.ArticleViewCountUpdateTask;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author： wjun_java@163.com
 * @date： 2021/4/22
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

    @Autowired
    private IArticleCategoryService articleCategoryService;

    @Override
    @Transactional
    public void saveArticle(Article article) throws Exception {

        saveOrUpdate(article);

        //删除文章分类以及标签
        getBaseMapper().deleteRelationByArticleId(article.getId());

        final Long[] articleCategoryList = article.getArticleCategory();
        if(articleCategoryList != null && articleCategoryList.length>0) {
            //插入新的分类
            getBaseMapper().saveArticleCategoryRelation(article.getId(), Arrays.asList(articleCategoryList));
        }

        final String[] articleTag = article.getArticleTag();
        if(articleTag != null && articleTag.length>0) {
            //插入新的标签
            List<Long> tagList = new ArrayList<>();
            for (String tag : articleTag) {
                if(StringUtils.isNotBlank(tag)) {
                    ArticleCategory articleCategory = findByTitle(tag);
                    if(articleCategory == null) {
                        articleCategory = new ArticleCategory();
                        articleCategory.setTitle(tag);
                        articleCategory.setType(ArticleCategory.TAG_TYPE);
                        articleCategoryService.save(articleCategory);
                    }
                    tagList.add(articleCategory.getId());
                }
            }
            getBaseMapper().saveArticleCategoryRelation(article.getId(), tagList);
        }

    }

    @Override
    public Page<ArticleVo> pageArticle(Page pageParam, QueryWrapper queryWrapper) {
        return this.getBaseMapper().pageArticle(pageParam, queryWrapper);
    }

    @Override
    public ArticleInfoVo getArticleById(Long id) {
        ArticleViewCountUpdateTask.recordCount(id);
        return getBaseMapper().getArticleById(id);
    }

    @Override
    public Page<ArticleVo> pageArticleByCategoryId(Page pageParam, QueryWrapper queryWrapper) {
        return getBaseMapper().pageArticleByCategoryId(pageParam, queryWrapper);
    }

    @Override
    public List<ArticleVo> getArticleListByCategoryId(QueryWrapper queryWrapper) {
        return getBaseMapper().getArticleListByCategoryId(queryWrapper);
    }

    @Override
    public void updateViewCount(Long id, Long count) {
        getBaseMapper().updateViewCount(id, count);
    }

    ArticleCategory findByTitle(String title) {
        QueryWrapper<ArticleCategory> queryWrapper = new QueryWrapper();
        queryWrapper.eq("title", title.trim());
        queryWrapper.eq("type", ArticleCategory.TAG_TYPE);
        return articleCategoryService.getOne(queryWrapper);
    }

}
