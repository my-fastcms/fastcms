package com.fastcms.common.model;

import java.io.Serializable;
import java.util.List;

/**
 * element-ui-tree
 * wjun_java@163.com
 */
public class TreeNode<T extends TreeNode> implements Serializable {

    /**
     * id
     */
    protected Long id;
    /**
     * 上级id
     */
    protected Long parentId;
    /**
     * 名称
     */
    protected String label;
    /**
     * 是否显示
     */
    protected Boolean isShow;
    /**
     * 是否被选中
     */
    protected Boolean isChecked;

    /**
     * 排序
     */
    protected int sortNum;

    /**
     * 子节点集合
     */
    List<T> children;

    public TreeNode(Long id, Long parentId, String label, Boolean isShow, int sortNum) {
        this.id = id;
        this.parentId = parentId;
        this.label = label;
        this.isShow = isShow;
        this.sortNum = sortNum;
    }

    public TreeNode(Long id, Long parentId, String label, Boolean isShow) {
        this(id, parentId, label, isShow, 0);
    }

    public TreeNode(Long id, Long parentId, String label) {
        this(id, parentId, label, true, 0);
    }

    public TreeNode(Long id, Long parentId) {
        this(id, parentId, null);
    }

    public TreeNode(String label, int sortNum) {
        this(null, null, label, true, sortNum);
    }

    public TreeNode(String label) {
        this(null, null, label, true, 0);
    }

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

    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    public List<T> getChildren() {
        return children;
    }

    public void setChildren(List<T> children) {
        this.children = children;
    }

}
