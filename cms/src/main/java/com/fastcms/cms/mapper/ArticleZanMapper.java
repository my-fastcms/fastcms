package com.fastcms.cms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fastcms.cms.entity.ArticleZan;
import com.fastcms.cms.service.IArticleZanService;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2023-05-01
 */
public interface ArticleZanMapper extends BaseMapper<ArticleZan> {

	Page<IArticleZanService.ArticleZanVo> pageArticleZan(Page pageParam, @Param("articleId") Long articleId);

}
