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
    @NotBlank(message = "菜单名称不能为空")
    private String menuName;

    /**
     * 菜单地址
     */
    @NotBlank(message = "菜单URL不能为空")
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
        return getUrl();
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
                && isEnableFakeStatic() && !menuUrl.endsWith(getFakeStaticSuffix())) {
            return menuUrl + getFakeStaticSuffix();
        }

        return menuUrl;
    }

}
