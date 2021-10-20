package com.fastcms.cms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.cms.entity.Menu;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 网站菜单表 服务类
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2021-05-27
 */
public interface IMenuService extends IService<Menu> {

	List<MenuNode> getMenus();

	@Data
	class MenuNode extends Menu implements Serializable {
		boolean hasChildren = false;
		List<MenuNode> children;
		public boolean getHasChildren() {
			return children != null && children.size()>0;
		}
	}

}
