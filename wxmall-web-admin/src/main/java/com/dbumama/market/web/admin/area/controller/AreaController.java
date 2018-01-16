package com.dbumama.market.web.admin.area.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dbumama.market.model.Area;
import com.dbumama.market.service.api.area.AreaService;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.BaseController;
import com.dbumama.market.web.core.plugin.spring.Inject.BY_NAME;

@RouteBind(path = "area")
public class AreaController extends BaseController{
	@BY_NAME
	private AreaService areaService;
	/**
	 * 地区
	 */
	public void list() {
		Long parentId = getParaToLong("parentId");
		List<Area> areas = new ArrayList<Area>();
		Area parent = areaService.findById(parentId);
		if (parent != null) {
			areas = areaService.getChildren(parent.getId());
		} else {
			areas = areaService.findRoots();
		}
		Map<Long, String> options = new HashMap<Long, String>();
		for (Area area : areas) {
			options.put(area.getId(), area.getName());
		}
		renderJson(options);
	}
}
