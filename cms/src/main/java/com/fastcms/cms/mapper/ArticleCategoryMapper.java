package com.fastcms.cms.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fastcms.core.mybatis.DataPermission;
import com.fastcms.cms.entity.ArticleCategory;
import com.fastcms.cms.service.IArticleCategoryService;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2021-05-23
 */
public interface ArticleCategoryMapper extends BaseMapper<ArticleCategory> {

	void deleteRelationByCategoryId(Long articleCategoryId);

	List<ArticleCategory> getArticleCategoryListByArticleId(@Param("articleId") Long articleId, @Param("type") String type);

	@DataPermission("a")
	Page<IArticleCategoryService.ArticleCategoryVo> pageArticleCategory(Page pageParam, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);

}
