package com.fastcms.common.model;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * wjun_java@163.com
 * TreeNode转换器
 */
public interface TreeNodeConvert<T extends TreeNode> {

    /**
     * 把object 转成 TreeNode
     * @param object
     * @return
     */
    T convert2Node(Object object);

    /**
     * 为一批父节点递归设置子节点集合
     * @param dataList
     * @return
     */
    default List<T> getTreeNodeList(List<?> dataList) {
        List<T> nodeList = dataList.stream().map(item -> convert2Node(item)).collect(Collectors.toList());
        List<T> parents = nodeList.stream().filter(item -> isParent(item)).collect(Collectors.toList());
        parents.forEach(item -> getChildrenNode(item, nodeList));
        return parents.stream().sorted(Comparator.comparing(T::getSortNum)).collect(Collectors.toList());
    }

    /**
     * 判断一批数据中是否是一级父类
     * @param node
     * @return
     */
    default boolean isParent(T node) {
        return node.getParentId() == 0;
    }

    /**
     * 获取子节点集合
     * @param node
     * @param nodeList
     */
    default void getChildrenNode(T node, List<T> nodeList) {
        List<T> childrenNodeList = nodeList.stream().filter(item -> accept(item, node)).sorted(Comparator.comparing(TreeNode::getSortNum)).collect(Collectors.toList());
        if(childrenNodeList != null && childrenNodeList.size()>0) {
            node.setChildren(childrenNodeList);
            childrenNodeList.forEach(item -> nodeList.remove(item));
            childrenNodeList.forEach(item -> getChildrenNode(item, nodeList));
        }
    }

    /**
     * 判断是否属于子节点
     * @param node1
     * @param node2
     * @return
     */
    default boolean accept(T node1, T node2) {
        return Objects.equals(node1.getParentId(), node2.getId());
    }

}
