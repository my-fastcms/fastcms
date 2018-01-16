package com.dbumama.market.service.api.specification;

import java.util.List;

import com.dbumama.market.model.Specification;
import com.dbumama.market.model.SpecificationValue;
import com.dbumama.market.service.api.base.BaseService;
import com.jfinal.plugin.activerecord.Page;

/**
 * @author wangjun
 * 2017年7月25日
 */
public interface SpecificationService extends BaseService {
	
	Page<SpecificationsResultDto> list (Long sellerId, Integer pageNo, Integer pageSize);
	
	public List<SpecificationResultDto> findAll(SpecificationParamDto specificationParamDto);

	public SpecificationResultDto getSpeciAndVaules(Long specId);
	
	public List<SpecificationValue> getSpeciValues(Long specId);

	public Specification save(Specification specification, String items, Long sellerId);

}
