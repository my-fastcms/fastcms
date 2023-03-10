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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fastcms.cms.entity.Article;
import com.fastcms.cms.entity.ArticleCategory;
import com.fastcms.cms.entity.ArticleTag;
import com.fastcms.cms.mapper.ArticleMapper;
import com.fastcms.cms.service.IArticleCategoryService;
import com.fastcms.cms.service.IArticleService;
import com.fastcms.cms.service.IArticleTagService;
import com.fastcms.cms.task.ArticleViewCountUpdateTask;
import com.fastcms.common.exception.FastcmsException;
import com.fastcms.common.exception.I18nFastcmsException;
import com.fastcms.common.utils.StrUtils;
import com.fastcms.entity.Attachment;
import com.fastcms.extension.IndexDataExtension;
import com.fastcms.service.IAttachmentService;
import com.fastcms.utils.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.pf4j.Extension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.fastcms.common.constants.FastcmsConstants.FASTCMS_SYSTEM_SAVE_ERROR;

/**
 * @author： wjun_java@163.com
 * @date： 2021/4/22
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Service
@Extension
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService, IndexDataExtension {

    @Autowired
    private IArticleTagService articleTagService;

    @Autowired
    private IArticleCategoryService articleCategoryService;

    @Autowired
    private IAttachmentService attachmentService;

    @Override
    @Transactional
    public boolean saveArticle(Article article) throws FastcmsException {

        if(!saveOrUpdate(article)) {
            throw new I18nFastcmsException(FastcmsException.SERVER_ERROR, FASTCMS_SYSTEM_SAVE_ERROR);
        }

        //删除文章分类以及标签
        getBaseMapper().deleteCategoryRelationByArticleId(article.getId());
        getBaseMapper().deleteTagRelationByArticleId(article.getId());

        if(article.getArticleCategory() != null) {
            final List<Long> articleCategoryList = new ArrayList<>(article.getArticleCategory());
            if(articleCategoryList != null && articleCategoryList.size()>0) {
                //插入新的分类
                getBaseMapper().saveArticleCategoryRelation(article.getId(), articleCategoryList);
            }
        }

        final List<String> articleTagList = article.getArticleTag();
        if(articleTagList != null && articleTagList.size()>0) {
            //插入新的标签
            processTag(article.getId(), articleTagList);
        }

        return true;

    }

    @Override
    public Article getArticle(Long articleId) {
        LambdaQueryWrapper<Article> wrapper = Wrappers.<Article>lambdaQuery()
                .select(Article.class, info -> !info.getColumn().equals("created")
                        && !info.getColumn().equals("updated")
                        && !info.getColumn().equals("version")
                        && !info.getColumn().equals("json_ext")
                )
                .eq(Article::getId, articleId);
        Article article = getOne(wrapper);

        if(article != null) {
            List<ArticleCategory> articleCategoryList = articleCategoryService.getArticleCategoryListByArticleId(articleId);
            article.setArticleCategory(articleCategoryList.stream().filter(item -> item.getType().equals(ArticleCategory.CATEGORY_TYPE)).map(ArticleCategory::getId).collect(Collectors.toSet()));

            List<ArticleTag> articleTagList = articleTagService.getArticleTagListByArticleId(articleId);
            article.setArticleTag(articleTagList.stream().map(ArticleTag::getTagName).collect(Collectors.toList()));

            Attachment attachment = attachmentService.getById(article.getAttachId());
            if(attachment != null) {
                article.setAttachId(attachment.getId());
                article.setAttachTitle(attachment.getFileName());
            }
        }

        return article;
    }

    void processTag(Long articleId, List<String> processList) {
        if(processList != null && processList.size()>0) {
            List<ArticleTag> articleTagList = new ArrayList<>();
            //插入新的标签
            for (String tag : processList) {
                if(StringUtils.isNotBlank(tag)) {
                    articleTagList.add(articleTagService.getByName(tag));
                }
            }

            if(!articleTagList.isEmpty()) {
                articleTagService.saveBatch(articleTagList.stream().filter(item -> item.getId() == null).collect(Collectors.toList()));
                getBaseMapper().saveArticleTagRelation(articleId, articleTagList.stream().map(ArticleTag::getId).collect(Collectors.toList()));
            }

        }
    }

    @Override
    public Page<ArticleVo> pageArticle(Page pageParam, QueryWrapper queryWrapper) {
        return this.getBaseMapper().pageArticle(pageParam, queryWrapper);
    }

    @Override
    public ArticleInfoVo getArticleDetail(Long id) {
        ArticleViewCountUpdateTask.recordCount(id);
        return getBaseMapper().getArticleById(id);
    }

    @Override
    public Page<ArticleVo> pageArticleByCategoryId(Page pageParam, QueryWrapper queryWrapper) {
        return getBaseMapper().pageArticleByCategoryId(pageParam, queryWrapper);
    }

    @Override
    public List<ArticleVo> getArticleListByCategoryId(Long categoryId, List<Long> includeIds, List<Long> excludeIds, Integer count, String orderBy) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(categoryId != null && categoryId > 0, "acr.category_id", categoryId);
        queryWrapper.eq("a.status", Article.STATUS_PUBLISH);
        queryWrapper.in(CollectionUtils.isNotEmpty(includeIds), "acr.category_id", includeIds);
        queryWrapper.notIn(CollectionUtils.isNotEmpty(excludeIds), "acr.category_id", excludeIds);
        queryWrapper.last(count != null, "limit 0," + count);
        queryWrapper.orderByDesc(StrUtils.isBlank(orderBy), orderBy);
        return getBaseMapper().getArticleListByCategoryId(queryWrapper);
    }

    @Override
    public List<ArticleVo> getArticleListByCategoryId(Long categoryId, Integer count, String orderBy) {
        return getArticleListByCategoryId(categoryId, null, null, count, orderBy);
    }

    @Override
    public List<ArticleVo> getArticleListByCategoryId(Long categoryId, String orderBy) {
        return getArticleListByCategoryId(categoryId, null, orderBy);
    }

    @Override
    public Page<ArticleVo> pageArticleByTagId(Page pageParam, QueryWrapper queryWrapper) {
        return getBaseMapper().pageArticleByTagId(pageParam, queryWrapper);
    }

    @Override
    public List<ArticleVo> getArticleListByTagId(Long tagId, List<Long> includeIds, List<Long> excludeIds, Integer count, String orderBy) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(tagId != null && tagId > 0, "acr.tag_id", tagId);
        queryWrapper.eq("a.status", Article.STATUS_PUBLISH);
        queryWrapper.in(CollectionUtils.isNotEmpty(includeIds), "acr.tag_id", includeIds);
        queryWrapper.notIn(CollectionUtils.isNotEmpty(excludeIds), "acr.tag_id", excludeIds);
        queryWrapper.last("limit 0," + count);
        queryWrapper.orderByDesc(orderBy);
        return getBaseMapper().getArticleListByTagId(queryWrapper);
    }

    @Override
    public List<ArticleVo> getArticleListByTagId(Long tagId, Integer count, String orderBy) {
        return getArticleListByCategoryId(tagId, null, null, count, orderBy);
    }

    @Override
    public void updateViewCount(Long id, Long count) {
        getBaseMapper().updateViewCount(id, count);
    }

    @Override
    public ArticleStatVo getArticleStatData() {
        return getBaseMapper().getArticleStatData();
    }

    @Override
    public Map<String, Object> getData() {
        Map<String, Object> result = new HashMap<>();
        result.put("articleStatData", getArticleStatData());

        LambdaQueryWrapper<Article> queryWrapper = Wrappers.<Article>lambdaQuery()
                .eq(Article::getStatus, Article.STATUS_PUBLISH)
                .orderByDesc(Article::getCreated)
                .last("limit 0, 12")
                .select(Article::getId, Article::getTitle, Article::getViewCount);

        List<Article> articleList = list(queryWrapper);
        result.put("newArticleList", articleList);

        return result;
    }
}
