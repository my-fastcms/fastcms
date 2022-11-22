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
package com.fastcms.cms.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fastcms.cms.entity.Article;
import com.fastcms.cms.entity.ArticleCategory;
import com.fastcms.cms.service.IArticleService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author： wjun_java@163.com
 * @date： 2021/4/23
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public interface ArticleMapper extends BaseMapper<Article> {

	void deleteCategoryRelationByArticleId(Long articleId);

	void deleteTagRelationByArticleId(Long articleId);

	void saveArticleCategoryRelation(@Param("articleId") Long articleId, @Param("articleCategoryIdList") List<Long> articleCategoryIdList);

	void saveArticleTagRelation(@Param("articleId") Long articleId, @Param("articleTagIdList") List<Long> articleTagIdList);

	Page<IArticleService.ArticleVo> pageArticle(Page pageParam, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);

	List<ArticleCategory> getArticleCategoriesByArticleId(Long articleId);

	IArticleService.ArticleInfoVo getArticleById(Object id);

	Page<IArticleService.ArticleVo> pageArticleByCategoryId(Page pageParam, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);

	List<IArticleService.ArticleVo> getArticleListByCategoryId(@Param(Constants.WRAPPER) QueryWrapper queryWrapper);

	Page<IArticleService.ArticleVo> pageArticleByTagId(Page pageParam, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);

	List<IArticleService.ArticleVo> getArticleListByTagId(@Param(Constants.WRAPPER) QueryWrapper queryWrapper);

	void updateViewCount(@Param("id") Long id, @Param("count") Long count);

	IArticleService.ArticleStatVo getArticleStatData();

}
