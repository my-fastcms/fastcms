package com.fastcms.cms.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fastcms.cms.entity.Menu;
import com.fastcms.cms.mapper.MenuMapper;
import com.fastcms.cms.service.IMenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 网站菜单表 服务实现类
 * @author wjun_java@163.com
 * @since 2021-05-27
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

	@Override
	public List<MenuNode> getMenus() {
		List<Menu> menus = list(Wrappers.<Menu>lambdaQuery().eq(Menu::getStatus, Menu.STATUS_SHOW));
		List<MenuNode> menuNodeList = menus.stream().map(item -> getMenuNode(item)).collect(Collectors.toList());;
		List<MenuNode> parentMenuList = menuNodeList.stream().filter(item -> item.getParentId() == 0).collect(Collectors.toList());
		parentMenuList.forEach(item -> getChildren(item, menuNodeList));
		return parentMenuList.stream().sorted(Comparator.comparing(MenuNode::getSortNum)).collect(Collectors.toList());
	}

	void getChildren(MenuNode menuNode, List<MenuNode> menuNodeList) {
		List<MenuNode> childrenNodeList = menuNodeList.stream().filter(item -> Objects.equals(item.getParentId(), menuNode.getId())).collect(Collectors.toList());
		if(childrenNodeList != null && !childrenNodeList.isEmpty()) {
			menuNode.setChildren(childrenNodeList);
			menuNode.setHasChildren(true);
			childrenNodeList.forEach(item -> getChildren(item, menuNodeList));
		}
	}

	MenuNode getMenuNode(Menu menu) {
		MenuNode menuNode = new MenuNode();
		BeanUtils.copyProperties(menu, menuNode);
		return menuNode;
	}

}
