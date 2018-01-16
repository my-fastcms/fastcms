package com.dbumama.market.service.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.dbumama.market.model.Area;
import com.dbumama.market.service.api.area.AreaResultDto;
import com.dbumama.market.service.api.area.AreaService;
@Service("areaService")
public class AreaServiceImpl implements AreaService{
	/*private static final String AREA_CACHENAME = "area_cache";
	private static final String AREA_CACHENAME_KEY = "area_key";*/
	
	private static Map<String, Area> areaCacheMap = new HashMap<String, Area>();
	public static final Area areaDao = new Area().dao();
	
	@Override
	public List<Area> findRoots() {
		List<Area> areaList=new ArrayList<Area>();
		areaList=areaDao.find("SELECT * FROM t_area area WHERE area.parent_id IS NULL ORDER BY area.orders ASC ");
		return areaList;
	}

	@Override
	public List<Area> getChildren(Long AreaId) {
		List<Area> areaList=new ArrayList<Area>();
		areaList=areaDao.find("SELECT * FROM `t_area` WHERE parent_id = ? ORDER BY orders ASC", AreaId);
		return areaList;
	}

	@Override
	public String getAreaName(String[] id) {
		
		String areaName="";
        if(id.length>0){
        	for (int i = 0; i < id.length; i++) {
        		Area area = areaCacheMap.get(id[i]);
        		if(area == null){
        			area=areaDao.findById(id[i]);
        			areaCacheMap.put(id[i], area);
        		}
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
        		Area area = areaCacheMap.get(id[i]);
        		if(area == null){
        			area=areaDao.findById(id[i]);
        			areaCacheMap.put(id[i], area);
        		}
    			AreaResultDto dto=new AreaResultDto();
    			dto.setId(new Long(id[i]));
    			dto.setName(area.getName());
    			resultDto.add(dto);
    		}

		}
		return resultDto;
	}

	@Override
	public Area findById(Long areaId) {
		return areaDao.findById(areaId);
	}

}
