package com.fastcms.cms.aspect;

import com.fastcms.cms.entity.ArticleCategory;
import com.fastcms.cms.site.SiteManager;
import com.fastcms.core.site.SiteContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wangjun
 **/
@Aspect
@Component
public class IArticleCategoryServiceAspect {

    @Autowired
    private SiteManager siteManager;

    @Around("execution(* com.fastcms.cms.service.IArticleCategoryService.list(..))")
    public List<ArticleCategory> list(ProceedingJoinPoint joinPoint) throws Throwable {

        if (SiteContextHolder.getSite() != null) {
            return siteManager.loadSiteCategories(SiteContextHolder.getSite());
        }

        return (List<ArticleCategory>) joinPoint.proceed(joinPoint.getArgs());
    }

}
