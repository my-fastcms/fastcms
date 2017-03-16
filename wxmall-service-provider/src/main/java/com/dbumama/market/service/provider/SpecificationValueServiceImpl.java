package com.dbumama.market.service.provider;

import org.springframework.stereotype.Service;

import com.dbumama.market.model.SpecificationValue;
import com.dbumama.market.service.api.specification.SpecificationValueService;

@Service("specificationValueService")
public class SpecificationValueServiceImpl extends AbstractActivityService implements SpecificationValueService{

	@Override
	public SpecificationValue find(Long SpecificationValueId) {
		return SpecificationValue.dao.findById(SpecificationValueId);
	}

}
