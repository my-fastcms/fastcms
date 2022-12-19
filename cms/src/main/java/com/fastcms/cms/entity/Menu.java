package com.fastcms.cms.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fastcms.core.template.StaticPathHelper;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 网站菜单
 *
 * @author wjun_java@163.com
 * @since 2021-05-27
 */
public class Menu implements Serializable, StaticPathHelper {

    private static final long serialVersionUID = 1L;

    public static final String STATUS_SHOW = "show";
    public static final String STATUS_HIDDEN = "hidden";

    public static final Integer ARTICLE_URL_TYPE = 1;
    public static final Integer PAGE_URL_TYPE = 2;
    public static final Integer CATEGORY_URL_TYPE = 3;
    public static final Integer TAG_URL_TYPE = 4;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 上级id
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Long parentId;

    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUserId;

    /**
     * 菜单名称
     */
    @NotBlank(message = "{fastcms.cms.template.menu.title.is.not.allow.empty}")
    private String menuName;

    /**
     * 菜单地址
     */
    @NotBlank(message = "{fastcms.cms.template.menu.url.is.not.allow.empty}")
    private String menuUrl;

    /**
     * 菜单图标
     */
    private String menuIcon;

    /**
     * 排序
     */
    private Integer sortNum;

    /**
     * 打开方式
     */
    private String target;

    /**
     * 菜单跳转url类型 1，文章，2，页面，3，分类， 4，标签
     */
    private Integer urlType;

    /**
     * 状态
     */
    private String status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime created;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Integer getUrlType() {
        return urlType;
    }

    public void setUrlType(Integer urlType) {
        this.urlType = urlType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    @Override
    public String getUrl() {

        if (menuUrl != null && !menuUrl.startsWith("http")
                && isEnable() && !menuUrl.endsWith(getStaticSuffix())) {

            String typePath = "";
            if (getUrlType() == ARTICLE_URL_TYPE) {
                typePath = getArticleStaticPath();
            } else if (getUrlType() == PAGE_URL_TYPE) {
                typePath = getPageStaticPath();
            } else if (getUrlType() == CATEGORY_URL_TYPE) {
                typePath = getCategoryStaticPath();
            }

            return getWebSiteDomain().concat(typePath).concat(menuUrl) + getStaticSuffix();
        }

        return menuUrl;
    }

}
