package com.dbumama.market.service.provider;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dbumama.market.model.Area;
import com.dbumama.market.service.api.area.AreaResultDto;
import com.dbumama.market.service.api.area.AreaService;
@Service("areaService")
public class AreaServiceImpl implements AreaService{

	@Override
	public List<Area> findRoots() {
		List<Area> areaList=new ArrayList<Area>();
		String sql = "SELECT * FROM t_area area WHERE area.parent_id IS NULL ORDER BY area.orders ASC ";
		areaList=Area.dao.find(sql);
		return areaList;
	}

	@Override
	public List<Area> getChildren(Long AreaId) {
		List<Area> areaList=new ArrayList<Area>();
		String sql = "SELECT * FROM `t_area` WHERE parent_id = ? ORDER BY orders ASC";
		areaList=Area.dao.find(sql, AreaId);
		return areaList;
	}

	@Override
	public String getAreaName(String[] id) {
		
		String areaName="";
        if(id.length>0){
        	for (int i = 0; i < id.length; i++) {
    			Area area=Area.dao.findById(id[i]);
    			areaName=areaName+area.getName()+",";
    		}
        	areaName=areaName.substring(0,areaName.length()-1);
		}
		
		return areaName;
	}

	@Override
	public List<AreaResultDto> getAreaResultDto(String[] id) {
		List<AreaResultDto> resultDto=new ArrayList<AreaResultDto>();
        if(id.length>0){
        	for (int i = 0; i < id.length; i++) {
    			Area area=Area.dao.findById(id[i]);
    			AreaResultDto dto=new AreaResultDto();
    			dto.setId(new Long(id[i]));
    			dto.setName(area.getName());
    			resultDto.add(dto);
    		}

		}
		return resultDto;
	}

}
