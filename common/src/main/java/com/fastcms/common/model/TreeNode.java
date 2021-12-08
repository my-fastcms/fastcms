package com.fastcms.common.model;

import java.io.Serializable;
import java.util.List;

/**
 * element-ui-tree
 * wjun_java@163.com
 */
public class TreeNode implements Serializable {

    /**
     * id
     */
    private Long id;
    /**
     * 上级id
     */
    private Long parentId;
    /**
     * 名称
     */
    private String label;
    /**
     * 是否显示
     */
    private Boolean isShow;
    /**
     * 是否被选中
     */
    private Boolean isChecked;

    public TreeNode(Long id, Long parentId, String label, Boolean isShow) {
        this.id = id;
        this.parentId = parentId;
        this.label = label;
        this.isShow = isShow;
    }

    public TreeNode(Long id, Long parentId, String label) {
        this(id, parentId, label, true);
    }

    public TreeNode(String label) {
        this(null, null, label, true);
    }

    /**
     * 子节点集合
     */
    List<TreeNode> children;

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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean getShow() {
        return isShow;
    }

    public void setShow(Boolean show) {
        isShow = show;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

}
