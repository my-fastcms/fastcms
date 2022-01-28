package com.fastcms.cms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.cms.entity.Menu;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;

/**
 * 网站菜单服务类
 * @author wjun_java@163.com
 * @since 2021-05-27
 */
public interface IMenuService extends IService<Menu> {

	List<MenuNode> getMenus();

	class MenuNode extends Menu implements Serializable {
		@JsonIgnore
		boolean hasChildren = false;
		List<MenuNode> children;

		public boolean getHasChildren() {
			return hasChildren;
		}

		public boolean isHasChildren() {
			return hasChildren;
		}

		public void setHasChildren(boolean hasChildren) {
			this.hasChildren = hasChildren;
		}

		public List<MenuNode> getChildren() {
			return children;
		}

		public void setChildren(List<MenuNode> children) {
			this.children = children;
		}
	}

}
