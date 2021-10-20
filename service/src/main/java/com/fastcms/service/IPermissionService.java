package com.fastcms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.entity.Permission;
import com.fastcms.mapper.PermissionMapper;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 *  权限服务类
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2021-02-14
 */
public interface IPermissionService extends IService<Permission> {

    /**
     * 获取角色对应的权限
     * @param roleId
     * @return
     */
    List<PermissionTreeNode> getPermissionByRoleId(Long roleId);

    /**
     * 获取用户已授权权限
     * @param userId
     * @return
     */
    List<Permission> getPermissionByUserId(Long userId);

    /**
     * 获取用户菜单权限
     * @param userId
     * @return
     */
    List<MenuNode> getUserMenuPermission(Long userId);

    /**
     * 获取用户中心菜单
     * @return
     */
    List<MenuNode> getUCenterMenuList();

    /**
     * 删除掉权限
     * @param permissionList
     */
    void deleteRolePermissionByPermission(List<Permission> permissionList);

    enum PermissionType {
        MENU("menu"), TAB("tab"), OPTION("option"), MODULE("module");
        PermissionType(String type){
            this.type = type;
        }
        public final String type;
        public String getType() {
            return this.type;
        }
    }

    @Data
    abstract class TreeNode<T extends TreeNode> {
        private Long id;
        private Long parentId;
        private int sortNum = 0;
        private String type;
        List<T> children;
    }

    interface TreeNodeConvert<T extends TreeNode> {
        T convert2Node(Object object);
        List<T> convert2NodeList(List<?> list);

        default void getChildrenNode(T node, List<T> nodeList) {
            List<T> childrenNodeList = nodeList.stream().filter(item -> accept(item, node)).sorted(Comparator.comparing(TreeNode::getSortNum)).collect(Collectors.toList());
            if(childrenNodeList != null && childrenNodeList.size()>0){
                node.setChildren(childrenNodeList);
                childrenNodeList.forEach(item -> nodeList.remove(item));
                childrenNodeList.forEach(item -> getChildrenNode(item, nodeList));
            }
        }

        boolean accept(T node1, T node2);

    }

    class PermissionTreeNodeConvert<T extends TreeNode> implements TreeNodeConvert<T> {

        @Override
        public T convert2Node(Object object) {
            PermissionMapper.RolePermission rolePermission = (PermissionMapper.RolePermission) object;
            PermissionTreeNode treeNode = new PermissionTreeNode();
            treeNode.setId(rolePermission.getId());
            treeNode.setParentId(rolePermission.getParentId());
            treeNode.setName(rolePermission.getName());
            treeNode.setType(rolePermission.getType());
            treeNode.setOpen("true");
            treeNode.setSortNum(rolePermission.getSortNum());
            if(rolePermission.getRoleId() != null){
                treeNode.setChecked(true);
            }
            return (T) treeNode;
        }

        @Override
        public List<T> convert2NodeList(List<?> list) {
            List<TreeNode> treeNodeList = new ArrayList<>();
            list.forEach(item -> treeNodeList.add((TreeNode) convert2Node(item)));
            List<TreeNode> parentNodeList = treeNodeList.stream().sorted(Comparator.comparing(TreeNode::getSortNum)).filter(item -> item.getParentId() == null).collect(Collectors.toList());
            parentNodeList.forEach(item -> getChildrenNode((T) item, (List<T>) treeNodeList));
            return (List<T>) parentNodeList;
        }

        @Override
        public boolean accept(T node1, T node2) {
            return Objects.equals(node1.getParentId(), node2.getId());
        }

    }

    class MenuNodeConvert<T extends TreeNode> implements TreeNodeConvert<T> {

        @Override
        public T convert2Node(Object object) {
            Permission permission = (Permission) object;
            MenuNode menuNode = new MenuNode();
            menuNode.setId(permission.getId());
            menuNode.setParentId(permission.getParentId());
            menuNode.setName(permission.getName());
            menuNode.setUrl(permission.getUrl());
            menuNode.setType(permission.getType());
            menuNode.setIcon(permission.getIcon());
            menuNode.setSortNum(permission.getSortNum());
            return (T) menuNode;
        }

        @Override
        public List<T> convert2NodeList(List<?> list) {
            List<MenuNode> menuNodeList = new ArrayList<>();
            list.stream().filter(item -> {
                Permission p = ((Permission)item);
                return p.getType().equals(PermissionType.MENU.getType()) || p.getType().equals(PermissionType.MODULE.getType());
            }).forEach(item -> menuNodeList.add((MenuNode) convert2Node(item)));
            List<MenuNode> parentNodeList = menuNodeList.stream().sorted(Comparator.comparing(MenuNode::getSortNum)).filter(item -> item.getParentId() == null).collect(Collectors.toList());
            parentNodeList.forEach(item -> getChildrenNode((T) item, (List<T>) menuNodeList));
            return (List<T>) parentNodeList;
        }

        @Override
        public boolean accept(T node1, T node2) {
            return Objects.equals(node1.getParentId(), node2.getId()) && PermissionType.MENU.getType().equals(node1.getType());
        }

    }

    @Data
    class PermissionTreeNode extends TreeNode implements Serializable {
        private String name;
        private String open;
        private Boolean checked = false;
    }

    @Data
    class MenuNode extends PermissionTreeNode {
        private String url;
        private String icon;
        private Boolean hasChildren = false;
        public Boolean getHasChildren() {
            return children != null && children.size()>0;
        }
    }

}
