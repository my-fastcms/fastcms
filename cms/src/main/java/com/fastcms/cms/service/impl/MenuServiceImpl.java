package com.fastcms.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fastcms.cms.entity.Menu;
import com.fastcms.cms.mapper.MenuMapper;
import com.fastcms.cms.service.IMenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 网站菜单表 服务实现类
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2021-05-27
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

	public static final String WEB_SITE_MENU = "web_site_menu";

	@Override
	public List<MenuNode> getMenus() {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("status", Menu.STATUS_SHOW);
		List<Menu> menus = list(queryWrapper);

		List<MenuNode> menuNodeList = new ArrayList<>();
		menus.forEach(item -> menuNodeList.add(getMenuNode(item)));

		List<MenuNode> parentMenuList = menuNodeList.stream().filter(item -> item.getParentId() == null).collect(Collectors.toList());

		parentMenuList.forEach(item -> getChildren(item, menuNodeList));

		return parentMenuList.stream().sorted(Comparator.comparing(MenuNode::getSortNum)).collect(Collectors.toList());
	}

	void getChildren(MenuNode menuNode, List<MenuNode> menuNodeList) {
		List<MenuNode> childrenNodeList = menuNodeList.stream().filter(item -> Objects.equals(item.getParentId(), menuNode.getId())).collect(Collectors.toList());
		if(childrenNodeList != null && !childrenNodeList.isEmpty()) {
			menuNode.setChildren(childrenNodeList);
			childrenNodeList.forEach(item -> getChildren(item, menuNodeList));
		}
	}

	MenuNode getMenuNode(Menu menu) {
		MenuNode menuNode = new MenuNode();
		BeanUtils.copyProperties(menu, menuNode);
		return menuNode;
	}

}
