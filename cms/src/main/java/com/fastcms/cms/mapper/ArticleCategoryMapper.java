package com.fastcms.cms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fastcms.cms.entity.ArticleCategory;
import org.apache.ibatis.annotations.Param;

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

}
